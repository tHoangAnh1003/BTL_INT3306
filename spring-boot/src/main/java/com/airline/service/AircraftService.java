package com.airline.service;

import com.airline.entity.AircraftEntity;

public interface AircraftService {
    AircraftEntity createAircraft(String airlineName, String model, int capacity);
}
