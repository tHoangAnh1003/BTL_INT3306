package com.airline.service.impl;

import com.airline.DTO.LoginRequest;
import com.airline.DTO.LoginResponse;
import com.airline.repository.UserRepository;
import com.airline.repository.entity.UserEntity;
import com.airline.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public LoginResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail());
        if (user != null && user.getPasswordHash().equals(request.getPassword())) {
            return new LoginResponse(user.getUserId(), user.getEmail(), user.getRole(), "Đăng nhập thành công");
        } else {
            return new LoginResponse(null, null, null, "Email hoặc mật khẩu không đúng");
        }
    }
}
