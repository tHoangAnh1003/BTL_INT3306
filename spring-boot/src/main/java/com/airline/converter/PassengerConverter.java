package com.airline.converter;

import com.airline.DTO.PassengerDTO;
import com.airline.entity.PassengerEntity;
import org.springframework.stereotype.Component;

@Component
public class PassengerConverter {

    public PassengerDTO toDTO(PassengerEntity entity) {
        if (entity == null) return null;
        PassengerDTO dto = new PassengerDTO();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setPassportNumber(entity.getPassportNumber());
        return dto;
    }

    public PassengerEntity toEntity(PassengerDTO dto) {
        if (dto == null) return null;
        PassengerEntity entity = new PassengerEntity();
        entity.setId(dto.getId());
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassportNumber(dto.getPassportNumber());
        return entity;
    }
}
