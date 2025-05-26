package com.airline.service;

import com.airline.repository.entity.UserEntity;

public interface UserService {
    UserEntity findByUsername(String username);
    boolean isUsernameTaken(String username);
    boolean isEmailTaken(String email);
    void registerUser(UserEntity user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void save(UserEntity user);
}
