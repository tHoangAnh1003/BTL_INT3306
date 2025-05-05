package com.airline.builder;

import java.time.LocalDateTime;

import com.airline.DTO.FlightSearchDTO;

public class FlightSearchBuilder {
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureDate;

    public FlightSearchBuilder setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
        return this;
    }

    public FlightSearchBuilder setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
        return this;
    }

    public FlightSearchBuilder setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public FlightSearchDTO build() {
        return new FlightSearchDTO(departureAirport, arrivalAirport, departureDate);
    }
}

