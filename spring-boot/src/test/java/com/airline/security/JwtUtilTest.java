package com.airline.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    @Test
    void testGenerateAndValidateToken() {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateAccessToken(1L, "test@example.com", "ADMIN");
        assertNotNull(token);
        assertTrue(jwtUtil.isTokenValid(token));
        assertEquals(1L, jwtUtil.getUserIdFromToken(token));
        assertEquals("test@example.com", jwtUtil.getEmailFromToken(token));
        assertEquals("ADMIN", jwtUtil.getRoleFromToken(token));
    }
} 