package com.airline.service;

import com.airline.DTO.LoginRequest;
import com.airline.DTO.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
