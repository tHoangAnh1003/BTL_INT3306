package com.airline.repository;

import com.airline.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
	List<BookingEntity> findByPassenger_Id(Long passengerId);

	@Query(value = "SELECT b.booking_id, a.model AS aircraft_model, " + " CONCAT(dep.city, ' → ', arr.city) AS route, "
			+ "f.departure_time, p.full_name AS passenger_name " + "FROM bookings b "
			+ "JOIN flights f ON b.flight_id = f.flight_id " + "JOIN aircrafts a ON f.airline_id = a.airline_id "
			+ "JOIN airports dep ON f.departure_airport = dep.airport_id "
			+ "JOIN airports arr ON f.arrival_airport = arr.airport_id "
			+ "JOIN passengers p ON b.passenger_id = p.passenger_id", nativeQuery = true)
	List<Object[]> getBookingStatisticsRaw();

}
