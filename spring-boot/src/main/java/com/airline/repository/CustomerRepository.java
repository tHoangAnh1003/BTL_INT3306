package com.airline.repository;

import java.util.List;
import com.airline.repository.entity.PassengerEntity;

public interface CustomerRepository {
    List<PassengerEntity> findAll();
    PassengerEntity findById(Long id);
    void save(PassengerEntity customer);
    void update(PassengerEntity customer);
    void delete(Long id);
    PassengerEntity findByEmail(String email);
}
