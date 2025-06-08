package com.airline.service.impl;

import com.airline.repository.FlightRepository;
import com.airline.entity.FlightEntity;
import com.airline.service.FlightService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<FlightEntity> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public FlightEntity getFlightById(Long id) {
        Optional<FlightEntity> optionalFlight = flightRepository.findById(id);
        return optionalFlight.orElse(null);
    }

    @Override
    public void createFlight(FlightEntity flight) {
        flightRepository.save(flight);
    }

    @Override
    public void updateFlight(FlightEntity flight) {
        flightRepository.save(flight);
    }

    @Override
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    public List<FlightEntity> searchFlights(String departure, String arrival, LocalDate departureDate) {
        LocalDateTime startOfDay = departureDate.atStartOfDay();
        LocalDateTime endOfDay = departureDate.plusDays(1).atStartOfDay();
        return flightRepository.searchFlightsByAirportNames(departure, arrival, startOfDay, endOfDay);
    }
    
    @Override
    @Transactional
    public void delayFlight(Long flightId, LocalDateTime newDepTime, LocalDateTime newArrTime) {
        Optional<FlightEntity> optionalFlight = flightRepository.findById(flightId);
        if (!optionalFlight.isPresent()) {
            throw new RuntimeException("Flight không tồn tại với id = " + flightId);
        }
        FlightEntity flight = optionalFlight.get();

        flight.setDepartureTime(newDepTime);
        flight.setArrivalTime(newArrTime);

        flightRepository.save(flight);
    }

}
