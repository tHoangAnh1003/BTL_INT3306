package com.airline.api.controller;


import com.airline.repository.entity.NewsEntity;
import com.airline.service.NewsService;
import com.airline.service.UserService;
import com.airline.repository.entity.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<NewsEntity> getAll() {
        return newsService.getAllNews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        NewsEntity n = newsService.getNewsById(id);
        if (n == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(n);
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestHeader("X-Requester-Id") Long requesterId,
            @RequestBody NewsEntity news) {
        UserEntity user = userService.findById(requesterId);
        String role = user.getRole();
        if (!"Admin".equalsIgnoreCase(role) && !"Staff".equalsIgnoreCase(role)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Bạn không có quyền tạo tin.");
            return ResponseEntity.badRequest().body(response);

        }
        news.setAuthor(user.getUsername());
        newsService.createNews(news);
        Map<String, Object> response = new HashMap<>();
        response.put("msg", "Đã tạo tin thành công");
        response.put("id", news.getNewsId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestHeader("X-Requester-Id") Long requesterId,
            @RequestBody NewsEntity news) {
        UserEntity user = userService.findById(requesterId);
        String role = user.getRole();
        if (!"Admin".equalsIgnoreCase(role) && !"Staff".equalsIgnoreCase(role)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Bạn không có quyền sửa tin.");
            return ResponseEntity.badRequest().body(response);
        }
        news.setNewsId(id);
        newsService.updateNews(news);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Cập nhật tin thành công");
        return ResponseEntity.badRequest().body(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id,
            @RequestHeader("X-Requester-Id") Long requesterId) {
        UserEntity user = userService.findById(requesterId);
        String role = user.getRole();
        if (!"Admin".equalsIgnoreCase(role)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Chỉ Admin mới có quyền xoá tin.");
            return ResponseEntity.badRequest().body(response);

        }
        newsService.deleteNews(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Xoá tin thành công");
        return ResponseEntity.badRequest().body(response);

    }
}
