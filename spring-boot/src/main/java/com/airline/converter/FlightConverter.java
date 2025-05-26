package com.airline.converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.airline.DTO.FlightDTO;
import com.airline.builder.FlightBuilder;
import com.airline.repository.AirportRepository;
import com.airline.repository.entity.FlightEntity;
import com.airline.utils.MapUtils;

@Component
public class FlightConverter {
	
	private final AirportRepository airportRepository;

    public FlightConverter(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

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
    
    public FlightDTO toDTO(FlightEntity flightEntity) {
        FlightDTO dto = new FlightDTO();
        dto.setFlightId(flightEntity.getFlightId());
        dto.setAirlineId(flightEntity.getAirlineId());
        dto.setFlightNumber(flightEntity.getFlightNumber());
        dto.setDepartureTime(flightEntity.getDepartureTime());
        dto.setArrivalTime(flightEntity.getArrivalTime());
        dto.setStatus(flightEntity.getStatus());

        String departureName = airportRepository.findNameById(flightEntity.getDepartureAirport());
        String arrivalName = airportRepository.findNameById(flightEntity.getArrivalAirport());

        dto.setDepartureAirportName(departureName);
        dto.setArrivalAirportName(arrivalName);

        return dto;
    }
    
    public List<FlightDTO> toDTOList(List<FlightEntity> flights) {
        return flights.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
