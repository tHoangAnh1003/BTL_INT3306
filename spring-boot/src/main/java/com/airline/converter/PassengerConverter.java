package com.airline.converter;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.airline.builder.PassengerBuilder;
import com.airline.repository.entity.PassengerEntity;
import com.airline.utils.MapUtils;

@Component
public class PassengerConverter {

    public PassengerEntity toPassengerEntity(Map<String, Object> map) {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setPassengerId(MapUtils.getObject(map, "passenger_id", Long.class));
        passenger.setFullName(MapUtils.getObject(map, "full_name", String.class));
        passenger.setEmail(MapUtils.getObject(map, "email", String.class));
        passenger.setPhone(MapUtils.getObject(map, "phone", String.class));
        passenger.setPassportNumber(MapUtils.getObject(map, "passport_number", String.class));
        return passenger;
    }

    public PassengerBuilder toPassengerBuilder(Map<String, Object> map) {
        return new PassengerBuilder.Builder()
            .setPassengerId(MapUtils.getObject(map, "passenger_id", Long.class))
            .setFullName(MapUtils.getObject(map, "full_name", String.class))
            .setEmail(MapUtils.getObject(map, "email", String.class))
            .setPhone(MapUtils.getObject(map, "phone", String.class))
            .setPassportNumber(MapUtils.getObject(map, "passport_number", String.class))
            .build();
    }
}
