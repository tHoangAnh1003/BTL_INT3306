package com.airline.repository;

import com.airline.entity.FlightEntity;
import com.airline.entity.FlightSeatEntity;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {

	@Query("SELECT f FROM FlightEntity f " + "JOIN FETCH f.departureAirport da " + "JOIN FETCH f.arrivalAirport aa "
			+ "WHERE da.code = :departureCode " + "AND aa.code = :arrivalCode "
			+ "AND f.departureTime BETWEEN :from AND :to")
	List<FlightEntity> searchFlightsByAirportNames(@Param("departureCode") String departureCode,
			@Param("arrivalCode") String arrivalCode, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

	@Query(value = "SELECT * FROM flights f " + "JOIN airlines a ON f.airline_id = a.airline_id "
			+ "JOIN aircrafts ac ON ac.airline_id = a.airline_id " + "WHERE ac.model = :model", nativeQuery = true)
	List<FlightEntity> findByAircraftModel(@Param("model") String model);

	List<FlightEntity> findByFlightNumber(String flightNumber);

	@Query("SELECT f FROM FlightEntity f WHERE f.flightNumber = :flightNumber AND DATE(f.departureTime) = :departureDate")
	Optional<FlightEntity> findByFlightNumberAndDepartureDate(@Param("flightNumber") String flightNumber,
			@Param("departureDate") LocalDate departureDate);

	@Query("SELECT f FROM FlightEntity f WHERE f.flightNumber = :flightNumber ORDER BY f.departureTime ASC")
	List<FlightEntity> findByFlightNumberOrderByDepartureTime(@Param("flightNumber") String flightNumber);

	@Query("SELECT f FROM FlightEntity f " +
		       "WHERE f.departureAirport.code = :departureCode " +
		       "AND f.arrivalAirport.code = :arrivalCode " +
		       "AND f.departureTime BETWEEN :startOfDay AND :endOfDay")
		List<FlightEntity> findByRouteAndDateRange(@Param("departureCode") String departureCode,
		                                           @Param("arrivalCode") String arrivalCode,
		                                           @Param("startOfDay") LocalDateTime startOfDay,
		                                           @Param("endOfDay") LocalDateTime endOfDay);


}
