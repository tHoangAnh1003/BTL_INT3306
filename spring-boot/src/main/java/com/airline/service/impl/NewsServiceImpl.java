package com.airline.service.impl;

import com.airline.repository.NewsRepository;
import com.airline.entity.NewsEntity;
import com.airline.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<NewsEntity> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public NewsEntity getNewsById(Long id) {
        Optional<NewsEntity> optionalNews = newsRepository.findById(id);
        return optionalNews.orElse(null);
    }

    @Override
    public void createNews(NewsEntity news) {
        news.setCreatedAt(LocalDateTime.now());
        newsRepository.save(news);
    }

    @Override
    public void updateNews(NewsEntity news) {
        newsRepository.save(news);
    }

    @Override
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}
