package com.airline.service;

import com.airline.repository.entity.FlightEntity;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    List<FlightEntity> getAllFlights();
    FlightEntity getFlightById(Long id);
    void createFlight(FlightEntity flight);
    void updateFlight(FlightEntity flight);
    void deleteFlight(Long id);
    List<FlightEntity> searchFlights(String departureAirportName, String arrivalAirportName, LocalDate departureDate, Long flightId, String status);
}
