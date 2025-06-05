package com.airline.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
    @GetMapping("/cities")
    public List<String> getAllCities() {
        return airportService.getAllCities();
    }
}
