package com.airline.repository;

import com.airline.entity.PaymentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PaymentRepositoryTest {
    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void testSaveAndFindById() {
        PaymentEntity payment = new PaymentEntity();
        payment.setAmount(100.0);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod("CASH");
        payment.setStatus("PAID");
        payment = paymentRepository.save(payment);
        assertNotNull(payment.getId());
        assertEquals(100.0, paymentRepository.findById(payment.getId()).get().getAmount());
    }

    @Test
    void testFindAll() {
        PaymentEntity payment1 = new PaymentEntity();
        payment1.setAmount(100.0);
        payment1.setPaymentDate(LocalDateTime.now());
        payment1.setPaymentMethod("CASH");
        payment1.setStatus("PAID");
        paymentRepository.save(payment1);

        PaymentEntity payment2 = new PaymentEntity();
        payment2.setAmount(200.0);
        payment2.setPaymentDate(LocalDateTime.now());
        payment2.setPaymentMethod("CARD");
        payment2.setStatus("PENDING");
        paymentRepository.save(payment2);

        assertEquals(2, paymentRepository.findAll().size());
    }
} 