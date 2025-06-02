package com.airline.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.airline.entity.UserEntity;
import com.airline.repository.UserRepository;
import com.airline.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPasswordHash("hashedpassword");
        testUser.setRole("USER");
    }

    @Test
    void testCreateUser() {
        // Given
        UserEntity newUser = new UserEntity();
        newUser.setUsername("newuser");
        newUser.setEmail("newuser@example.com");
        newUser.setPasswordHash("newhashedpassword");
        newUser.setRole("USER");

        when(userRepository.save(any(UserEntity.class))).thenReturn(newUser);

        // When
        userService.save(newUser);

        // Then
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testFindById_UserExists() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // When
        UserEntity result = userService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_UserNotExists() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.findById(1L);
        });
        assertEquals("User not found with id: 1", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        // Given
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");

        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");

        List<UserEntity> expectedUsers = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // When
        List<UserEntity> result = userService.findAll();

        // Then
        assertEquals(2, result.size());
        assertEquals(expectedUsers, result);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUser() {
        // Given
        UserEntity updatedUser = new UserEntity();
        updatedUser.setId(1L);
        updatedUser.setUsername("updateduser");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setPasswordHash("updatedhashedpassword");
        updatedUser.setRole("ADMIN");

        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUser);

        // When
        userService.save(updatedUser);

        // Then
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void testExistsByUsername_True() {
        // Given
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // When
        boolean result = userService.existsByUsername("testuser");

        // Then
        assertTrue(result);
        verify(userRepository, times(1)).existsByUsername("testuser");
    }

    @Test
    void testExistsByUsername_False() {
        // Given
        when(userRepository.existsByUsername("nonexistentuser")).thenReturn(false);

        // When
        boolean result = userService.existsByUsername("nonexistentuser");

        // Then
        assertFalse(result);
        verify(userRepository, times(1)).existsByUsername("nonexistentuser");
    }

    @Test
    void testExistsByEmail_True() {
        // Given
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // When
        boolean result = userService.existsByEmail("test@example.com");

        // Then
        assertTrue(result);
        verify(userRepository, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void testExistsByEmail_False() {
        // Given
        when(userRepository.existsByEmail("nonexistent@example.com")).thenReturn(false);

        // When
        boolean result = userService.existsByEmail("nonexistent@example.com");

        // Then
        assertFalse(result);
        verify(userRepository, times(1)).existsByEmail("nonexistent@example.com");
    }

    @Test
    void testRegisterUser() {
        // Given
        UserEntity newUser = new UserEntity();
        newUser.setUsername("newuser");
        newUser.setEmail("newuser@example.com");
        newUser.setPasswordHash("hashedpassword");
        newUser.setRole("USER");

        when(userRepository.save(any(UserEntity.class))).thenReturn(newUser);

        // When
        userService.registerUser(newUser);

        // Then
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testFindByEmail_UserExists() {
        // Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(testUser);

        // When
        UserEntity result = userService.findByEmail("test@example.com");

        // Then
        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testFindByEmail_UserNotExists() {
        // Given
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        // When
        UserEntity result = userService.findByEmail("nonexistent@example.com");

        // Then
        assertNull(result);
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
    }

    @Test
    void testFindByUsername_UserExists() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(testUser);

        // When
        UserEntity result = userService.findByUsername("testuser");

        // Then
        assertNotNull(result);
        assertEquals(testUser.getUsername(), result.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testFindByUsername_UserNotExists() {
        // Given
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(null);

        // When
        UserEntity result = userService.findByUsername("nonexistentuser");

        // Then
        assertNull(result);
        verify(userRepository, times(1)).findByUsername("nonexistentuser");
    }
} 