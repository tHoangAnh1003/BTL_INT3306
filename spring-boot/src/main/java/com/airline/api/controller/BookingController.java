package com.airline.api.controller;

import com.airline.repository.entity.UserEntity;
import com.airline.repository.entity.BookingEntity;
import com.airline.service.BookingService;
import com.airline.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserService userService;

    // 1. Get all Bookings
    @GetMapping
    public List<BookingEntity> getAll(@RequestParam Long requesterId) {
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
    public BookingEntity create(@RequestBody BookingEntity booking, @RequestParam Long requesterId) {
        UserEntity requester = userService.findById(requesterId);
        if (!"USER".equalsIgnoreCase(requester.getRole())) {
            throw new RuntimeException("Only USERs can create bookings.");
        }
        booking.setPassengerId(requesterId); 
        bookingService.createBooking(booking);
        return booking;
    }

    // 4. Update Booking by ID
    @PutMapping("/{id}")
    public BookingEntity update(@PathVariable Long id, @RequestBody BookingEntity booking, @RequestParam Long requesterId) {
        UserEntity requester = userService.findById(requesterId);
        BookingEntity existing = bookingService.getBookingById(id);

        if (!"ADMIN".equalsIgnoreCase(requester.getRole()) && !existing.getPassengerId().equals(requesterId)) {
            throw new RuntimeException("Access denied: You can only update your own bookings.");
        }

        booking.setBookingId(id);
        bookingService.updateBooking(booking);
        return booking;
    }

    // 5. Remove Booking
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestParam Long requesterId) {
        UserEntity requester = userService.findById(requesterId);
        BookingEntity booking = bookingService.getBookingById(id);

        if (!"ADMIN".equalsIgnoreCase(requester.getRole()) && !booking.getPassengerId().equals(requesterId)) {
            throw new RuntimeException("Access denied: You can only delete your own bookings.");
        }

        bookingService.deleteBooking(id);
    }
}
