package com.airline.service.impl;

import com.airline.repository.FlightRepository;
import com.airline.repository.entity.FlightEntity;
import com.airline.repository.impl.FlightRepositoryImpl;
import com.airline.service.FlightService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl implements FlightService {

	private final FlightRepository flightRepository;
    private final DataSource dataSource;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, DataSource dataSource) {
        this.flightRepository = flightRepository;
        this.dataSource = dataSource;
    }

    @Override
    public List<FlightEntity> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public FlightEntity getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    @Override
    public void createFlight(FlightEntity flight) {
        flightRepository.save(flight);
    }

    @Override
    public void updateFlight(FlightEntity flight) {
        flightRepository.update(flight);
    }

    @Override
    public void deleteFlight(Long id) {
        flightRepository.delete(id);
    }

    @Override
    public List<FlightEntity> searchFlights(String departureAirportName, String arrivalAirportName, LocalDate departureDate) {
        try (Connection connection = dataSource.getConnection()) {
            FlightRepository flightRepository = new FlightRepositoryImpl(connection);
            return flightRepository.searchFlights(departureAirportName, arrivalAirportName, departureDate);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }
    }
}
