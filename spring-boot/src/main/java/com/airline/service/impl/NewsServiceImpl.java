package com.airline.service.impl;

import com.airline.repository.NewsRepository;
import com.airline.repository.entity.NewsEntity;
import com.airline.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public List<NewsEntity> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public NewsEntity getNewsById(Long id) {
        return newsRepository.findById(id);
    }

    @Override
    public void createNews(NewsEntity news) {
        news.setCreatedAt(LocalDateTime.now());
        newsRepository.save(news);
    }

    @Override
    public void updateNews(NewsEntity news) {
        newsRepository.update(news);
    }

    @Override
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}
