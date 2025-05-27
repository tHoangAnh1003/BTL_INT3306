package com.airline.api.controller;

import com.airline.repository.entity.UserEntity;
import com.airline.DTO.LoginRequest;
import com.airline.model.LoginResponse;
import com.airline.repository.UserRepository;
import com.airline.security.JwtUtil;
import com.airline.service.AuthService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail());

        if (user != null && user.getPasswordHash().equals(loginRequest.getPassword())) {
            String token = jwtUtil.generateToken(user.getUserId(), user.getRole());
            return ResponseEntity.ok(new LoginResponse(token, user.getRole()));
        }

        return ResponseEntity.status(401).body("Email hoặc mật khẩu không đúng");
    }
    
 // Test
    @GetMapping("/admin-only")
    public ResponseEntity<?> adminOnly(HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getAttribute("authenticatedUser");
        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.ok("Welcome admin!");
        } else {
            return ResponseEntity.status(403).body("Bạn không có quyền truy cập");
        }
    }
}
