// BookingConverter.java
package com.airline.converter;

import java.time.format.DateTimeFormatter;

import com.airline.DTO.booking.BookingResponseDTO;
import com.airline.DTO.booking.BookingSummaryDTO;
import com.airline.entity.BookingEntity;
import com.airline.entity.FlightEntity;
import com.airline.entity.FlightSeatEntity;

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
   

    public static BookingSummaryDTO toSummaryDTO(BookingEntity booking) {
        FlightEntity flight = booking.getFlight();
        FlightSeatEntity seat = booking.getSeat();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

        BookingSummaryDTO dto = new BookingSummaryDTO();
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setBookingDate(booking.getBookingDate().format(formatter));

        dto.setRoute(flight.getDepartureAirport().getCity() + " - " + flight.getArrivalAirport().getCity());
        dto.setDepartureTime(flight.getDepartureTime().format(formatter));
        dto.setArrivalTime(flight.getArrivalTime().format(formatter));

        dto.setSeatNumber(seat.getSeatNumber());

        return dto;
    }


}
