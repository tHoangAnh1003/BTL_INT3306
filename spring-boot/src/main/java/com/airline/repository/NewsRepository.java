package com.airline.repository;

import java.util.List;
import com.airline.repository.entity.NewsEntity;

public interface NewsRepository {
    List<NewsEntity> findAll();
    NewsEntity findById(Long id);
    void save(NewsEntity news);
    void update(NewsEntity news);
    void deleteById(Long id);
}
