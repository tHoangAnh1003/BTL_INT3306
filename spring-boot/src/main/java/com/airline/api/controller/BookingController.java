 package com.airline.api.controller;

import com.airline.repository.entity.UserEntity;
import com.airline.repository.entity.BookingEntity;
import com.airline.service.BookingService;
import com.airline.service.UserService;
import com.airline.utils.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

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
    public BookingEntity create(
            @RequestHeader("X-Requester-Id") Long requesterId,
            @RequestBody BookingEntity booking) {

        UserEntity requester = userService.findById(requesterId);
        if (!AuthUtil.isCustomer(requester)) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "Only Customers can create bookings.");
        }

        booking.setPassengerId(requesterId);
        bookingService.createBooking(booking);
        return booking;
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
}
