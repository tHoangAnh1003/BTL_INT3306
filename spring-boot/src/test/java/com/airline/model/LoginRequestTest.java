package com.airline.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {
    @Test
    void testLoginRequest() {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@example.com");
        req.setPassword("123456");
        assertEquals("test@example.com", req.getEmail());
        assertEquals("123456", req.getPassword());
    }
} 