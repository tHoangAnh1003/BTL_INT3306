package com.airline.service.impl;

import com.airline.repository.PaymentRepository;
import com.airline.repository.entity.PaymentEntity;
import com.airline.service.PaymentService;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<PaymentEntity> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public PaymentEntity getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public void createPayment(PaymentEntity payment) {
        paymentRepository.save(payment);
    }

    @Override
    public void updatePayment(PaymentEntity payment) {
        paymentRepository.update(payment);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.delete(id);
    }
}
