package com.airline.repository;

import com.airline.repository.entity.PassengerEntity;

import java.util.List;

public interface PassengerRepository {
    List<PassengerEntity> findAll();
    PassengerEntity findById(Long id);
    void save(PassengerEntity passenger);
    void update(PassengerEntity passenger);
    void delete(Long id);
}
