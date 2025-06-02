package com.airline.repository;

import com.airline.entity.NewsEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NewsRepositoryTest {
    @Autowired
    private NewsRepository newsRepository;

    @Test
    void testSaveAndFindById() {
        NewsEntity news = new NewsEntity();
        news.setTitle("Tin moi");
        news.setContent("Noi dung");
        news.setCreatedAt(LocalDateTime.now());
        news.setAuthor("admin");
        news = newsRepository.save(news);
        assertNotNull(news.getNewsId());
        assertEquals("Tin moi", newsRepository.findById(news.getNewsId()).get().getTitle());
    }

    @Test
    void testFindAll() {
        NewsEntity news1 = new NewsEntity();
        news1.setTitle("Tin 1");
        news1.setContent("Noi dung 1");
        news1.setCreatedAt(LocalDateTime.now());
        news1.setAuthor("admin");
        newsRepository.save(news1);

        NewsEntity news2 = new NewsEntity();
        news2.setTitle("Tin 2");
        news2.setContent("Noi dung 2");
        news2.setCreatedAt(LocalDateTime.now());
        news2.setAuthor("staff");
        newsRepository.save(news2);

        assertEquals(2, newsRepository.findAll().size());
    }
} 