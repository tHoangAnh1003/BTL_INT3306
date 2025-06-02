package com.airline.DTO.flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class FlightDelayRequest {
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime newDepartureTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime newArrivalTime;

    public LocalDateTime getNewDepartureTime() {
        return newDepartureTime;
    }

    public void setNewDepartureTime(LocalDateTime newDepartureTime) {
        this.newDepartureTime = newDepartureTime;
    }

    public LocalDateTime getNewArrivalTime() {
        return newArrivalTime;
    }

    public void setNewArrivalTime(LocalDateTime newArrivalTime) {
        this.newArrivalTime = newArrivalTime;
    }
}
