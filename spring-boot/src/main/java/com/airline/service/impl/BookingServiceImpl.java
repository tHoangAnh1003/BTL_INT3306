package com.airline.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airline.DTO.booking.BookingStatisticsDTO;
import com.airline.entity.BookingEntity;
import com.airline.entity.FlightEntity;
import com.airline.entity.FlightSeatEntity;
import com.airline.repository.BookingRepository;
import com.airline.repository.FlightSeatRepository;
import com.airline.service.BookingService;
import com.airline.service.FlightService;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightService flightService;
    
    @Autowired
    private FlightSeatRepository flightSeatRepository;

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
    public BookingEntity findById(Long id) {
        Optional<BookingEntity> bookingOpt = bookingRepository.findById(id);
        if (!bookingOpt.isPresent()) {
            throw new RuntimeException("Booking not found with id: " + id);
        }
        return bookingOpt.get();
    }

    @Override
    public void save(BookingEntity booking) {
        bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void cancelBooking(Long bookingId, Long userId) {
        BookingEntity booking = findById(bookingId);

        if (!booking.getPassenger().getId().equals(userId - 3)) {
            throw new RuntimeException("User không có quyền hủy vé này");
        }

        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            throw new RuntimeException("Vé đã bị hủy trước đó");
        }

        if (booking.getFlight().getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Không thể hủy vé đã hoặc đang trong thời gian bay");
        }

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);

        FlightSeatEntity seat = booking.getSeat();
        if (seat != null) {
            seat.setIsBooked(false);
            flightSeatRepository.save(seat);
        }
    }

    
    @Override
    public List<BookingStatisticsDTO> getBookingStatistics() {
        List<Object[]> rawStats = bookingRepository.getBookingStatisticsRaw();
        List<BookingStatisticsDTO> result = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

        for (Object[] row : rawStats) {
            BookingStatisticsDTO dto = new BookingStatisticsDTO();

            dto.setAircraftModel((String) row[1]);
            dto.setRoute((String) row[2]);

            // Format departure time
            if (row[3] instanceof Timestamp) {
                Timestamp timestamp = (Timestamp) row[3];
                LocalDateTime dateTime = timestamp.toLocalDateTime();
                dto.setDepartureTime(dateTime.format(formatter));
            } else {
                dto.setDepartureTime(row[3].toString()); // fallback
            }

            dto.setPassengerName((String) row[4]);
            
            dto.setStatus((String) row[5]);

            result.add(dto);
        }

        return result;
    }

}
