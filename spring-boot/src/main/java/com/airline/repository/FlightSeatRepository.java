package com.airline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airline.entity.FlightEntity;
import com.airline.entity.FlightSeatEntity;

@Repository
public interface FlightSeatRepository extends JpaRepository<FlightSeatEntity, Long> {
	List<FlightSeatEntity> findAvailableSeatsByFlightId(Long flightId);

	List<FlightSeatEntity> findByFlightAndIsBookedFalse(FlightEntity flight);
}
