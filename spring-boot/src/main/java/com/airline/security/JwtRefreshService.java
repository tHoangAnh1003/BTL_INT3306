package com.airline.security;


import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class JwtRefreshService {
    private final Map<Long, String> refreshTokenStore = new HashMap<>();

    public void saveRefreshToken(Long userId, String refreshToken) {
        refreshTokenStore.put(userId, refreshToken);
    }

    public boolean isRefreshTokenValid(Long userId, String refreshToken) {
        return refreshToken.equals(refreshTokenStore.get(userId));
    }
}

