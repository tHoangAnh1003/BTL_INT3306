package com.airline.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.DTO.booking.BookingStatisticsDTO;
import com.airline.entity.BookingEntity;
import com.airline.entity.FlightEntity;
import com.airline.repository.BookingRepository;
import com.airline.service.BookingService;
import com.airline.service.FlightService;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightService flightService;

    private static final long CANCEL_DEADLINE_HOURS = 24;

    @Override
    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public BookingEntity getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public void createBooking(BookingEntity booking) {
        bookingRepository.save(booking);
    }

    @Override
    public void updateBooking(BookingEntity booking) {
        bookingRepository.save(booking); // update when ID exists
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<BookingEntity> getBookingsByPassengerId(Long passengerId) {
        return bookingRepository.findByPassenger_Id(passengerId); 
    }

    @Override
    public boolean canCancelBooking(Long bookingId) {
    	Optional<BookingEntity> optionalBooking = bookingRepository.findById(bookingId);
        if (!optionalBooking.isPresent()) return false;

        BookingEntity booking = optionalBooking.get();
        FlightEntity flight = flightService.getFlightById(booking.getFlight().getId());
        if (flight == null) return false;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime departureTime = flight.getDepartureTime();

        long hoursUntilDeparture = java.time.Duration.between(now, departureTime).toHours();
        return hoursUntilDeparture >= CANCEL_DEADLINE_HOURS && "Confirmed".equalsIgnoreCase(booking.getStatus());
    }

    @Override
    public boolean cancelBooking(Long bookingId) {
        Optional<BookingEntity> optionalBooking = bookingRepository.findById(bookingId);
        if (!optionalBooking.isPresent()) return false;

        BookingEntity booking = optionalBooking.get();
        if (!canCancelBooking(bookingId)) return false;

        booking.setStatus("Cancelled");
        bookingRepository.save(booking);
        return true;
    }
    
    @Override
    public List<BookingStatisticsDTO> getBookingStatistics() {
        List<Object[]> rawStats = bookingRepository.getBookingStatisticsRaw();
        List<BookingStatisticsDTO> result = new ArrayList<>();

        for (int i = 0; i < rawStats.size(); i++) {
            Object[] row = rawStats.get(i);
            BookingStatisticsDTO dto = new BookingStatisticsDTO();

            dto.setAircraftModel((String) row[1]);
            dto.setRoute((String) row[2]);
            dto.setDepartureTime(row[3].toString()); // or format as needed
            dto.setPassengerName((String) row[4]);

            result.add(dto);
        }

        return result;
    }

}
