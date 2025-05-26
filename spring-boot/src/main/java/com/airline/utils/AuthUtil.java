package com.airline.utils;

import com.airline.repository.entity.UserEntity;

public class AuthUtil {
    public static boolean isAdmin(UserEntity user) {
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    public static boolean isCustomer(UserEntity user) {
        return user != null && "Customer".equalsIgnoreCase(user.getRole());
    }
    
    public static boolean isStaff(UserEntity user) {
        return user != null && "STAFF".equalsIgnoreCase(user.getRole());
    }

    public static boolean isUser(UserEntity user) {
        return user != null && "USER".equalsIgnoreCase(user.getRole());
    }
}
