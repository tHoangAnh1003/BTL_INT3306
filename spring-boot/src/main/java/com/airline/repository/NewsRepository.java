package com.airline.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airline.entity.NewsEntity;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    List<NewsEntity> findByTitleContaining(String title);
    List<NewsEntity> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<NewsEntity> findTop5ByOrderByCreatedAtDesc();
}
