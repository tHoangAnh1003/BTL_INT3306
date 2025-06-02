package com.airline.converter;

import com.airline.entity.PassengerEntity;
import com.airline.DTO.passenger.PassengerDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PassengerConverterTest {
    @Test
    void testToDTO() {
        PassengerEntity entity = new PassengerEntity();
        entity.setId(1L);
        entity.setFullName("Nguyen Van A");
        PassengerConverter converter = new PassengerConverter();
        PassengerDTO dto = converter.toDTO(entity);
        assertEquals(1L, dto.getId());
        assertEquals("Nguyen Van A", dto.getFullName());
    }
} 