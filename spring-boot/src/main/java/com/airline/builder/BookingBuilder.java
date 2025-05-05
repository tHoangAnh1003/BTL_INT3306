package com.airline.builder;

import com.airline.DTO.BookingDTO;

public class BookingBuilder {
    private Long bookingId;
    private Long passengerId;
    private Long flightId;
    private Long seatId;
    private String status;

    public BookingBuilder setBookingId(Long bookingId) {
        this.bookingId = bookingId;
        return this;
    }

    public BookingBuilder setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
        return this;
    }

    public BookingBuilder setFlightId(Long flightId) {
        this.flightId = flightId;
        return this;
    }

    public BookingBuilder setSeatId(Long seatId) {
        this.seatId = seatId;
        return this;
    }

    public BookingBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public BookingDTO build() {
        return new BookingDTO(bookingId, passengerId, flightId, seatId, status);
    }
}

