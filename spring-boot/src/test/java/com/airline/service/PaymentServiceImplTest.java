package com.airline.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.airline.entity.PaymentEntity;
import com.airline.repository.PaymentRepository;
import com.airline.service.impl.PaymentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private PaymentEntity testPayment;

    @BeforeEach
    void setUp() {
        testPayment = new PaymentEntity();
        testPayment.setId(1L);
        testPayment.setAmount(1000000.0);
        testPayment.setStatus("COMPLETED");
        testPayment.setPaymentMethod("CREDIT_CARD");
    }

    @Test
    void testCreatePayment() {
        // Given
        PaymentEntity newPayment = new PaymentEntity();
        newPayment.setAmount(1500000.0);
        newPayment.setStatus("PENDING");
        newPayment.setPaymentMethod("BANK_TRANSFER");

        when(paymentRepository.save(any(PaymentEntity.class))).thenReturn(newPayment);

        // When
        paymentService.createPayment(newPayment);

        // Then
        verify(paymentRepository, times(1)).save(newPayment);
    }

    @Test
    void testFindById_PaymentExists() {
        // Given
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));

        // When
        PaymentEntity result = paymentService.getPaymentById(1L);

        // Then
        assertNotNull(result);
        assertEquals(testPayment.getId(), result.getId());
        assertEquals(testPayment.getAmount(), result.getAmount());
        assertEquals(testPayment.getStatus(), result.getStatus());
        assertEquals(testPayment.getPaymentMethod(), result.getPaymentMethod());
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_PaymentNotExists() {
        // Given
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            paymentService.getPaymentById(1L);
        });
        assertEquals("Payment not found with id: 1", exception.getMessage());
        verify(paymentRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        // Given
        PaymentEntity payment1 = new PaymentEntity();
        payment1.setId(1L);
        payment1.setAmount(1000000.0);
        payment1.setStatus("COMPLETED");
        payment1.setPaymentMethod("CREDIT_CARD");

        PaymentEntity payment2 = new PaymentEntity();
        payment2.setId(2L);
        payment2.setAmount(1500000.0);
        payment2.setStatus("PENDING");
        payment2.setPaymentMethod("BANK_TRANSFER");

        List<PaymentEntity> expectedPayments = Arrays.asList(payment1, payment2);
        when(paymentRepository.findAll()).thenReturn(expectedPayments);

        // When
        List<PaymentEntity> result = paymentService.getAllPayments();

        // Then
        assertEquals(2, result.size());
        assertEquals(expectedPayments, result);
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testUpdatePayment() {
        // Given
        PaymentEntity updatedPayment = new PaymentEntity();
        updatedPayment.setId(1L);
        updatedPayment.setAmount(2000000.0);
        updatedPayment.setStatus("REFUNDED");
        updatedPayment.setPaymentMethod("CREDIT_CARD");

        when(paymentRepository.save(any(PaymentEntity.class))).thenReturn(updatedPayment);

        // When
        paymentService.updatePayment(updatedPayment);

        // Then
        verify(paymentRepository, times(1)).save(updatedPayment);
    }

    @Test
    void testDeletePayment() {
        // Given
        Long paymentId = 1L;
        doNothing().when(paymentRepository).deleteById(paymentId);

        // When
        paymentService.deletePayment(paymentId);

        // Then
        verify(paymentRepository, times(1)).deleteById(paymentId);
    }

    @Test
    void testFindPaymentsByStatus() {
        // Given
        String status = "COMPLETED";
        List<PaymentEntity> expectedPayments = Arrays.asList(testPayment);
        when(paymentRepository.findByStatus(status)).thenReturn(expectedPayments);

        // When
        List<PaymentEntity> result = paymentService.findPaymentsByStatus(status);

        // Then
        assertEquals(expectedPayments, result);
        verify(paymentRepository, times(1)).findByStatus(status);
    }

    @Test
    void testFindPaymentsByUserId() {
        // Given
        Long userId = 1L;
        List<PaymentEntity> expectedPayments = Arrays.asList(testPayment);
        when(paymentRepository.findByUserId(userId)).thenReturn(expectedPayments);

        // When
        List<PaymentEntity> result = paymentService.findPaymentsByUserId(userId);

        // Then
        assertEquals(expectedPayments, result);
        verify(paymentRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testUpdatePaymentStatus() {
        // Given
        Long paymentId = 1L;
        String newStatus = "REFUNDED";
        testPayment.setStatus("COMPLETED");

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(testPayment));
        when(paymentRepository.save(any(PaymentEntity.class))).thenReturn(testPayment);

        // When
        PaymentEntity result = paymentService.updatePaymentStatus(paymentId, newStatus);

        // Then
        assertNotNull(result);
        assertEquals(newStatus, result.getStatus());
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentRepository, times(1)).save(testPayment);
    }

    @Test
    void testValidatePaymentAmount() {
        // Given
        PaymentEntity payment = new PaymentEntity();
        payment.setAmount(-1000.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.validatePaymentAmount(payment);
        });
        assertEquals("Payment amount cannot be negative", exception.getMessage());
    }
} 