package com.airline.service;

import com.airline.repository.entity.PassengerEntity;

import java.util.List;

public interface PassengerService {
    List<PassengerEntity> getAllPassengers();
    PassengerEntity getPassengerById(Long id);
    void createPassenger(PassengerEntity passenger);
    PassengerEntity updatePassenger(PassengerEntity passenger);
    void deletePassenger(Long id);
}
