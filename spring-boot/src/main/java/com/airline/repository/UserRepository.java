package com.airline.repository;

import com.airline.repository.entity.UserEntity;

public interface UserRepository {
    UserEntity findByUsername(String username);
    void save(UserEntity user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
