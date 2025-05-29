package com.airline.repository;

import com.airline.entity.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<AirportEntity, Long> {

    Long findIdByName(String name);

    String findNameById(Long id);
}
