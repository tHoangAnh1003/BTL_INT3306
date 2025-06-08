package com.airline.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airline.entity.AirportEntity;
import com.airline.service.AirportService;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @GetMapping
    public List<AirportEntity> getAllAirports() {
        return airportService.getAllAirports();
    }
    
    // Lấy ra thành phố
    @GetMapping("/cities")
    public List<String> getAllCities() {
        return airportService.getAllCities();
    }
    
    // Lấy mã thành phố
    @GetMapping("/code")
    public ResponseEntity<Map<String, String>> getCodeByCity(@RequestParam String city) {
        String code = airportService.getAirportCodeByCity(city);
        Map<String, String> response = new HashMap<>();
        response.put("code", code);
        return ResponseEntity.ok(response);
    }
}
