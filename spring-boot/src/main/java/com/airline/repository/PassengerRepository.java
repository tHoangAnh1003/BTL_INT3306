package com.airline.repository;

import com.airline.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {
}
