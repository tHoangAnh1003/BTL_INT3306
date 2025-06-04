package com.airline.DTO.flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FlightDelayRequest {
    private String newDepartureTime; 
    private String newArrivalTime;

    

    public LocalDateTime getParsedNewDepartureTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        return LocalDateTime.parse(newDepartureTime, formatter);
    }

    public LocalDateTime getParsedNewArrivalTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        return LocalDateTime.parse(newArrivalTime, formatter);
    }

	public String getNewDepartureTime() {
		return newDepartureTime;
	}

	public void setNewDepartureTime(String newDepartureTime) {
		this.newDepartureTime = newDepartureTime;
	}

	public String getNewArrivalTime() {
		return newArrivalTime;
	}

	public void setNewArrivalTime(String newArrivalTime) {
		this.newArrivalTime = newArrivalTime;
	}
}
