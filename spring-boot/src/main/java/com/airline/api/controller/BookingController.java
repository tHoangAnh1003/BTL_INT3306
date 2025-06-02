 package com.airline.api.controller;

import com.airline.entity.UserEntity;
import com.airline.repository.FlightRepository;
import com.airline.repository.FlightSeatRepository;
import com.airline.repository.PassengerRepository;
import com.airline.security.JwtAuthenticationFilter;
import com.airline.DTO.booking.BookingResponseDTO;
import com.airline.DTO.booking.BookingStatisticsDTO;
import com.airline.converter.BookingConverter;
import com.airline.entity.BookingEntity;
import com.airline.entity.FlightEntity;
import com.airline.entity.FlightSeatEntity;
import com.airline.entity.PassengerEntity;
import com.airline.service.BookingService;
import com.airline.utils.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    
    @Autowired 
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;
    
    @Autowired
    private FlightSeatRepository flightSeatRepository;
    
    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> getAll(HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        List<BookingEntity> bookings = AuthUtil.isAdmin(user)
                ? bookingService.getAllBookings()
                : bookingService.getBookingsByPassengerId(user.getId());

        List<BookingResponseDTO> dtoList = bookings.stream()
        	    .map(BookingConverter::toDTO)
        	    .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getById(@PathVariable Long id) {
        BookingEntity booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(BookingConverter.toDTO(booking));
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> createBooking(HttpServletRequest request,
                                                             @RequestBody BookingEntity booking) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);

        if (!AuthUtil.isCustomer(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("msg", "Chỉ khách hàng mới được đặt vé"));
        }

        booking.setPassengerId(user.getId());

        FlightEntity flight = flightRepository.findById(booking.getFlightId())
                              .orElseThrow(() -> new RuntimeException("Flight không tồn tại"));

        FlightSeatEntity seat = flightSeatRepository.findById(booking.getSeatId())
                              .orElseThrow(() -> new RuntimeException("Seat không tồn tại"));

        booking.setFlight(flight);
        booking.setSeat(seat);
        
        PassengerEntity passenger = passengerRepository.findById(user.getId())
                                   .orElseThrow(() -> new RuntimeException("Passenger không tồn tại"));
        booking.setPassenger(passenger);

        bookingService.createBooking(booking);

        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", "Đặt vé thành công");
        resp.put("bookingId", booking.getId());

        return ResponseEntity.ok(resp);
    }


    @PutMapping("/{id}")
    public ResponseEntity<BookingEntity> updateBooking(HttpServletRequest request,
                                                       @PathVariable Long id,
                                                       @RequestBody BookingEntity booking) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        BookingEntity existing = bookingService.getBookingById(id);

        if (!AuthUtil.isAdmin(user) && !existing.getPassengerId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không có quyền sửa đặt vé này.");
        }

        booking.setId(id);
        bookingService.updateBooking(booking);
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(HttpServletRequest request,
                                           @PathVariable Long id) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        BookingEntity existing = bookingService.getBookingById(id);

        if (!AuthUtil.isAdmin(user) && !existing.getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không có quyền hủy đặt vé này.");
        }

        bookingService.deleteBooking(id);
        return ResponseEntity.ok(Collections.singletonMap("msg", "Đã xóa thành công"));
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<BookingEntity>> getByPassenger(HttpServletRequest request,
                                                              @PathVariable Long passengerId) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        if (AuthUtil.isAdmin(user) || user.getId().equals(passengerId)) {
            return ResponseEntity.ok(bookingService.getBookingsByPassengerId(passengerId));
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
    }

    @GetMapping("/{id}/can-cancel")
    public ResponseEntity<Boolean> canCancel(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.canCancelBooking(id));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        boolean result = bookingService.cancelBooking(id);
        if (result) {
            return ResponseEntity.ok("Đã hủy đặt vé thành công.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Không thể hủy: đã quá hạn hoặc trạng thái không hợp lệ.");
    }
    
    @GetMapping("/statistics")
    public ResponseEntity<List<BookingStatisticsDTO>> getBookingStatistics(HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        if (!AuthUtil.isAdmin(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }

        List<BookingStatisticsDTO> stats = bookingService.getBookingStatistics();
        return ResponseEntity.ok(stats);
    }

}

