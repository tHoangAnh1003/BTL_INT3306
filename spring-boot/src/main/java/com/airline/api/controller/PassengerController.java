package com.airline.api.controller;

import com.airline.repository.entity.UserEntity;
import com.airline.repository.PassengerRepository;
import com.airline.repository.UserRepository;
import com.airline.repository.entity.PassengerEntity;
import com.airline.security.JwtAuthenticationFilter;
import com.airline.service.PassengerService;
import com.airline.utils.AuthUtil;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

	@Autowired private PassengerRepository passengerRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PassengerService passengerService;

    @GetMapping("/{passengerId}")
    public ResponseEntity<?> getPassengerById(HttpServletRequest request,
                                              @PathVariable Long passengerId) {
        UserEntity requester = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        if (!AuthUtil.isCustomer(requester) || !requester.getUserId().equals(passengerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn chỉ xem/sửa chính mình.");
        }
        PassengerEntity p = passengerService.getPassengerById(passengerId);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/{passengerId}")
    public ResponseEntity<?> updatePassenger(HttpServletRequest request,
                                             @PathVariable Long passengerId,
                                             @RequestBody PassengerEntity update) {
        UserEntity requester = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        if (!AuthUtil.isCustomer(requester) || !requester.getUserId().equals(passengerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn chỉ xem/sửa chính mình.");
        }
        update.setPassengerId(passengerId);
        PassengerEntity saved = passengerService.updatePassenger(update);
        return ResponseEntity.ok(saved);
    }
}
