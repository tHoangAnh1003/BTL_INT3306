package com.airline.builder;

import java.time.LocalDateTime;

public class FlightBuilder {
    private Long flightId;
    private Long airlineId;
    private String flightNumber;
    private Long departureAirport;
    private Long arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String status;

    private FlightBuilder(Builder builder) {
        this.flightId = builder.flightId;
        this.airlineId = builder.airlineId;
        this.flightNumber = builder.flightNumber;
        this.departureAirport = builder.departureAirport;
        this.arrivalAirport = builder.arrivalAirport;
        this.departureTime = builder.departureTime;
        this.arrivalTime = builder.arrivalTime;
        this.status = builder.status;
    }

    public static class Builder {
        private Long flightId;
        private Long airlineId;
        private String flightNumber;
        private Long departureAirport;
        private Long arrivalAirport;
        private LocalDateTime departureTime;
        private LocalDateTime arrivalTime;
        private String status;

        public Builder setFlightId(Long flightId) {
            this.flightId = flightId;
            return this;
        }
        public Builder setAirlineId(Long airlineId) {
            this.airlineId = airlineId;
            return this;
        }
        public Builder setFlightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
            return this;
        }
        public Builder setDepartureAirport(Long departureAirport) {
            this.departureAirport = departureAirport;
            return this;
        }
        public Builder setArrivalAirport(Long arrivalAirport) {
            this.arrivalAirport = arrivalAirport;
            return this;
        }
        public Builder setDepartureTime(LocalDateTime departureTime) {
            this.departureTime = departureTime;
            return this;
        }
        public Builder setArrivalTime(LocalDateTime arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }
        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }
        public FlightBuilder build() {
            return new FlightBuilder(this);
        }
    }

    public Long getFlightId() { return flightId; }
    public Long getAirlineId() { return airlineId; }
    public String getFlightNumber() { return flightNumber; }
    public Long getDepartureAirport() { return departureAirport; }
    public Long getArrivalAirport() { return arrivalAirport; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public String getStatus() { return status; }
}
