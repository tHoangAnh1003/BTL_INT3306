package com.airline.repository;

public interface AirportRepository {
    Long findAirportIdByName(String airportName);
    String findNameById(Long airportId);
}
