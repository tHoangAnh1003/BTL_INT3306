package com.airline.DTO;

public class BookingDTO {
    private Long bookingId;
    private Long passengerId;
    private Long flightId;
    private Long seatId;
    private String status;

    public BookingDTO(Long bookingId, Long passengerId, Long flightId, Long seatId, String status) {
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.seatId = seatId;
        this.status = status;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
               "bookingId=" + bookingId +
               ", passengerId=" + passengerId +
               ", flightId=" + flightId +
               ", seatId=" + seatId +
               ", status='" + status + '\'' +
               '}';
    }
}
