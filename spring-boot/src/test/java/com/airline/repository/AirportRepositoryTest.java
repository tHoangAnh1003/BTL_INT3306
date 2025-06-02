package com.airline.repository;

import com.airline.entity.AirportEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AirportRepositoryTest {
    @Autowired
    private AirportRepository airportRepository;

    @Test
    void testSaveAndFindById() {
        AirportEntity airport = new AirportEntity();
        airport.setName("Noi Bai");
        airport.setCode("HAN");
        airport.setCity("Hanoi");
        airport.setCountry("Vietnam");
        airport = airportRepository.save(airport);
        assertNotNull(airport.getId());
        assertEquals("Noi Bai", airportRepository.findNameById(airport.getId()));
    }

    @Test
    void testFindIdByName() {
        AirportEntity airport = new AirportEntity();
        airport.setName("Tan Son Nhat");
        airport.setCode("SGN");
        airport.setCity("HCM");
        airport.setCountry("Vietnam");
        airport = airportRepository.save(airport);
        assertEquals(airport.getId(), airportRepository.findIdByName("Tan Son Nhat"));
    }

    @Test
    void testFindAll() {
        AirportEntity airport1 = new AirportEntity();
        airport1.setName("Noi Bai");
        airport1.setCode("HAN");
        airport1.setCity("Hanoi");
        airport1.setCountry("Vietnam");
        airportRepository.save(airport1);

        AirportEntity airport2 = new AirportEntity();
        airport2.setName("Tan Son Nhat");
        airport2.setCode("SGN");
        airport2.setCity("HCM");
        airport2.setCountry("Vietnam");
        airportRepository.save(airport2);

        assertEquals(2, airportRepository.findAll().size());
    }
} 