package com.airline.DTO;

import java.time.LocalDateTime;

public class FlightSearchDTO {
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureDate;

    public FlightSearchDTO(String departureAirport, String arrivalAirport, LocalDateTime departureDate) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = departureDate;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    @Override
    public String toString() {
        return "FlightSearchDTO{" +
               "departureAirport='" + departureAirport + '\'' +
               ", arrivalAirport='" + arrivalAirport + '\'' +
               ", departureDate=" + departureDate +
               '}';
    }
}

