 package com.airline.api.controller;

import com.airline.repository.entity.UserEntity;
import com.airline.security.JwtAuthenticationFilter;
import com.airline.repository.entity.BookingEntity;
import com.airline.service.BookingService;
import com.airline.service.UserService;
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

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;

    // 1. Get all Bookings
    @GetMapping
    public List<BookingEntity> getAll(HttpServletRequest request) {
        UserEntity requester = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        if (AuthUtil.isAdmin(requester)) {
            return bookingService.getAllBookings();
        } else {
            return bookingService.getBookingsByPassengerId(requester.getUserId());
        }
    }

    // 2. Get Booking by ID
    @GetMapping("/{id}")
    public BookingEntity getById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    // 3. Create new Booking
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(HttpServletRequest request,
                                                      @RequestBody BookingEntity booking) {
        UserEntity requester = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        if (!AuthUtil.isCustomer(requester)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("msg", "Chỉ khách hàng mới được đặt vé"));
        }
        booking.setPassengerId(requester.getUserId());
        bookingService.createBooking(booking);
        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", "Đặt vé thành công");
        resp.put("bookingId", booking.getBookingId());
        return ResponseEntity.ok(resp);
    }


    // 4. Update Booking by ID
    @PutMapping("/{id}")
    public BookingEntity update(HttpServletRequest request,
                                @PathVariable Long id,
                                @RequestBody BookingEntity booking) {
        UserEntity requester = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        BookingEntity existing = bookingService.getBookingById(id);
        if (!AuthUtil.isAdmin(requester)
            && !existing.getPassengerId().equals(requester.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You can only update your own bookings.");
        }
        booking.setBookingId(id);
        bookingService.updateBooking(booking);
        return booking;
    }

    // 5. Remove Booking
    @DeleteMapping("/{id}")
    public void delete(HttpServletRequest request,
                       @PathVariable Long id) {
        UserEntity requester = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        BookingEntity existing = bookingService.getBookingById(id);
        if (!AuthUtil.isAdmin(requester)
            && !existing.getPassengerId().equals(requester.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        bookingService.deleteBooking(id);
    }
    
    @GetMapping("/passenger/{passengerId}")
    public List<BookingEntity> getByPassengerId(HttpServletRequest request,
                                                @PathVariable Long passengerId) {
        UserEntity requester = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        if (AuthUtil.isAdmin(requester)
            || requester.getUserId().equals(passengerId)) {
            return bookingService.getBookingsByPassengerId(passengerId);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
    }
   
    @GetMapping("/{id}/can-cancel")
    public ResponseEntity<Boolean> canCancel(@PathVariable Long id) {
        boolean canCancel = bookingService.canCancelBooking(id);
        return ResponseEntity.ok(canCancel);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        boolean result = bookingService.cancelBooking(id);
        if (result) {
            return ResponseEntity.ok("Booking cancelled successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Cannot cancel booking: past allowed cancellation deadline or invalid status.");
        }
    }
}
