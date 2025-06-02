package com.airline.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.airline.entity.UserEntity;
import com.airline.model.LoginRequest;
import com.airline.model.LoginResponse;
import com.airline.model.RefreshTokenResponse;
import com.airline.repository.UserRepository;
import com.airline.security.JwtUtil;
import com.airline.service.impl.AuthServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    private UserEntity testUser;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setPasswordHash("hashedpassword");
        testUser.setRole("USER");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");
    }

    @Test
    void testLogin_Success() {
        // Given
        String accessToken = "access-token";
        String refreshToken = "refresh-token";

        when(userRepository.findByEmail("test@example.com")).thenReturn(testUser);
        when(passwordEncoder.matches("password123", "hashedpassword")).thenReturn(true);
        when(jwtUtil.generateAccessToken(1L, "test@example.com", "USER")).thenReturn(accessToken);
        when(jwtUtil.generateRefreshToken(1L)).thenReturn(refreshToken);

        // When
        LoginResponse result = authService.login(loginRequest);

        // Then
        assertNotNull(result);
        assertEquals(accessToken, result.getAccessToken());
        assertEquals(refreshToken, result.getRefreshToken());
        assertEquals("USER", result.getRole());

        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(passwordEncoder, times(1)).matches("password123", "hashedpassword");
        verify(jwtUtil, times(1)).generateAccessToken(1L, "test@example.com", "USER");
        verify(jwtUtil, times(1)).generateRefreshToken(1L);
    }

    @Test
    void testLogin_UserNotFound() {
        // Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(loginRequest);
        });
        assertEquals("Email hoặc mật khẩu không đúng", exception.getMessage());

        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtUtil, never()).generateAccessToken(anyLong(), anyString(), anyString());
        verify(jwtUtil, never()).generateRefreshToken(anyLong());
    }

    @Test
    void testLogin_WrongPassword() {
        // Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(testUser);
        when(passwordEncoder.matches("password123", "hashedpassword")).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(loginRequest);
        });
        assertEquals("Email hoặc mật khẩu không đúng", exception.getMessage());

        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(passwordEncoder, times(1)).matches("password123", "hashedpassword");
        verify(jwtUtil, never()).generateAccessToken(anyLong(), anyString(), anyString());
        verify(jwtUtil, never()).generateRefreshToken(anyLong());
    }

    @Test
    void testRefreshToken_Success() {
        // Given
        String refreshToken = "valid-refresh-token";
        String newAccessToken = "new-access-token";

        when(jwtUtil.isTokenValid(refreshToken)).thenReturn(true);
        when(jwtUtil.getUserIdFromToken(refreshToken)).thenReturn(1L);
        when(jwtUtil.getEmailFromToken(refreshToken)).thenReturn("test@example.com");
        when(jwtUtil.getRoleFromToken(refreshToken)).thenReturn("USER");
        when(jwtUtil.generateAccessToken(1L, "test@example.com", "USER")).thenReturn(newAccessToken);

        // When
        RefreshTokenResponse result = authService.refreshToken(refreshToken);

        // Then
        assertNotNull(result);
        assertEquals(newAccessToken, result.getAccessToken());

        verify(jwtUtil, times(1)).isTokenValid(refreshToken);
        verify(jwtUtil, times(1)).getUserIdFromToken(refreshToken);
        verify(jwtUtil, times(1)).getEmailFromToken(refreshToken);
        verify(jwtUtil, times(1)).getRoleFromToken(refreshToken);
        verify(jwtUtil, times(1)).generateAccessToken(1L, "test@example.com", "USER");
    }

    @Test
    void testRefreshToken_InvalidToken() {
        // Given
        String invalidRefreshToken = "invalid-refresh-token";

        when(jwtUtil.isTokenValid(invalidRefreshToken)).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.refreshToken(invalidRefreshToken);
        });
        assertEquals("Refresh token không hợp lệ hoặc đã hết hạn", exception.getMessage());

        verify(jwtUtil, times(1)).isTokenValid(invalidRefreshToken);
        verify(jwtUtil, never()).getUserIdFromToken(anyString());
        verify(jwtUtil, never()).getEmailFromToken(anyString());
        verify(jwtUtil, never()).getRoleFromToken(anyString());
        verify(jwtUtil, never()).generateAccessToken(anyLong(), anyString(), anyString());
    }

    @Test
    void testRegister_Success() {
        // Given
        UserEntity newUser = new UserEntity();
        newUser.setEmail("newuser@example.com");
        newUser.setPasswordHash("password123");
        newUser.setRole("USER");

        when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(newUser.getPasswordHash())).thenReturn("hashedpassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(newUser);

        // When
        UserEntity result = authService.register(newUser);

        // Then
        assertNotNull(result);
        assertEquals(newUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).existsByEmail(newUser.getEmail());
        verify(passwordEncoder, times(1)).encode(newUser.getPasswordHash());
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testRegister_DuplicateEmail() {
        // Given
        UserEntity newUser = new UserEntity();
        newUser.setEmail("existing@example.com");
        newUser.setPasswordHash("password123");

        when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.register(newUser);
        });
        assertEquals("Email already exists", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail(newUser.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testRegister_InvalidPassword() {
        // Given
        UserEntity newUser = new UserEntity();
        newUser.setEmail("newuser@example.com");
        newUser.setPasswordHash("123"); // Too short password

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(newUser);
        });
        assertEquals("Password must be at least 8 characters long", exception.getMessage());
        verify(userRepository, never()).existsByEmail(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testLogout() {
        // Given
        String token = "valid-token";
        when(jwtUtil.isTokenValid(token)).thenReturn(true);

        // When
        authService.logout(token);

        // Then
        verify(jwtUtil, times(1)).isTokenValid(token);
        // Add verification for token blacklisting if implemented
    }

    @Test
    void testValidateToken() {
        // Given
        String token = "valid-token";
        when(jwtUtil.isTokenValid(token)).thenReturn(true);
        when(jwtUtil.getUserIdFromToken(token)).thenReturn(1L);
        when(jwtUtil.getEmailFromToken(token)).thenReturn("test@example.com");
        when(jwtUtil.getRoleFromToken(token)).thenReturn("USER");

        // When
        boolean result = authService.validateToken(token);

        // Then
        assertTrue(result);
        verify(jwtUtil, times(1)).isTokenValid(token);
        verify(jwtUtil, times(1)).getUserIdFromToken(token);
        verify(jwtUtil, times(1)).getEmailFromToken(token);
        verify(jwtUtil, times(1)).getRoleFromToken(token);
    }

    @Test
    void testChangePassword() {
        // Given
        Long userId = 1L;
        String oldPassword = "oldpassword";
        String newPassword = "newpassword";
        String hashedOldPassword = "hashedoldpassword";
        String hashedNewPassword = "hashednewpassword";

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(oldPassword, testUser.getPasswordHash())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn(hashedNewPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

        // When
        authService.changePassword(userId, oldPassword, newPassword);

        // Then
        verify(userRepository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).matches(oldPassword, testUser.getPasswordHash());
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(userRepository, times(1)).save(testUser);
    }
} 