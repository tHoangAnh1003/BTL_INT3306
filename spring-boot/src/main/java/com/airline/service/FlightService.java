package com.airline.service;

import com.airline.entity.FlightEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {
    List<FlightEntity> getAllFlights();
    FlightEntity getFlightById(Long id);
    void createFlight(FlightEntity flight);
    void updateFlight(FlightEntity flight);
    void deleteFlight(Long id);
    List<FlightEntity> searchFlights(String departure, String arrival, LocalDate departureDate);
    void delayFlight(Long flightId, LocalDateTime newDepTime, LocalDateTime newArrTime);
}
