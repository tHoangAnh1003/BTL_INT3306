package com.airline.repository;

import java.util.List;
import com.airline.repository.entity.PaymentEntity;

public interface PaymentRepository {
    void addPayment(PaymentEntity payment);
    PaymentEntity getPaymentById(Long id);
    List<PaymentEntity> getAllPayments();
    void updatePayment(PaymentEntity payment);
    void deletePayment(Long id);
    List<PaymentEntity> getPaymentsByBookingId(Long bookingId);
}
