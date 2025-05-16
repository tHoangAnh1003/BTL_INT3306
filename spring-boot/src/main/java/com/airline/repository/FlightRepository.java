package com.airline.repository;

import java.util.List;

import com.airline.repository.entity.FlightEntity;

public interface FlightRepository {
    List<FlightEntity> findAll();
    FlightEntity findById(Long id);
    void save(FlightEntity flight);
    void update(FlightEntity flight);
    void delete(Long id);
}

