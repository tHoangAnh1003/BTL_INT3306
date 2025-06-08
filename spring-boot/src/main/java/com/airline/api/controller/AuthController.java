package com.airline.api.controller;

import com.airline.model.LoginRequest;
import com.airline.model.LoginResponse;
import com.airline.model.RefreshTokenRequest;
import com.airline.model.RefreshTokenResponse;
import com.airline.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    // Refresh Token
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        RefreshTokenResponse response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    // Test chức năng admin
    @GetMapping("/admin-only")
    public ResponseEntity<String> adminOnly(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if ("ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.ok("Welcome admin!");
        } else {
            return ResponseEntity.status(403).body("Bạn không có quyền truy cập");
        }
    }
}
