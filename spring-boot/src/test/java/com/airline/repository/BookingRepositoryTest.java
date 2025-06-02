package com.airline.repository;

import com.airline.entity.BookingEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookingRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void testSaveAndFindById() {
        BookingEntity booking = new BookingEntity();
        booking.setStatus("CONFIRMED");
        booking.setBookingDate(LocalDateTime.now());
        booking = bookingRepository.save(booking);
        assertNotNull(booking.getId());
        assertEquals("CONFIRMED", bookingRepository.findById(booking.getId()).get().getStatus());
    }

    @Test
    void testFindAll() {
        BookingEntity booking1 = new BookingEntity();
        booking1.setStatus("CONFIRMED");
        booking1.setBookingDate(LocalDateTime.now());
        bookingRepository.save(booking1);

        BookingEntity booking2 = new BookingEntity();
        booking2.setStatus("CANCELLED");
        booking2.setBookingDate(LocalDateTime.now());
        bookingRepository.save(booking2);

        assertEquals(2, bookingRepository.findAll().size());
    }
} 