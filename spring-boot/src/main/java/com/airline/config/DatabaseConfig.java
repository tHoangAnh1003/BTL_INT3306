package com.airline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/airline";
    private static final String USER = "root";
    private static final String PASS = "HoangAnh1003";

    @Bean
    public Connection connection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot create DB connection", e);
        }
    }
}
