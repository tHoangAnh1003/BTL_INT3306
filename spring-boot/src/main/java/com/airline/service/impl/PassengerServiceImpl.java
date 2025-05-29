package com.airline.service.impl;

import com.airline.repository.PassengerRepository;
import com.airline.DTO.PassengerDTO;
import com.airline.converter.PassengerConverter;
import com.airline.entity.PassengerEntity;
import com.airline.service.PassengerService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerServiceImpl implements PassengerService {

	@Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PassengerConverter passengerConverter;

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
        return passengerRepository.save(passenger);
    }

    @Override
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }

    @Override
    public PassengerDTO getPassengerDTOById(Long id) {
        return passengerRepository.findById(id)
                .map(passengerConverter::toDTO)
                .orElse(null); // hoặc throw new ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @Override
    public PassengerDTO updatePassenger(Long id, PassengerDTO dto) {
        Optional<PassengerEntity> existingOpt = passengerRepository.findById(id);
        if (existingOpt.isPresent()) {
            return null; // hoặc throw new ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        PassengerEntity existing = existingOpt.get();

        existing.setFullName(dto.getFullName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setPassportNumber(dto.getPassportNumber());

        PassengerEntity saved = passengerRepository.save(existing);
        return passengerConverter.toDTO(saved);
    }
}
