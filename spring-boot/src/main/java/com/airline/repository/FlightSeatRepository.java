package com.airline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.entity.FlightSeatEntity;

public interface FlightSeatRepository extends JpaRepository<FlightSeatEntity, Long> {
}
