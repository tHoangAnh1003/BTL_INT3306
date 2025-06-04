 package com.airline.api.controller;

import com.airline.entity.UserEntity;
import com.airline.repository.BookingRepository;
import com.airline.repository.FlightRepository;
import com.airline.repository.FlightSeatRepository;
import com.airline.repository.PassengerRepository;
import com.airline.security.JwtAuthenticationFilter;
import com.airline.DTO.booking.BookingRequestDTO;
import com.airline.DTO.booking.BookingResponseDTO;
import com.airline.DTO.booking.BookingStatisticsDTO;
import com.airline.DTO.booking.BookingSummaryDTO;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
    
    @Autowired
    private BookingRepository bookingRepository;
    
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
                                                             @RequestBody BookingRequestDTO bookingRequest) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);

        if (!AuthUtil.isCustomer(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("msg", "Chỉ khách hàng mới được đặt vé"));
        }

        List<FlightEntity> flights = flightRepository.findByAircraftModel(bookingRequest.getAircraftModel());
        if (flights.isEmpty()) {
            throw new RuntimeException("Không tìm thấy chuyến bay với máy bay: " + bookingRequest.getAircraftModel());
        }

        FlightEntity flight = flights.get(0);

        FlightSeatEntity seat = flightSeatRepository.findById(bookingRequest.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat không tồn tại"));

        PassengerEntity passenger = passengerRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Passenger không tồn tại"));

        BookingEntity booking = new BookingEntity();
        booking.setPassenger(passenger);
        booking.setFlight(flight);
        booking.setSeat(seat);
        booking.setStatus(bookingRequest.getStatus());
        booking.setBookingDate(LocalDateTime.now());

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

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(HttpServletRequest request,
                                           @PathVariable Long bookingId) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bạn cần đăng nhập");
        }

        try {
            bookingService.cancelBooking(bookingId, user.getId());
            return ResponseEntity.ok("Hủy vé thành công");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
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
    
    @GetMapping("/my")
    public ResponseEntity<?> getMyBookings(HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);

        if (!AuthUtil.isCustomer(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("msg", "Chỉ khách hàng mới được xem danh sách vé đã đặt"));
        }

        List<BookingEntity> bookings = bookingRepository.findByPassenger_Id(user.getId());
        List<BookingSummaryDTO> response = new ArrayList<>();

        for (BookingEntity booking : bookings) {
            response.add(BookingConverter.toSummaryDTO(booking));
        }

        return ResponseEntity.ok(response);
    }


}

