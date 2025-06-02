package com.airline.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.airline.entity.NewsEntity;
import com.airline.repository.NewsRepository;
import com.airline.service.impl.NewsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class NewsServiceImplTest {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsServiceImpl newsService;

    private NewsEntity testNews;

    @BeforeEach
    void setUp() {
        testNews = new NewsEntity();
        testNews.setNewsId(1L);
        testNews.setTitle("Test News Title");
        testNews.setContent("Test news content");
        testNews.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testCreateNews() {
        // Given
        NewsEntity newNews = new NewsEntity();
        newNews.setTitle("New Article");
        newNews.setContent("New article content");

        when(newsRepository.save(any(NewsEntity.class))).thenReturn(newNews);

        // When
        newsService.createNews(newNews);

        // Then
        verify(newsRepository, times(1)).save(newNews);
        // Verify that createNews sets the createdAt field
        assertNotNull(newNews.getCreatedAt());
    }

    @Test
    void testFindById_NewsExists() {
        // Given
        when(newsRepository.findById(1L)).thenReturn(Optional.of(testNews));

        // When
        NewsEntity result = newsService.getNewsById(1L);

        // Then
        assertNotNull(result);
        assertEquals(testNews.getNewsId(), result.getNewsId());
        assertEquals(testNews.getTitle(), result.getTitle());
        assertEquals(testNews.getContent(), result.getContent());
        verify(newsRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NewsNotExists() {
        // Given
        when(newsRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        NewsEntity result = newsService.getNewsById(1L);

        // Then
        assertNull(result);
        verify(newsRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        // Given
        NewsEntity news1 = new NewsEntity();
        news1.setNewsId(1L);
        news1.setTitle("News 1");
        news1.setContent("Content 1");

        NewsEntity news2 = new NewsEntity();
        news2.setNewsId(2L);
        news2.setTitle("News 2");
        news2.setContent("Content 2");

        List<NewsEntity> expectedNews = Arrays.asList(news1, news2);
        when(newsRepository.findAll()).thenReturn(expectedNews);

        // When
        List<NewsEntity> result = newsService.getAllNews();

        // Then
        assertEquals(2, result.size());
        assertEquals(expectedNews, result);
        verify(newsRepository, times(1)).findAll();
    }

    @Test
    void testUpdateNews() {
        // Given
        NewsEntity updatedNews = new NewsEntity();
        updatedNews.setNewsId(1L);
        updatedNews.setTitle("Updated Title");
        updatedNews.setContent("Updated content");
        updatedNews.setCreatedAt(LocalDateTime.now());

        when(newsRepository.save(any(NewsEntity.class))).thenReturn(updatedNews);

        // When
        newsService.updateNews(updatedNews);

        // Then
        verify(newsRepository, times(1)).save(updatedNews);
    }

    @Test
    void testDeleteNews() {
        // Given
        Long newsId = 1L;
        doNothing().when(newsRepository).deleteById(newsId);

        // When
        newsService.deleteNews(newsId);

        // Then
        verify(newsRepository, times(1)).deleteById(newsId);
    }

    @Test
    void testFindNewsByTitle() {
        // Given
        String title = "Test News";
        List<NewsEntity> expectedNews = Arrays.asList(testNews);
        when(newsRepository.findByTitleContaining(title)).thenReturn(expectedNews);

        // When
        List<NewsEntity> result = newsService.findNewsByTitle(title);

        // Then
        assertEquals(expectedNews, result);
        verify(newsRepository, times(1)).findByTitleContaining(title);
    }

    @Test
    void testFindNewsByDateRange() {
        // Given
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();
        List<NewsEntity> expectedNews = Arrays.asList(testNews);
        when(newsRepository.findByCreatedAtBetween(startDate, endDate)).thenReturn(expectedNews);

        // When
        List<NewsEntity> result = newsService.findNewsByDateRange(startDate, endDate);

        // Then
        assertEquals(expectedNews, result);
        verify(newsRepository, times(1)).findByCreatedAtBetween(startDate, endDate);
    }

    @Test
    void testFindLatestNews() {
        // Given
        int limit = 5;
        List<NewsEntity> expectedNews = Arrays.asList(testNews);
        when(newsRepository.findTop5ByOrderByCreatedAtDesc()).thenReturn(expectedNews);

        // When
        List<NewsEntity> result = newsService.findLatestNews(limit);

        // Then
        assertEquals(expectedNews, result);
        verify(newsRepository, times(1)).findTop5ByOrderByCreatedAtDesc();
    }

    @Test
    void testUpdateNewsStatus() {
        // Given
        Long newsId = 1L;
        String newStatus = "PUBLISHED";
        testNews.setStatus("DRAFT");

        when(newsRepository.findById(newsId)).thenReturn(Optional.of(testNews));
        when(newsRepository.save(any(NewsEntity.class))).thenReturn(testNews);

        // When
        NewsEntity result = newsService.updateNewsStatus(newsId, newStatus);

        // Then
        assertNotNull(result);
        assertEquals(newStatus, result.getStatus());
        verify(newsRepository, times(1)).findById(newsId);
        verify(newsRepository, times(1)).save(testNews);
    }
} 