package com.airline.api.controller;

import com.airline.entity.NewsEntity;
import com.airline.entity.UserEntity;
import com.airline.security.JwtAuthenticationFilter;
import com.airline.security.JwtAuthenticationFilter_Test;
import com.airline.service.NewsService;
import com.airline.utils.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    // 1. Get All News
    @GetMapping
    public ResponseEntity<List<NewsEntity>> getAll() {
        List<NewsEntity> newsList = newsService.getAllNews();
        return ResponseEntity.ok(newsList);
    }

    // 2. Get News by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        NewsEntity news = newsService.getNewsById(id);
        if (news == null) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("message", "Không tìm thấy tin tức với ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
        return ResponseEntity.ok(news);
    }

    // 3. Create News (admin + staff)
    @PostMapping
    public ResponseEntity<?> create(HttpServletRequest request,
                                    @RequestBody NewsEntity news) {

        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);

        if (!AuthUtil.isAdmin(user) && !AuthUtil.isStaff(user)) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("message", "Bạn không có quyền tạo tin.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resp);
        }

        news.setAuthor(user.getUsername());
        news.setCreatedAt(LocalDateTime.now());
        newsService.createNews(news);

        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Tạo tin thành công");
        resp.put("id", news.getNewsId());
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // 4. Update News (admin + staff)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @PathVariable Long id,
                                    @RequestBody NewsEntity news) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);

        if (!AuthUtil.isAdmin(user) && !AuthUtil.isStaff(user)) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("message", "Bạn không có quyền cập nhật tin.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resp);
        }

        news.setNewsId(id);
        newsService.updateNews(news);

        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Cập nhật tin thành công");
        return ResponseEntity.ok(resp);
    }

    // 5. Delete News (chỉ admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(HttpServletRequest request,
                                    @PathVariable Long id) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);

        if (!AuthUtil.isAdmin(user)) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("message", "Chỉ Admin mới có quyền xóa tin.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resp);
        }

        newsService.deleteNews(id);

        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Xóa tin thành công");
        return ResponseEntity.ok(resp);
    }
}
