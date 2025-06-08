package com.airline.service;

import java.util.List;

import com.airline.DTO.booking.BookingStatisticsDTO;
import com.airline.entity.BookingEntity;

public interface BookingService {
    List<BookingEntity> getAllBookings();
    BookingEntity getBookingById(Long id);
    void createBooking(BookingEntity booking);
    void updateBooking(BookingEntity booking);
    void deleteBooking(Long id);
    List<BookingEntity> getBookingsByPassengerId(Long passengerId);
    BookingEntity findById(Long id);
    void save(BookingEntity booking);
    void cancelBooking(Long bookingId, Long userId);
    List<BookingStatisticsDTO> getBookingStatistics();
}
