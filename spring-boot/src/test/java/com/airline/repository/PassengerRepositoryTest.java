package com.airline.repository;

import com.airline.entity.PassengerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PassengerRepositoryTest {
    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    void testSaveAndFindById() {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setFullName("Nguyen Van A");
        passenger.setEmail("a@gmail.com");
        passenger.setPhone("0123456789");
        passenger.setPassportNumber("P123456");
        passenger = passengerRepository.save(passenger);
        assertNotNull(passenger.getId());
        assertEquals("Nguyen Van A", passengerRepository.findById(passenger.getId()).get().getFullName());
    }

    @Test
    void testFindAll() {
        PassengerEntity passenger1 = new PassengerEntity();
        passenger1.setFullName("Nguyen Van A");
        passenger1.setEmail("a@gmail.com");
        passenger1.setPhone("0123456789");
        passenger1.setPassportNumber("P123456");
        passengerRepository.save(passenger1);

        PassengerEntity passenger2 = new PassengerEntity();
        passenger2.setFullName("Nguyen Van B");
        passenger2.setEmail("b@gmail.com");
        passenger2.setPhone("0987654321");
        passenger2.setPassportNumber("P654321");
        passengerRepository.save(passenger2);

        assertEquals(2, passengerRepository.findAll().size());
    }
} 