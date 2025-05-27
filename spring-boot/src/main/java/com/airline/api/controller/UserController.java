package com.airline.api.controller;

import com.airline.repository.entity.UserEntity;
import com.airline.security.JwtAuthenticationFilter;
import com.airline.service.UserService;
import com.airline.utils.AuthUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public List<UserEntity> getAllUsers(HttpServletRequest request) {
        UserEntity requester = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        if (!AuthUtil.isAdmin(requester)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only ADMIN can view users.");
        }
        return userService.findAll();
    }


    // GET /api/users/{id}
    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userService.findById(id);
    } 
}
