package com.airline.utils;

import javax.servlet.http.HttpServletRequest;

import com.airline.entity.UserEntity;
import com.airline.security.JwtAuthenticationFilter;

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
    
    public static UserEntity getUser(HttpServletRequest request) {
        return (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
    }

    public static String getUserRole(HttpServletRequest request) {
        UserEntity user = getUser(request);
        return user != null ? user.getRole() : null;
    }

    public static Long getUserId(HttpServletRequest request) {
        UserEntity user = getUser(request);
        return user != null ? user.getId() : null;
    }
}
