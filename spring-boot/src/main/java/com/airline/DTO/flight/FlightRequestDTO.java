package com.airline.DTO.flight;

import java.time.LocalDateTime;

public class FlightRequestDTO {
    private Long airlineId;
    private String flightNumber;
    private Long departureAirport;
    private Long arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String status;
    
    
	public Long getAirlineId() {
		return airlineId;
	}
	public void setAirlineId(Long airlineId) {
		this.airlineId = airlineId;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public Long getDepartureAirport() {
		return departureAirport;
	}
	public void setDepartureAirport(Long departureAirport) {
		this.departureAirport = departureAirport;
	}
	public Long getArrivalAirport() {
		return arrivalAirport;
	}
	public void setArrivalAirport(Long arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}
	public LocalDateTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

