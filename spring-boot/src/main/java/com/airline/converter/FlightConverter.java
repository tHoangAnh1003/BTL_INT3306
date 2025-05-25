package com.airline.converter;

import java.util.Map;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.airline.builder.FlightBuilder;
import com.airline.repository.entity.FlightEntity;
import com.airline.utils.MapUtils;

@Component
public class FlightConverter {

    public FlightEntity toFlightEntity(Map<String, Object> map) {
        FlightEntity flight = new FlightEntity();
        flight.setFlightId(MapUtils.getObject(map, "flight_id", Long.class));
        flight.setAirlineId(MapUtils.getObject(map, "airline_id", Long.class));
        flight.setFlightNumber(MapUtils.getObject(map, "flight_number", String.class));
        flight.setDepartureAirport(MapUtils.getObject(map, "departure_airport", Long.class));
        flight.setArrivalAirport(MapUtils.getObject(map, "arrival_airport", Long.class));
        
        Timestamp depTime = MapUtils.getObject(map, "departure_time", Timestamp.class);
        flight.setDepartureTime(depTime != null ? depTime.toLocalDateTime() : null);
        
        Timestamp arrTime = MapUtils.getObject(map, "arrival_time", Timestamp.class);
        flight.setArrivalTime(arrTime != null ? arrTime.toLocalDateTime() : null);
        
        flight.setStatus(MapUtils.getObject(map, "status", String.class));
        return flight;
    }

    public FlightBuilder toFlightBuilder(Map<String, Object> map) {
        Timestamp depTime = MapUtils.getObject(map, "departure_time", Timestamp.class);
        Timestamp arrTime = MapUtils.getObject(map, "arrival_time", Timestamp.class);

        return new FlightBuilder.Builder()
                .setFlightId(MapUtils.getObject(map, "flight_id", Long.class))
                .setAirlineId(MapUtils.getObject(map, "airline_id", Long.class))
                .setFlightNumber(MapUtils.getObject(map, "flight_number", String.class))
                .setDepartureAirport(MapUtils.getObject(map, "departure_airport", Long.class))
                .setArrivalAirport(MapUtils.getObject(map, "arrival_airport", Long.class))
                .setDepartureTime(depTime != null ? depTime.toLocalDateTime() : null)
                .setArrivalTime(arrTime != null ? arrTime.toLocalDateTime() : null)
                .setStatus(MapUtils.getObject(map, "status", String.class))
                .build();
    }
}
