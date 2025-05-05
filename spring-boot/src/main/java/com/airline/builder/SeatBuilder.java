package com.airline.builder;

import com.airline.DTO.SeatDTO;

public class SeatBuilder {
    private Long seatId;
    private String seatNumber;
    private String seatClass;
    private Double price;
    private Boolean isBooked;

    public SeatBuilder setSeatId(Long seatId) {
        this.seatId = seatId;
        return this;
    }

    public SeatBuilder setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public SeatBuilder setSeatClass(String seatClass) {
        this.seatClass = seatClass;
        return this;
    }

    public SeatBuilder setPrice(Double price) {
        this.price = price;
        return this;
    }

    public SeatBuilder setIsBooked(Boolean isBooked) {
        this.isBooked = isBooked;
        return this;
    }

    public SeatDTO build() {
        return new SeatDTO(seatId, seatNumber, seatClass, price, isBooked);
    }
}

