package com.airline.repository;

import com.airline.repository.entity.PassengerEntity;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository {
    List<PassengerEntity> findAll();
    Optional<PassengerEntity> findById(Long id);
    void save(PassengerEntity passenger);
    void update(PassengerEntity passenger);
    void delete(Long id);
}
