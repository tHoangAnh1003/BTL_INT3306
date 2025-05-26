 package com.airline.api.controller;

import com.airline.repository.entity.UserEntity;
import com.airline.repository.entity.BookingEntity;
import com.airline.service.BookingService;
import com.airline.service.UserService;
import com.airline.utils.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;

    // 1. Get all Bookings
    @GetMapping
    public List<BookingEntity> getAll(
            @RequestHeader("X-Requester-Id") Long requesterId) {

        UserEntity requester = userService.findById(requesterId);

        if ("ADMIN".equalsIgnoreCase(requester.getRole())) {
            return bookingService.getAllBookings();
        } else {
            return bookingService.getBookingsByPassengerId(requesterId);
        }
    }

    // 2. Get Booking by ID
    @GetMapping("/{id}")
    public BookingEntity getById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    // 3. Create new Booking
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(
            @RequestHeader("X-Requester-Id") Long requesterId,
            @RequestBody BookingEntity booking) {

        UserEntity requester = userService.findById(requesterId);
        if (!AuthUtil.isCustomer(requester)) {
            Map<String, Object> error = new HashMap<>();
            error.put("msg", "Chỉ khách hàng mới được đặt vé");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }

        booking.setPassengerId(requesterId);
        bookingService.createBooking(booking);

        Map<String, Object> response = new HashMap<>();
        response.put("msg", "Đặt vé thành công");
        response.put("bookingId", booking.getBookingId());

        return ResponseEntity.ok(response);
    }


    // 4. Update Booking by ID
    @PutMapping("/{id}")
    public BookingEntity update(
            @PathVariable Long id,
            @RequestHeader("X-Requester-Id") Long requesterId,
            @RequestBody BookingEntity booking) {

        UserEntity requester = userService.findById(requesterId);
        BookingEntity existing = bookingService.getBookingById(id);

        if (!AuthUtil.isAdmin(requester)
                && !existing.getPassengerId().equals(requesterId)) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Access denied: You can only update your own bookings.");
        }

        booking.setBookingId(id);
        bookingService.updateBooking(booking);
        return booking;
    }

    // 5. Remove Booking
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id,
            @RequestHeader("X-Requester-Id") Long requesterId) {

        UserEntity requester = userService.findById(requesterId);
        BookingEntity existing = bookingService.getBookingById(id);

        if (!AuthUtil.isAdmin(requester)
                && !existing.getPassengerId().equals(requesterId)) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Access denied");
        }

        bookingService.deleteBooking(id);
    }
    
    @GetMapping("/passenger/{passengerId}")
    public List<BookingEntity> getByPassengerId(
            @PathVariable Long passengerId,
            @RequestHeader("X-Requester-Id") Long requesterId) {

        UserEntity requester = userService.findById(requesterId);

        if (AuthUtil.isAdmin(requester) 
                || requesterId.equals(passengerId)) {
            return bookingService.getBookingsByPassengerId(passengerId);
        }

        throw new ResponseStatusException(
            HttpStatus.FORBIDDEN, "Access denied");
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
