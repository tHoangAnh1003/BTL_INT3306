package com.airline.repository;

import com.airline.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindById() {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPasswordHash("123456");
        user.setRole("ADMIN");
        user = userRepository.save(user);
        assertNotNull(user.getId());
        assertEquals("testuser", userRepository.findById(user.getId()).get().getUsername());
    }

    @Test
    void testFindAll() {
        UserEntity user1 = new UserEntity();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        user1.setPasswordHash("123456");
        user1.setRole("CUSTOMER");
        userRepository.save(user1);

        UserEntity user2 = new UserEntity();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        user2.setPasswordHash("abcdef");
        user2.setRole("STAFF");
        userRepository.save(user2);

        assertEquals(2, userRepository.findAll().size());
    }
} 