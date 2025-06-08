package com.airline.converter;

import com.airline.DTO.flight.FlightDTO;
import com.airline.DTO.flight.FlightResponseDTO;
import com.airline.entity.FlightEntity;
import com.airline.entity.AirportEntity;
import com.airline.entity.AirlineEntity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class FlightConverter {
	
//	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

    // Entity -> DTO
	public static FlightResponseDTO toDTO(FlightEntity entity) {
        FlightResponseDTO dto = new FlightResponseDTO();
        dto.setFlightId(entity.getId());
        dto.setFlightNumber(entity.getFlightNumber());
        dto.setDepartureTime(entity.getDepartureTime());
        dto.setArrivalTime(entity.getArrivalTime());
        dto.setStatus(entity.getStatus());

        dto.setDeparture(formatAirport(entity.getDepartureAirport()));
        dto.setArrival(formatAirport(entity.getArrivalAirport()));

        return dto;
    }

    private static String formatAirport(AirportEntity airport) {
        if (airport == null) return "";
        return airport.getCity() + " - " + airport.getCountry();
    } 
    
    public static FlightDTO toDTOR(FlightEntity entity) {
        FlightDTO dto = new FlightDTO();
        dto.setFlightNumber(entity.getFlightNumber());
        
        if (entity.getDepartureAirport() != null) {
            dto.setDepartureAirport(entity.getDepartureAirport().getCity() + " - " + entity.getDepartureAirport().getCountry());
        }

        if (entity.getArrivalAirport() != null) {
            dto.setArrivalAirport(entity.getArrivalAirport().getCity() + " - " + entity.getArrivalAirport().getCountry());
        }
        
        dto.setDepartureTime(entity.getDepartureTime());
        dto.setArrivalTime(entity.getArrivalTime());
        dto.setStatus(entity.getStatus());

        return dto;
    }


    // DTO -> Entity
    public FlightEntity toEntity(FlightDTO dto) {
        if (dto == null) return null;

        FlightEntity entity = new FlightEntity();
        entity.setFlightNumber(dto.getFlightNumber());

        if (dto.getDepartureAirport() != null) {
            AirportEntity departureAirport = new AirportEntity();
            departureAirport.setCode(dto.getDepartureAirport());
            entity.setDepartureAirport(departureAirport);
        }
        if (dto.getArrivalAirport() != null) {
            AirportEntity arrivalAirport = new AirportEntity();
            arrivalAirport.setCode(dto.getArrivalAirport());
            entity.setArrivalAirport(arrivalAirport);
        }

        entity.setDepartureTime(dto.getDepartureTime());
        entity.setArrivalTime(dto.getArrivalTime());
        entity.setStatus(dto.getStatus());

        return entity;
    }
    
//    public static FlightEntity toEntityA(FlightRequestDTO dto, AirportRepository airportRepo) {
//        FlightEntity flight = new FlightEntity();
//        flight.setFlightNumber(dto.getFlightNumber());
//        flight.setStatus(dto.getStatus());
//        flight.setDepartureTime(LocalDateTime.parse(dto.getDepartureTime(), FORMATTER));
//        flight.setArrivalTime(LocalDateTime.parse(dto.getArrivalTime(), FORMATTER));
//
//        AirportEntity departure = airportRepo.findByName(dto.getDeparture())
//            .orElseThrow(() -> new RuntimeException("Departure airport not found: " + dto.getDeparture()));
//        AirportEntity arrival = airportRepo.findByName(dto.getArrival())
//            .orElseThrow(() -> new RuntimeException("Arrival airport not found: " + dto.getArrival()));
//
//        flight.setDepartureAirport(departure);
//        flight.setArrivalAirport(arrival);
//
//        return flight;
//    }

    public List<FlightDTO> toDTOList(List<FlightEntity> entities) {
        if (entities == null) return null;

        List<FlightDTO> dtoList = new ArrayList<>();
        for (FlightEntity entity : entities) {
            FlightDTO dto = FlightConverter.toDTOR(entity); 
            dtoList.add(dto);
        }

        return dtoList;
    }
    public List<FlightEntity> toEntityList(List<FlightDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
