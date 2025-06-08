package com.airline.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id", nullable = false)
    @JsonIgnore
    private PassengerEntity passenger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    @JsonIgnore
    private FlightEntity flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    @JsonIgnore
    private FlightSeatEntity seat;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "status")
    private String status;

    // ==== Getter & Setter ====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PassengerEntity getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerEntity passenger) {
        this.passenger = passenger;
    }

    public FlightEntity getFlight() {
        return flight;
    }

    public void setFlight(FlightEntity flight) {
        this.flight = flight;
    }

    public FlightSeatEntity getSeat() {
        return seat;
    }

    public void setSeat(FlightSeatEntity seat) {
        this.seat = seat;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ==== Convenience methods for ID access (DTO-friendly) ====

    public void setPassengerId(Long passengerId) {
        if (this.passenger == null) {
            this.passenger = new PassengerEntity();
        }
        this.passenger.setId(passengerId);
    }

    public Long getPassengerId() {
        return (passenger != null) ? passenger.getId() : null;
    }

    public void setFlightId(Long flightId) {
        if (this.flight == null) {
            this.flight = new FlightEntity();
        }
        this.flight.setId(flightId);
    }

    public Long getFlightId() {
        return (flight != null) ? flight.getId() : null;
    }

    public void setSeatId(Long seatId) {
        if (this.seat == null) {
            this.seat = new FlightSeatEntity();
        }
        this.seat.setId(seatId);
    }

    public Long getSeatId() {
        return (seat != null) ? seat.getId() : null;
    }
}
