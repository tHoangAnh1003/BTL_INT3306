package com.airline.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    
    @Override
    public String getAirportCodeByCity(String city) {
        return airportRepository.findByCityIgnoreCase(city)
                .map(AirportEntity::getCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy mã sân bay cho thành phố: " + city));
    }
}
