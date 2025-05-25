package com.airline.repository;

import com.airline.repository.entity.PaymentEntity;

import java.util.List;

public interface PaymentRepository {
    List<PaymentEntity> findAll();
    PaymentEntity findById(Long id);
    void save(PaymentEntity payment);
    void update(PaymentEntity payment);
    void delete(Long id);
}
