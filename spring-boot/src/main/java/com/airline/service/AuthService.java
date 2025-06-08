package com.airline.service;

import com.airline.model.LoginRequest;
import com.airline.model.LoginResponse;
import com.airline.model.RefreshTokenResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    RefreshTokenResponse refreshToken(String refreshToken);
}
