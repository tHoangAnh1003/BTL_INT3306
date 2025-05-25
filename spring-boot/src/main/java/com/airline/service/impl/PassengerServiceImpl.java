package com.airline.service.impl;

import com.airline.repository.PassengerRepository;
import com.airline.repository.entity.PassengerEntity;
import com.airline.service.PassengerService;

import java.util.List;

public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public List<PassengerEntity> getAllPassengers() {
        return passengerRepository.findAll();
    }

    @Override
    public PassengerEntity getPassengerById(Long id) {
        return passengerRepository.findById(id);
    }

    @Override
    public void createPassenger(PassengerEntity passenger) {
        passengerRepository.save(passenger);
    }

    @Override
    public void updatePassenger(PassengerEntity passenger) {
        passengerRepository.update(passenger);
    }

    @Override
    public void deletePassenger(Long id) {
        passengerRepository.delete(id);
    }
}
