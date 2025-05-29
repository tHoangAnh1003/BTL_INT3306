package com.airline.api.controller;

import com.airline.DTO.PassengerDTO;
import com.airline.entity.UserEntity;
import com.airline.security.JwtAuthenticationFilter;
import com.airline.service.PassengerService;
import com.airline.utils.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    // 1. Get passenger info 
    @GetMapping("/{passengerId}")
    public ResponseEntity<?> getPassengerById(HttpServletRequest request,
                                              @PathVariable Long passengerId) {
        UserEntity requester = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);

        if (!AuthUtil.isCustomer(requester) || !requester.getId().equals(passengerId)) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("message", "Bạn chỉ có quyền truy cập thông tin của chính mình.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resp);
        }

        PassengerDTO dto = passengerService.getPassengerDTOById(passengerId);
        if (dto == null) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("message", "Không tìm thấy thông tin hành khách.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }

        return ResponseEntity.ok(dto);
    }

    // 2. Update passenger info 
    @PutMapping("/{passengerId}")
    public ResponseEntity<?> updatePassenger(HttpServletRequest request,
                                             @PathVariable Long passengerId,
                                             @RequestBody PassengerDTO updateDto) {
        UserEntity requester = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);

        if (!AuthUtil.isCustomer(requester) || !requester.getId().equals(passengerId)) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("message", "Bạn chỉ có quyền cập nhật thông tin của chính mình.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resp);
        }

        PassengerDTO updated = passengerService.updatePassenger(passengerId, updateDto);
        return ResponseEntity.ok(updated);
    }
}
