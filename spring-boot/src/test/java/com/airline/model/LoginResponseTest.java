package com.airline.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginResponseTest {
    @Test
    void testLoginResponse() {
        LoginResponse res = new LoginResponse("access", "refresh", "ADMIN");
        assertEquals("access", res.getAccessToken());
        assertEquals("refresh", res.getRefreshToken());
        assertEquals("ADMIN", res.getRole());
    }
} 