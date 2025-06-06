package com.airline.DTO.booking;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingRequestDTO {
   
    @JsonProperty("flightNumber")
    private String flightNumber;
    
    @JsonProperty("departureDate") 
    private String departureDate;
    
    @JsonProperty("seatId")
    private Long seatId;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("departureAirportCode")
    private String departureAirportCode;
    
    @JsonProperty("arrivalAirportCode")
    private String arrivalAirportCode;
    
    @JsonProperty("departureTime")
    private String departureTime;
    
    @JsonProperty("aircraftModel")
    private String aircraftModel;
    
    // Constructors
    public BookingRequestDTO() {}
    
    public BookingRequestDTO(String flightNumber, String departureDate) {
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
        this.status = "CONFIRMED";
    }
    
    // Getters and Setters
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }
    
    @Override
    public String toString() {
        return "BookingRequestDTO{" +
                "flightNumber='" + flightNumber + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", seatId=" + seatId +
                ", status='" + status + '\'' +
                '}';
    }
}