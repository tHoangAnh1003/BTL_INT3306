package com.airline.service;

import java.util.List;
import com.airline.repository.entity.NewsEntity;

public interface NewsService {
    List<NewsEntity> getAllNews();
    NewsEntity getNewsById(Long id);
    void createNews(NewsEntity news);
    void updateNews(NewsEntity news);
    void deleteNews(Long id);
}
