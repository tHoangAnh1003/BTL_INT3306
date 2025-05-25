package com.airline.DTO;

import java.time.LocalDateTime;

public class FlightDTO {
    private Long id;
    private Long airlineId;
    private String flightNumber;
    private Long departureAirportId;
    private Long arrivalAirportId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAirlineId() { return airlineId; }
    public void setAirlineId(Long airlineId) { this.airlineId = airlineId; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public Long getDepartureAirportId() { return departureAirportId; }
    public void setDepartureAirportId(Long departureAirportId) { this.departureAirportId = departureAirportId; }

    public Long getArrivalAirportId() { return arrivalAirportId; }
    public void setArrivalAirportId(Long arrivalAirportId) { this.arrivalAirportId = arrivalAirportId; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
