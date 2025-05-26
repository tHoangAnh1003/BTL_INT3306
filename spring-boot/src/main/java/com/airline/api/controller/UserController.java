package com.airline.api.controller;

import com.airline.repository.entity.UserEntity;
import com.airline.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 1. Create new User
    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        if (userService.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userService.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        userService.save(user);
        return user;
    }
    
    @GetMapping
    public List<UserEntity> getAllUsers(@RequestParam("requesterId") Long requesterId) {
        UserEntity requester = userService.findById(requesterId);
        if (requester == null) {
            throw new RuntimeException("Requester not found");
        }
        if (!"ADMIN".equalsIgnoreCase(requester.getRole())) {
            throw new RuntimeException("Access denied. Only ADMIN can view all users.");
        }
        return userService.findAll();
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userService.findById(id);
    } 
}
