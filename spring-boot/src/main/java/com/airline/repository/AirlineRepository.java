package com.airline.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.entity.AirlineEntity;

public interface AirlineRepository extends JpaRepository<AirlineEntity, Long> {
    AirlineEntity findByName(String name);
}