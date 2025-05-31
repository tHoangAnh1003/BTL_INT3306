package com.airline.service;

import java.util.List;

import com.airline.entity.UserEntity;

public interface UserService {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void registerUser(UserEntity user);
    void save(UserEntity user);
    List<UserEntity> findAll();
    UserEntity findById(Long id);
}
