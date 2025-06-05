package com.airline.repository;

import com.airline.entity.AirportEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<AirportEntity, Long> {

    Long findIdByName(String name);

    String findNameById(Long id);
    
    Optional<AirportEntity> findByCity(String city);
    
    @Query("SELECT DISTINCT a.city FROM AirportEntity a")
    List<String> findDistinctCities();
    
    Optional<AirportEntity> findByName(String name);
}
