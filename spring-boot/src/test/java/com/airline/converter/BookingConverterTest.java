package com.airline.converter;

import com.airline.entity.BookingEntity;
import com.airline.DTO.booking.BookingDTO;
import com.airline.DTO.booking.BookingResponseDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookingConverterTest {
    @Test
    void testToDTO() {
        BookingEntity entity = new BookingEntity();
        entity.setId(1L);
        entity.setStatus("CONFIRMED");
        BookingResponseDTO dto = BookingConverter.toDTO(entity);
        assertEquals(1L, dto.getId());
        assertEquals("CONFIRMED", dto.getStatus());
    }
} 