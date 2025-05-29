package com.airline.converter;

import com.airline.DTO.FlightDTO;
import com.airline.DTO.FlightResponseDTO;
import com.airline.entity.FlightEntity;
import com.airline.entity.AirportEntity;
import com.airline.entity.AirlineEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class FlightConverter {

    // Entity -> DTO
	public static FlightResponseDTO toDTO(FlightEntity entity) {
        FlightResponseDTO dto = new FlightResponseDTO();
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

    public List<FlightDTO> toDTOList(List<FlightEntity> entities) {
        if (entities == null) return null;

        List<FlightDTO> dtoList = new ArrayList<>();
        for (FlightEntity entity : entities) {
            FlightDTO dto = FlightConverter.toDTOR(entity); // hoặc toDTO tùy bạn đặt tên
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
