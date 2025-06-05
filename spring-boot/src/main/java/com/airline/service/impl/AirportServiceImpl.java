package com.airline.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.entity.AirportEntity;
import com.airline.repository.AirportRepository;
import com.airline.service.AirportService;

@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public List<AirportEntity> getAllAirports() {
        return airportRepository.findAll();
    }
    
    @Override
    public List<String> getAllCities() {
        return airportRepository.findDistinctCities();
    }
}
