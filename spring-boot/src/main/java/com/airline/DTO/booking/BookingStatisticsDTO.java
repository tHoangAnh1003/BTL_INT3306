package com.airline.DTO.booking;

import java.time.LocalDateTime;

public class BookingStatisticsDTO {
    private String aircraftModel;
    private String route; // HaNoi - Ho Chi Minh city, Hanoi - kuala lumpur
    private String departureTime;
    private String passengerName;

    // Getters and setters
    public String getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
}
