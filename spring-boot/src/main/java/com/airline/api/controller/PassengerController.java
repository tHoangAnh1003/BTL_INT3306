package com.airline.api.controller;

import com.airline.repository.PassengerRepository;
import com.airline.repository.UserRepository;
import com.airline.repository.entity.PassengerEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerRepository passengerRepository;
    private final UserRepository userRepository;

    public PassengerController(PassengerRepository passengerRepository, UserRepository userRepository) {
        this.passengerRepository = passengerRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{passengerId}")
    public ResponseEntity<?> getPassengerById(
            @PathVariable Long passengerId,
            @RequestHeader("X-Requester-Id") Long requesterId) {

        String role = userRepository.getRoleByUserId(requesterId);
        if (!"Customer".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền.");
        }
        if (!requesterId.equals(passengerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body("Chỉ được xem thông tin của chính mình.");
        }

        PassengerEntity passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hành khách."));

        return ResponseEntity.ok(passenger);
    }

    @PutMapping("/{passengerId}")
    public ResponseEntity<?> updatePassenger(
            @PathVariable Long passengerId,
            @RequestHeader("X-Requester-Id") Long requesterId,
            @RequestBody PassengerEntity passengerUpdate) {

        String role = userRepository.getRoleByUserId(requesterId);
        if (!"Customer".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body("Chỉ khách hàng được sửa thông tin cá nhân.");
        }
        if (!requesterId.equals(passengerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body("Bạn chỉ được sửa thông tin cá nhân của chính mình.");
        }

        PassengerEntity passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hành khách."));

        passenger.setFullName(passengerUpdate.getFullName());
        passenger.setEmail(passengerUpdate.getEmail());
        passenger.setPhone(passengerUpdate.getPhone());
        // passenger.setPassportNumber(passengerUpdate.getPassportNumber()); 

        passengerRepository.update(passenger);
        return ResponseEntity.ok(passenger);
    }
}
