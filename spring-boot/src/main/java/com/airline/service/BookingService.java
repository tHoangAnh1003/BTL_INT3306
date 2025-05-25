package com.airline.service;

import java.util.List;

import com.airline.repository.entity.BookingEntity;

public interface BookingService {
    List<BookingEntity> getAllBookings();
    BookingEntity getBookingById(Long id);
    void createBooking(BookingEntity booking);
    void updateBooking(BookingEntity booking);
    void deleteBooking(Long id);
}
