package com.airline.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.airline.repository.BookingRepository;
import com.airline.repository.entity.BookingEntity;
import com.airline.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public BookingEntity getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public void createBooking(BookingEntity booking) {
        bookingRepository.save(booking);
    }

    @Override
    public void updateBooking(BookingEntity booking) {
        bookingRepository.update(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.delete(id);
    }
}
