package com.airline.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.airline.builder.BookingBuilder;
import com.airline.utils.MapUtils;

@Component
public class BookingSearchBuilderConverter {

    // Giả sử booking_date trong map là String ISO hoặc timestamp, cần parse
    private LocalDateTime parseDate(Object obj) {
        if (obj == null) return null;
        if (obj instanceof LocalDateTime) return (LocalDateTime) obj;
        if (obj instanceof String) {
            return LocalDateTime.parse((String) obj, DateTimeFormatter.ISO_DATE_TIME);
        }
        return null;
    }

    public BookingBuilder toBookingBuilder(Map<String, Object> map) {
        return new BookingBuilder.Builder()
            .setId(MapUtils.getObject(map, "booking_id", Long.class))  // Theo DB là booking_id
            .setPassengerId(MapUtils.getObject(map, "passenger_id", Long.class))
            .setFlightId(MapUtils.getObject(map, "flight_id", Long.class))
            .setSeatId(MapUtils.getObject(map, "seat_id", Long.class))
            .setBookingDate(parseDate(map.get("booking_date")))
            .setStatus(MapUtils.getObject(map, "status", String.class))
            .build();
    }
}

