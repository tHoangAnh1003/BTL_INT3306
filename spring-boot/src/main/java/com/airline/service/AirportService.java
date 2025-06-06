package com.airline.service;

import com.airline.entity.AirportEntity;
import java.util.List;

public interface AirportService {
    List<AirportEntity> getAllAirports();
    
    List<String> getAllCities();
    
    String getAirportCodeByCity(String city);
}
