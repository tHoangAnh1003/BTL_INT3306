package com.airline.api.controller;

import com.airline.repository.entity.UserEntity;
import com.airline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 1. Get User by Name
    @GetMapping("/{username}")
    public UserEntity getUserByUsername(@PathVariable String username) {
        UserEntity user = userService.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    // 2. Create new User
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
}
