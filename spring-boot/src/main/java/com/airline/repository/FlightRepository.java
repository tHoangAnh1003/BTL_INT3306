package com.airline.repository;

import com.airline.entity.FlightEntity;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {

	@Query("SELECT f FROM FlightEntity f " +
		       "JOIN f.departureAirport da " +
		       "JOIN f.arrivalAirport aa " +
		       "WHERE da.name = :departure AND aa.name = :arrival " +
		       "AND f.departureTime BETWEEN :startOfDay AND :endOfDay")
		List<FlightEntity> searchFlightsByAirportNames(
		        @Param("departure") String departure,
		        @Param("arrival") String arrival,
		        @Param("startOfDay") LocalDateTime startOfDay,
		        @Param("endOfDay") LocalDateTime endOfDay
		);

}
