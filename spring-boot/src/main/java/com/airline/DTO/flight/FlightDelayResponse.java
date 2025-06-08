package com.airline.DTO.flight;


public class FlightDelayResponse {
    private String message;
    private String newDepartureTime;
    private String newArrivalTime;

    public FlightDelayResponse(String message, String newDepartureTime, String newArrivalTime) {
        this.message = message;
        this.newDepartureTime = newDepartureTime;
        this.newArrivalTime = newArrivalTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

