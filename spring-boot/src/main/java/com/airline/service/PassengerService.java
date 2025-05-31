package com.airline.service;

import com.airline.DTO.passenger.PassengerDTO;
import com.airline.entity.PassengerEntity;

import java.util.List;

public interface PassengerService {
    List<PassengerEntity> getAllPassengers();
    PassengerEntity getPassengerById(Long id);
    void createPassenger(PassengerEntity passenger);
    PassengerEntity updatePassenger(PassengerEntity passenger);
    void deletePassenger(Long id);
    PassengerDTO getPassengerDTOById(Long id);
    PassengerDTO updatePassenger(Long id, PassengerDTO dto);
}
