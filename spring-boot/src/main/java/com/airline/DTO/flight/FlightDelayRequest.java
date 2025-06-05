package com.airline.DTO.flight;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FlightDelayRequest {
    private String newDepartureTime; 
    private String newArrivalTime;

//    public LocalDateTime getParsedNewDepartureTime() {
//        try {
//            return LocalDateTime.parse(newDepartureTime); // ISO 8601
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Invalid newDepartureTime format. Expected: yyyy-MM-dd'T'HH:mm:ss");
//        }
//    }
//
//    public LocalDateTime getParsedNewArrivalTime() {
//        try {
//            return LocalDateTime.parse(newArrivalTime); // ISO 8601
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Invalid newArrivalTime format. Expected: yyyy-MM-dd'T'HH:mm:ss");
//        }
//    }
    
    public LocalDateTime getParsedNewDepartureTime() {
        LocalDateTime ldt = LocalDateTime.parse(newDepartureTime); // "2025-06-09T08:00"
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
        return zdt.toLocalDateTime(); 
    }

    public LocalDateTime getParsedNewArrivalTime() {
        LocalDateTime ldt = LocalDateTime.parse(newArrivalTime);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
        return zdt.toLocalDateTime();
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
