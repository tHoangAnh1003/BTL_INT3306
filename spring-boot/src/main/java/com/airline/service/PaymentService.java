package com.airline.service;

import com.airline.entity.PaymentEntity;

import java.util.List;

public interface PaymentService {
    List<PaymentEntity> getAllPayments();
    PaymentEntity getPaymentById(Long id);
    void createPayment(PaymentEntity payment);
    void updatePayment(PaymentEntity payment);
    void deletePayment(Long id);
}
