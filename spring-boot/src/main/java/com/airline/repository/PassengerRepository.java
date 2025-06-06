package com.airline.repository;

import com.airline.entity.PassengerEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {
	Optional<PassengerEntity> findByUserId(Long id);
}
