package com.airline.converter;

import com.airline.entity.PaymentEntity;
import com.airline.DTO.payment.PaymentDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentConverterTest {
    @Test
    void testToDTO() {
        PaymentEntity entity = new PaymentEntity();
        entity.setId(1L);
        entity.setStatus("PAID");
        PaymentConverter converter = new PaymentConverter();
        PaymentDTO dto = converter.toDTO(entity);
        assertEquals(1L, dto.getId());
        assertEquals("PAID", dto.getStatus());
    }
} 