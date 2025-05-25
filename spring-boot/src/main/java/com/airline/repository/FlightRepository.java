package com.airline.repository;

import com.airline.repository.entity.FlightEntity;

import java.util.List;

public interface FlightRepository {
    List<FlightEntity> findAll();
    FlightEntity findById(Long id);
    void save(FlightEntity flight);
    void update(FlightEntity flight);
    void delete(Long id);
}
