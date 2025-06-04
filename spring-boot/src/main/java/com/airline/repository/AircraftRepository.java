package com.airline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.entity.AircraftEntity;

public interface AircraftRepository extends JpaRepository<AircraftEntity, Long> {
    
}
