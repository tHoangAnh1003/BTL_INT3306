package com.airline.builder;

import com.airline.repository.entity.BookingEntity;

import java.time.LocalDateTime;

public class BookingBuilder {
    public static class Builder {
        private Long id;
        private Long passengerId;
        private Long flightId;
        private Long seatId;
        private LocalDateTime bookingDate;
        private String status;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setPassengerId(Long passengerId) {
            this.passengerId = passengerId;
            return this;
        }

        public Builder setFlightId(Long flightId) {
            this.flightId = flightId;
            return this;
        }

        public Builder setSeatId(Long seatId) {
            this.seatId = seatId;
            return this;
        }

        public Builder setBookingDate(LocalDateTime bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public BookingEntity build() {
            BookingEntity entity = new BookingEntity();
            entity.setBookingId(this.id);
            entity.setPassengerId(this.passengerId);
            entity.setFlightId(this.flightId);
            entity.setSeatId(this.seatId);
            entity.setBookingDate(this.bookingDate);
            entity.setStatus(this.status);
            return entity;
        }
    }
}
