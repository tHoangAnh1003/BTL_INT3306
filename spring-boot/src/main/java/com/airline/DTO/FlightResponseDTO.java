package com.airline.DTO;

import java.time.LocalDateTime;

public class FlightResponseDTO {
    private String flightNumber;
    private String departure; // "Bangkok - Thailand"
    private String arrival;   // "Kuala Lumpur - Malaysia"
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String status;
    
	public String getFlightNumber() {
		return flightNumber;
	}
	
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	
	public String getDeparture() {
		return departure;
	}
	
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	
	public String getArrival() {
		return arrival;
	}
	
	public void setArrival(String arrival) {
		this.arrival = arrival;
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

