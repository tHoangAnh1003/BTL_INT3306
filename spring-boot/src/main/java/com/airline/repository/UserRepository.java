package com.airline.repository;

import java.util.List;

import com.airline.repository.entity.UserEntity;

public interface UserRepository {
    void save(UserEntity user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<UserEntity> findAll();
    UserEntity findById(Long id);
    String getRoleByUserId(Long userId);
}
