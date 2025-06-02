package com.airline.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.airline.entity.PassengerEntity;
import com.airline.repository.PassengerRepository;
import com.airline.converter.PassengerConverter;
import com.airline.DTO.passenger.PassengerDTO;
import com.airline.service.impl.PassengerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceImplTest {

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerConverter passengerConverter;

    @InjectMocks
    private PassengerServiceImpl passengerService;

    private PassengerEntity testPassenger;
    private PassengerDTO testPassengerDTO;

    @BeforeEach
    void setUp() {
        testPassenger = new PassengerEntity();
        testPassenger.setId(1L);
        testPassenger.setFullName("John Doe");
        testPassenger.setEmail("john.doe@example.com");
        testPassenger.setPhone("0123456789");
        testPassenger.setPassportNumber("AB123456");

        testPassengerDTO = new PassengerDTO();
        testPassengerDTO.setFullName("John Doe");
        testPassengerDTO.setEmail("john.doe@example.com");
        testPassengerDTO.setPhone("0123456789");
        testPassengerDTO.setPassportNumber("AB123456");
    }

    @Test
    void testCreatePassenger() {
        // Given
        PassengerEntity newPassenger = new PassengerEntity();
        newPassenger.setFullName("Jane Smith");
        newPassenger.setEmail("jane.smith@example.com");
        newPassenger.setPhone("0987654321");
        newPassenger.setPassportNumber("CD789012");

        when(passengerRepository.save(any(PassengerEntity.class))).thenReturn(newPassenger);

        // When
        passengerService.createPassenger(newPassenger);

        // Then
        verify(passengerRepository, times(1)).save(newPassenger);
    }

    @Test
    void testFindById_PassengerExists() {
        // Given
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(testPassenger));

        // When
        PassengerEntity result = passengerService.getPassengerById(1L);

        // Then
        assertNotNull(result);
        assertEquals(testPassenger.getId(), result.getId());
        assertEquals(testPassenger.getFullName(), result.getFullName());
        assertEquals(testPassenger.getEmail(), result.getEmail());
        assertEquals(testPassenger.getPhone(), result.getPhone());
        assertEquals(testPassenger.getPassportNumber(), result.getPassportNumber());
        verify(passengerRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_PassengerNotExists() {
        // Given
        when(passengerRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            passengerService.getPassengerById(1L);
        });
        assertEquals("Passenger not found.", exception.getMessage());
        verify(passengerRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        // Given
        PassengerEntity passenger1 = new PassengerEntity();
        passenger1.setId(1L);
        passenger1.setFullName("John Doe");
        passenger1.setEmail("john.doe@example.com");

        PassengerEntity passenger2 = new PassengerEntity();
        passenger2.setId(2L);
        passenger2.setFullName("Jane Smith");
        passenger2.setEmail("jane.smith@example.com");

        List<PassengerEntity> expectedPassengers = Arrays.asList(passenger1, passenger2);
        when(passengerRepository.findAll()).thenReturn(expectedPassengers);

        // When
        List<PassengerEntity> result = passengerService.getAllPassengers();

        // Then
        assertEquals(2, result.size());
        assertEquals(expectedPassengers, result);
        verify(passengerRepository, times(1)).findAll();
    }

    @Test
    void testUpdatePassenger() {
        // Given
        PassengerEntity updatedPassenger = new PassengerEntity();
        updatedPassenger.setId(1L);
        updatedPassenger.setFullName("John Updated");
        updatedPassenger.setEmail("john.updated@example.com");
        updatedPassenger.setPhone("0111111111");
        updatedPassenger.setPassportNumber("XY999999");

        when(passengerRepository.save(any(PassengerEntity.class))).thenReturn(updatedPassenger);

        // When
        PassengerEntity result = passengerService.updatePassenger(updatedPassenger);

        // Then
        assertEquals(updatedPassenger, result);
        verify(passengerRepository, times(1)).save(updatedPassenger);
    }

    @Test
    void testDeletePassenger() {
        // Given
        Long passengerId = 1L;
        doNothing().when(passengerRepository).deleteById(passengerId);

        // When
        passengerService.deletePassenger(passengerId);

        // Then
        verify(passengerRepository, times(1)).deleteById(passengerId);
    }

    @Test
    void testGetPassengerDTOById_PassengerExists() {
        // Given
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(testPassenger));
        when(passengerConverter.toDTO(testPassenger)).thenReturn(testPassengerDTO);

        // When
        PassengerDTO result = passengerService.getPassengerDTOById(1L);

        // Then
        assertNotNull(result);
        assertEquals(testPassengerDTO, result);
        verify(passengerRepository, times(1)).findById(1L);
        verify(passengerConverter, times(1)).toDTO(testPassenger);
    }

    @Test
    void testGetPassengerDTOById_PassengerNotExists() {
        // Given
        when(passengerRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        PassengerDTO result = passengerService.getPassengerDTOById(1L);

        // Then
        assertNull(result);
        verify(passengerRepository, times(1)).findById(1L);
        verify(passengerConverter, never()).toDTO(any());
    }

    @Test
    void testFindPassengersByFlightId() {
        // Given
        Long flightId = 1L;
        List<PassengerEntity> expectedPassengers = Arrays.asList(testPassenger);
        when(passengerRepository.findByFlightId(flightId)).thenReturn(expectedPassengers);

        // When
        List<PassengerEntity> result = passengerService.findPassengersByFlightId(flightId);

        // Then
        assertEquals(expectedPassengers, result);
        verify(passengerRepository, times(1)).findByFlightId(flightId);
    }

    @Test
    void testValidatePassengerInfo() {
        // Given
        PassengerEntity invalidPassenger = new PassengerEntity();
        invalidPassenger.setFullName("");
        invalidPassenger.setEmail("invalid-email");
        invalidPassenger.setPhone("123");
        invalidPassenger.setPassportNumber("");

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            passengerService.validatePassengerInfo(invalidPassenger);
        });
        assertTrue(exception.getMessage().contains("Invalid passenger information"));
    }

    @Test
    void testFindPassengersByUserId() {
        // Given
        Long userId = 1L;
        List<PassengerEntity> expectedPassengers = Arrays.asList(testPassenger);
        when(passengerRepository.findByUserId(userId)).thenReturn(expectedPassengers);

        // When
        List<PassengerEntity> result = passengerService.findPassengersByUserId(userId);

        // Then
        assertEquals(expectedPassengers, result);
        verify(passengerRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testUpdatePassengerStatus() {
        // Given
        Long passengerId = 1L;
        String newStatus = "CHECKED_IN";
        testPassenger.setStatus("REGISTERED");

        when(passengerRepository.findById(passengerId)).thenReturn(Optional.of(testPassenger));
        when(passengerRepository.save(any(PassengerEntity.class))).thenReturn(testPassenger);

        // When
        PassengerEntity result = passengerService.updatePassengerStatus(passengerId, newStatus);

        // Then
        assertNotNull(result);
        assertEquals(newStatus, result.getStatus());
        verify(passengerRepository, times(1)).findById(passengerId);
        verify(passengerRepository, times(1)).save(testPassenger);
    }
} 