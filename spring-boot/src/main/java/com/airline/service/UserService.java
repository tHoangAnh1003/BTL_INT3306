package com.airline.service;

import java.util.List;

import com.airline.repository.entity.UserEntity;

public interface UserService {
    boolean isUsernameTaken(String username);
    boolean isEmailTaken(String email);
    void registerUser(UserEntity user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void save(UserEntity user);
    List<UserEntity> findAll();
    UserEntity findById(Long id);
}
