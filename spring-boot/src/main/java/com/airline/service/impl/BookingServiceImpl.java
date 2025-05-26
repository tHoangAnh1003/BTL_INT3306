package com.airline.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.repository.BookingRepository;
import com.airline.repository.entity.BookingEntity;
import com.airline.repository.entity.FlightEntity;
import com.airline.service.BookingService;
import com.airline.service.FlightService;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
    private BookingRepository bookingRepository;
	@Autowired
    private FlightService flightService;

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
    
    @Override
    public List<BookingEntity> getBookingsByPassengerId(Long passengerId) {
        return bookingRepository.findByPassengerId(passengerId);
    }
    
    private static final long CANCEL_DEADLINE_HOURS = 24;

    @Override
    public boolean canCancelBooking(Long bookingId) {
        BookingEntity booking = bookingRepository.findById(bookingId);
        if (booking == null) {
            return false; 
        }
        FlightEntity flight = flightService.getFlightById(booking.getFlightId());
        if (flight == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime departureTime = flight.getDepartureTime();

        long hoursUntilDeparture = java.time.Duration.between(now, departureTime).toHours();

        return hoursUntilDeparture >= CANCEL_DEADLINE_HOURS && "Confirmed".equalsIgnoreCase(booking.getStatus());
    }

    @Override
    public boolean cancelBooking(Long bookingId) {
        if (!canCancelBooking(bookingId)) {
            return false;
        }
        BookingEntity booking = bookingRepository.findById(bookingId);
        booking.setStatus("Cancelled");
        bookingRepository.update(booking);
        return true;
    }
}
