package com.airline.api.controller;

import com.airline.entity.UserEntity;
import com.airline.security.JwtAuthenticationFilter;
import com.airline.service.UserService;
import com.airline.utils.AuthUtil;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 1. Create new User
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserEntity user) {
        Map<String, Object> resp = new HashMap<>();

        if (userService.existsByUsername(user.getUsername())) {
            resp.put("message", "Tên người dùng đã tồn tại.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
        }

        if (userService.existsByEmail(user.getEmail())) {
            resp.put("message", "Email đã tồn tại.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
        }

        userService.save(user);
        resp.put("message", "Tạo người dùng thành công.");
        resp.put("userId", user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // 2. Get all users (only ADMIN)
    @GetMapping
    public ResponseEntity<?> getAllUsers(HttpServletRequest request) {
        UserEntity requester = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);

        if (!AuthUtil.isAdmin(requester)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message", "Chỉ Admin được phép xem danh sách người dùng."));
        }

        List<UserEntity> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    // 3. Get specific user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(HttpServletRequest request, @PathVariable Long id) {
        UserEntity requester = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);

        // Cho phép Admin hoặc chính chủ được xem
        if (!AuthUtil.isAdmin(requester) && !requester.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message", "Bạn chỉ được xem thông tin của chính mình."));
        }

        UserEntity user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Không tìm thấy người dùng."));
        }

        return ResponseEntity.ok(user);
    }
}
