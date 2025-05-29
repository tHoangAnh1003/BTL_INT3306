// BookingConverter.java
package com.airline.converter;

import com.airline.DTO.BookingResponseDTO;
import com.airline.entity.BookingEntity;

public class BookingConverter {

    public static BookingResponseDTO toDTO(BookingEntity entity) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(entity.getId());
        dto.setFlightId(entity.getFlight().getId());
        dto.setSeatNumber(entity.getSeat().getSeatNumber());
        dto.setPassengerId(entity.getPassenger().getId());
        dto.setPassengerName(entity.getPassenger().getFullName());
        dto.setBookingDate(entity.getBookingDate());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
