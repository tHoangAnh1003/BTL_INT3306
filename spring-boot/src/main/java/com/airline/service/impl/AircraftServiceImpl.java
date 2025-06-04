package com.airline.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airline.entity.AircraftEntity;
import com.airline.entity.AirlineEntity;
import com.airline.repository.AircraftRepository;
import com.airline.repository.AirlineRepository;
import com.airline.service.AircraftService;

@Service
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;

    public AircraftServiceImpl(AircraftRepository aircraftRepository, AirlineRepository airlineRepository) {
        this.aircraftRepository = aircraftRepository;
        this.airlineRepository = airlineRepository;
    }

    @Override
    @Transactional
    public AircraftEntity createAircraft(String airlineName, String model, int capacity) {
        AirlineEntity airline = airlineRepository.findByName(airlineName);
        if (airline == null) {
            throw new IllegalArgumentException("Airline not found: " + airlineName);
        }

        AircraftEntity aircraft = new AircraftEntity();
        aircraft.setAirline(airline);
        aircraft.setModel(model);
        aircraft.setCapacity(capacity);

        return aircraftRepository.save(aircraft);
    }
}
