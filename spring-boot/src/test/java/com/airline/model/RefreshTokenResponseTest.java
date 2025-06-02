package com.airline.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenResponseTest {
    @Test
    void testRefreshTokenResponse() {
        RefreshTokenResponse res = new RefreshTokenResponse("access");
        assertEquals("access", res.getAccessToken());
    }
} 