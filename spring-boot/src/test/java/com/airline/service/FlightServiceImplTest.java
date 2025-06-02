package com.airline.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.airline.entity.FlightEntity;
import com.airline.repository.FlightRepository;
import com.airline.service.impl.FlightServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FlightServiceImplTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightServiceImpl flightService;

    private FlightEntity testFlight;

    @BeforeEach
    void setUp() {
        testFlight = new FlightEntity();
        testFlight.setId(1L);
        testFlight.setFlightNumber("VN001");
        testFlight.setDepartureTime(LocalDateTime.now().plusDays(1));
        testFlight.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));
        testFlight.setStatus("SCHEDULED");
    }

    @Test
    void testCreateFlight() {
        // Given
        FlightEntity newFlight = new FlightEntity();
        newFlight.setFlightNumber("VN002");
        newFlight.setDepartureTime(LocalDateTime.now().plusDays(2));
        newFlight.setArrivalTime(LocalDateTime.now().plusDays(2).plusHours(3));
        newFlight.setStatus("SCHEDULED");

        when(flightRepository.save(any(FlightEntity.class))).thenReturn(newFlight);

        // When
        flightService.createFlight(newFlight);

        // Then
        verify(flightRepository, times(1)).save(newFlight);
    }

    @Test
    void testFindById_FlightExists() {
        // Given
        when(flightRepository.findById(1L)).thenReturn(Optional.of(testFlight));

        // When
        FlightEntity result = flightService.getFlightById(1L);

        // Then
        assertNotNull(result);
        assertEquals(testFlight.getId(), result.getId());
        assertEquals(testFlight.getFlightNumber(), result.getFlightNumber());
        assertEquals(testFlight.getStatus(), result.getStatus());
        verify(flightRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_FlightNotExists() {
        // Given
        when(flightRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        FlightEntity result = flightService.getFlightById(1L);

        // Then
        assertNull(result);
        verify(flightRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        // Given
        FlightEntity flight1 = new FlightEntity();
        flight1.setId(1L);
        flight1.setFlightNumber("VN001");
        flight1.setStatus("SCHEDULED");

        FlightEntity flight2 = new FlightEntity();
        flight2.setId(2L);
        flight2.setFlightNumber("VN002");
        flight2.setStatus("DELAYED");

        List<FlightEntity> expectedFlights = Arrays.asList(flight1, flight2);
        when(flightRepository.findAll()).thenReturn(expectedFlights);

        // When
        List<FlightEntity> result = flightService.getAllFlights();

        // Then
        assertEquals(2, result.size());
        assertEquals(expectedFlights, result);
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void testUpdateFlight() {
        // Given
        FlightEntity updatedFlight = new FlightEntity();
        updatedFlight.setId(1L);
        updatedFlight.setFlightNumber("VN001_UPDATED");
        updatedFlight.setDepartureTime(LocalDateTime.now().plusDays(3));
        updatedFlight.setArrivalTime(LocalDateTime.now().plusDays(3).plusHours(4));
        updatedFlight.setStatus("DELAYED");

        when(flightRepository.save(any(FlightEntity.class))).thenReturn(updatedFlight);

        // When
        flightService.updateFlight(updatedFlight);

        // Then
        verify(flightRepository, times(1)).save(updatedFlight);
    }

    @Test
    void testDeleteFlight() {
        // Given
        Long flightId = 1L;
        doNothing().when(flightRepository).deleteById(flightId);

        // When
        flightService.deleteFlight(flightId);

        // Then
        verify(flightRepository, times(1)).deleteById(flightId);
    }

    @Test
    void testSearchFlights() {
        // Given
        String departure = "Hanoi";
        String arrival = "Ho Chi Minh City";
        LocalDate departureDate = LocalDate.now().plusDays(1);
        LocalDateTime startOfDay = departureDate.atStartOfDay();
        LocalDateTime endOfDay = departureDate.plusDays(1).atStartOfDay();

        FlightEntity searchResult = new FlightEntity();
        searchResult.setId(1L);
        searchResult.setFlightNumber("VN001");
        searchResult.setStatus("SCHEDULED");

        List<FlightEntity> expectedResults = Arrays.asList(searchResult);
        when(flightRepository.searchFlightsByAirportNames(departure, arrival, startOfDay, endOfDay))
                .thenReturn(expectedResults);

        // When
        List<FlightEntity> result = flightService.searchFlights(departure, arrival, departureDate);

        // Then
        assertEquals(1, result.size());
        assertEquals(expectedResults, result);
        verify(flightRepository, times(1)).searchFlightsByAirportNames(departure, arrival, startOfDay, endOfDay);
    }

    @Test
    void testSearchFlights_NoResults() {
        // Given
        String departure = "Hanoi";
        String arrival = "Ho Chi Minh City";
        LocalDate departureDate = LocalDate.now().plusDays(1);
        LocalDateTime startOfDay = departureDate.atStartOfDay();
        LocalDateTime endOfDay = departureDate.plusDays(1).atStartOfDay();

        when(flightRepository.searchFlightsByAirportNames(departure, arrival, startOfDay, endOfDay))
                .thenReturn(Arrays.asList());

        // When
        List<FlightEntity> result = flightService.searchFlights(departure, arrival, departureDate);

        // Then
        assertTrue(result.isEmpty());
        verify(flightRepository, times(1)).searchFlightsByAirportNames(departure, arrival, startOfDay, endOfDay);
    }

    @Test
    void testSearchFlights_InvalidDate() {
        // Given
        String departure = "Hanoi";
        String arrival = "Ho Chi Minh City";
        LocalDate pastDate = LocalDate.now().minusDays(1);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            flightService.searchFlights(departure, arrival, pastDate);
        });
        assertEquals("Departure date cannot be in the past", exception.getMessage());
        verify(flightRepository, never()).searchFlightsByAirportNames(any(), any(), any(), any());
    }

    @Test
    void testUpdateFlightStatus() {
        // Given
        Long flightId = 1L;
        String newStatus = "DELAYED";
        testFlight.setStatus("SCHEDULED");

        when(flightRepository.findById(flightId)).thenReturn(Optional.of(testFlight));
        when(flightRepository.save(any(FlightEntity.class))).thenReturn(testFlight);

        // When
        FlightEntity result = flightService.updateFlightStatus(flightId, newStatus);

        // Then
        assertNotNull(result);
        assertEquals(newStatus, result.getStatus());
        verify(flightRepository, times(1)).findById(flightId);
        verify(flightRepository, times(1)).save(testFlight);
    }

    @Test
    void testFindFlightsByStatus() {
        // Given
        String status = "SCHEDULED";
        List<FlightEntity> expectedFlights = Arrays.asList(testFlight);
        when(flightRepository.findByStatus(status)).thenReturn(expectedFlights);

        // When
        List<FlightEntity> result = flightService.findFlightsByStatus(status);

        // Then
        assertEquals(expectedFlights, result);
        verify(flightRepository, times(1)).findByStatus(status);
    }
} 