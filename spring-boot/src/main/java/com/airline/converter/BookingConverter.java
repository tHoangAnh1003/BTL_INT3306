package com.airline.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.airline.builder.BookingBuilder;
import com.airline.repository.entity.BookingEntity;
import com.airline.utils.MapUtils;

@Component
public class BookingConverter {

    public BookingEntity toBookingEntity(Map<String, Object> map) {
        BookingEntity booking = new BookingBuilder.Builder()
                .setId(MapUtils.getObject(map, "booking_id", Long.class))
                .setPassengerId(MapUtils.getObject(map, "passenger_id", Long.class))
                .setFlightId(MapUtils.getObject(map, "flight_id", Long.class))
                .setSeatId(MapUtils.getObject(map, "seat_id", Long.class))
                .setBookingDate(MapUtils.getObject(map, "booking_date", java.sql.Timestamp.class).toLocalDateTime())
                .setStatus(MapUtils.getObject(map, "status", String.class))
                .build();
        return booking;
    }
}
