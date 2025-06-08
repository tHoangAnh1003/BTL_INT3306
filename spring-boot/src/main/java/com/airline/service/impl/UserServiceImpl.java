package com.airline.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airline.repository.PassengerRepository;
import com.airline.repository.UserRepository;
import com.airline.DTO.passenger.MeResponseDTO;
import com.airline.entity.PassengerEntity;
import com.airline.entity.UserEntity;
import com.airline.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void save(UserEntity user) {
        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);

        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("Customer");
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void registerUser(UserEntity user) {
        save(user);
    }
    
    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    public MeResponseDTO getCurrentUserProfile(Long userId) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<PassengerEntity> passengerOpt = passengerRepository.findByUser_Id(userId);
        String fullName = passengerOpt.map(PassengerEntity::getFullName).orElse("N/A");

        return new MeResponseDTO(fullName, user.getEmail(), user.getUsername());
    }
}
