package com.airline.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenRequestTest {
    @Test
    void testRefreshTokenRequest() {
        RefreshTokenRequest req = new RefreshTokenRequest();
        req.setRefreshToken("refresh");
        assertEquals("refresh", req.getRefreshToken());
    }
} 