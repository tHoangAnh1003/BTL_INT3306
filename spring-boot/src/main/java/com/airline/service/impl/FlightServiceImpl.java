package com.airline.service.impl;

import com.airline.repository.FlightRepository;
import com.airline.repository.entity.FlightEntity;
import com.airline.service.FlightService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
    private FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
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
}
