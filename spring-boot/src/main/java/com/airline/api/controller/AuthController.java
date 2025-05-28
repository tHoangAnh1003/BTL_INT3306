package com.airline.api.controller;

import com.airline.repository.entity.UserEntity;
import com.airline.DTO.LoginRequest;
import com.airline.model.LoginResponse;
import com.airline.repository.UserRepository;
import com.airline.security.JwtUtil;
import com.airline.service.AuthService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpStatus;
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
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail());

        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            String accessToken = jwtUtil.generateAccessToken(user.getUserId(), user.getEmail(), user.getRole());
            String refreshToken = jwtUtil.generateRefreshToken(user.getUserId());

            Map<String, Object> response = new HashMap<>();
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);
            response.put("role", user.getRole());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body("Email hoặc mật khẩu không đúng");
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null || !jwtUtil.isTokenValid(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }

        Long userId = jwtUtil.getUserIdFromToken(refreshToken);
        String email = jwtUtil.getEmailFromToken(refreshToken);
        String role = jwtUtil.getRoleFromToken(refreshToken);

        String newAccessToken = jwtUtil.generateAccessToken(userId, email, role);
        
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", newAccessToken);

        return ResponseEntity.ok(response);
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
