package com.airline.repository;

import com.airline.entity.FlightEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FlightRepositoryTest {
    @Autowired
    private FlightRepository flightRepository;

    @Test
    void testSaveAndFindById() {
        FlightEntity flight = new FlightEntity();
        flight.setFlightNumber("VN123");
        flight.setDepartureTime(LocalDateTime.now());
        flight.setArrivalTime(LocalDateTime.now().plusHours(2));
        flight.setStatus("ON_TIME");
        flight = flightRepository.save(flight);
        assertNotNull(flight.getId());
        assertEquals("VN123", flightRepository.findById(flight.getId()).get().getFlightNumber());
    }

    @Test
    void testFindAll() {
        FlightEntity flight1 = new FlightEntity();
        flight1.setFlightNumber("VN123");
        flight1.setStatus("ON_TIME");
        flight1.setDepartureTime(LocalDateTime.now());
        flight1.setArrivalTime(LocalDateTime.now().plusHours(2));
        flightRepository.save(flight1);

        FlightEntity flight2 = new FlightEntity();
        flight2.setFlightNumber("VN456");
        flight2.setStatus("DELAYED");
        flight2.setDepartureTime(LocalDateTime.now());
        flight2.setArrivalTime(LocalDateTime.now().plusHours(3));
        flightRepository.save(flight2);

        assertEquals(2, flightRepository.findAll().size());
    }
} 