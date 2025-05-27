package com.airline.service.impl;

import com.airline.repository.PassengerRepository;
import com.airline.repository.entity.PassengerEntity;
import com.airline.service.PassengerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerServiceImpl implements PassengerService {

	@Autowired
    private PassengerRepository passengerRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public List<PassengerEntity> getAllPassengers() {
        return passengerRepository.findAll();
    }

    @Override
    public PassengerEntity getPassengerById(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found."));
    }

    @Override
    public void createPassenger(PassengerEntity passenger) {
        passengerRepository.save(passenger);
    }

    @Override
    public PassengerEntity updatePassenger(PassengerEntity passenger) {
        passengerRepository.update(passenger);
        return passengerRepository.findById(passenger.getPassengerId())
                                  .orElseThrow(() -> new RuntimeException("Không tìm thấy hành khách sau khi cập nhật"));
    }

    @Override
    public void deletePassenger(Long id) {
        passengerRepository.delete(id);
    }
}
