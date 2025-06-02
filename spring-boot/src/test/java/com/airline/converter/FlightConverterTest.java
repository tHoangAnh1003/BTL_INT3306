package com.airline.converter;

import com.airline.entity.FlightEntity;
import com.airline.DTO.flight.FlightResponseDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FlightConverterTest {
    @Test
    void testToDTO() {
        FlightEntity entity = new FlightEntity();
        entity.setId(1L);
        entity.setFlightNumber("VN123");
        FlightResponseDTO dto = FlightConverter.toDTO(entity);
        assertEquals(1L, dto.getFlightID());
        assertEquals("VN123", dto.getFlightNumber());
    }
} 