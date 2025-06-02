package com.airline.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airline.entity.FlightEntity;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
	List<FlightEntity> findByStatus(String status);
	List<FlightEntity> searchFlightsByAirportNames(String departure, String arrival, LocalDateTime startTime, LocalDateTime endTime);
}
