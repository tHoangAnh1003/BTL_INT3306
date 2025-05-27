package com.airline.api.controller;


import com.airline.repository.entity.NewsEntity;
import com.airline.service.NewsService;
import com.airline.service.UserService;
import com.airline.utils.AuthUtil;
import com.airline.repository.entity.UserEntity;
import com.airline.security.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity<?> create(HttpServletRequest request,
                                    @RequestBody NewsEntity news) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        if (!AuthUtil.isAdmin(user) && !AuthUtil.isStaff(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message", "Bạn không có quyền tạo tin."));
        }
        news.setAuthor(user.getUsername());
        newsService.createNews(news);
        Map<String, Object> resp = new HashMap<>();
        resp.put("msg", "Đã tạo tin thành công");
        resp.put("id", news.getNewsId());
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @PathVariable Long id,
                                    @RequestBody NewsEntity news) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        if (!AuthUtil.isAdmin(user) && !AuthUtil.isStaff(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message", "Bạn không có quyền sửa tin."));
        }
        news.setNewsId(id);
        newsService.updateNews(news);
        return ResponseEntity.ok(Collections.singletonMap("message", "Cập nhật tin thành công"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(HttpServletRequest request,
                                    @PathVariable Long id) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        if (!AuthUtil.isAdmin(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message", "Chỉ Admin mới có quyền xoá tin."));
        }
        newsService.deleteNews(id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Xoá tin thành công"));
    }
}
