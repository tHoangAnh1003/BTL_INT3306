package com.airline.api;

import com.airline.repository.entity.BookingEntity;
import com.airline.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingAPI {

    @Autowired
    private BookingService bookingService;

    // 1. Get all Bookings
    @GetMapping
    public List<BookingEntity> getAll() {
        return bookingService.getAllBookings();
    }

    // 2. Get Booking by ID
    @GetMapping("/{id}")
    public BookingEntity getById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    // 3. Create new Booking
    @PostMapping
    public BookingEntity create(@RequestBody BookingEntity booking) {
        bookingService.createBooking(booking);
        return booking;
    }

    // 4. Update Booking by ID
    @PutMapping("/{id}")
    public BookingEntity update(@PathVariable Long id, @RequestBody BookingEntity booking) {
        booking.setBookingId(id);
        bookingService.updateBooking(booking);
        return booking;
    }

    // 5. Remove Booking
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
}
