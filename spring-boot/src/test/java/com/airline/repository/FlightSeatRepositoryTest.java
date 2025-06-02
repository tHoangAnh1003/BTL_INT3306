package com.airline.repository;

import com.airline.entity.FlightSeatEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FlightSeatRepositoryTest {
    @Autowired
    private FlightSeatRepository flightSeatRepository;

    @Test
    void testSaveAndFindById() {
        FlightSeatEntity seat = new FlightSeatEntity();
        seat.setSeatNumber("1A");
        seat.setSeatClass("BUSINESS");
        seat.setPrice(100.0);
        seat.setIsBooked(false);
        seat = flightSeatRepository.save(seat);
        assertNotNull(seat.getId());
        assertEquals("1A", flightSeatRepository.findById(seat.getId()).get().getSeatNumber());
    }

    @Test
    void testFindAll() {
        FlightSeatEntity seat1 = new FlightSeatEntity();
        seat1.setSeatNumber("1A");
        seat1.setSeatClass("BUSINESS");
        seat1.setPrice(100.0);
        seat1.setIsBooked(false);
        flightSeatRepository.save(seat1);

        FlightSeatEntity seat2 = new FlightSeatEntity();
        seat2.setSeatNumber("2B");
        seat2.setSeatClass("ECONOMY");
        seat2.setPrice(50.0);
        seat2.setIsBooked(true);
        flightSeatRepository.save(seat2);

        assertEquals(2, flightSeatRepository.findAll().size());
    }
} 