package com.airline.api.controller;

import com.airline.DTO.flight.FlightResponseDTO;
import com.airline.converter.FlightConverter;
import com.airline.entity.FlightEntity;
import com.airline.entity.UserEntity;
import com.airline.security.JwtAuthenticationFilter;
import com.airline.service.FlightService;
import com.airline.utils.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightConverter flightConverter;

    // 1. Get All Flights
    @GetMapping
    public ResponseEntity<?> getAllFlights() {
        List<FlightEntity> flights = flightService.getAllFlights();
        List<FlightResponseDTO> response = new ArrayList<>();

        for (FlightEntity entity : flights) {
            FlightResponseDTO dto = FlightConverter.toDTO(entity);
            response.add(dto);
        }

        return ResponseEntity.ok(response);
    }


    // 2. Get Flight by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        FlightEntity entity = flightService.getFlightById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        FlightResponseDTO dto = flightConverter.toDTO(entity);
        return ResponseEntity.ok(dto);
    }


    // 3. Create Flight
    @PostMapping
    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody FlightEntity flight) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);

        if (!AuthUtil.isAdmin(user) && !AuthUtil.isStaff(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Chỉ admin hoặc staff mới được thêm chuyến bay");
        }

        flightService.createFlight(flight);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 4. Update Flight
    @PutMapping("/{id}")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @PathVariable Long id,
                                    @RequestBody FlightEntity flight) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);

        if (!AuthUtil.isAdmin(user) && !AuthUtil.isStaff(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Chỉ admin hoặc staff mới được cập nhật chuyến bay");
        }

        flight.setId(id); 
        flightService.updateFlight(flight);
        return ResponseEntity.ok().build();
    }

    // 5. Delete Flight 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable Long id) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);

        if (!AuthUtil.isAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Chỉ admin mới được xóa chuyến bay");
        }

        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Search Flights
    @GetMapping("/search")
    public ResponseEntity<?> searchFlights(
            @RequestParam String departure,
            @RequestParam String arrival,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate
    ) {
        List<FlightEntity> flights = flightService.searchFlights(departure, arrival, departureDate);
        
        List<FlightResponseDTO> response = new ArrayList<>();

        for (FlightEntity entity : flights) {
            FlightResponseDTO dto = FlightConverter.toDTO(entity);
            response.add(dto);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/round-trip")
    public ResponseEntity<?> searchRoundTripFlights(
            @RequestParam String departure,
            @RequestParam String arrival,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate
    ) {
        List<FlightEntity> departureFlights = flightService.searchFlights(departure, arrival, departureDate);
        List<FlightResponseDTO> departureResponses = new ArrayList<>();
        for (FlightEntity entity : departureFlights) {
            departureResponses.add(FlightConverter.toDTO(entity));
        }

        List<FlightEntity> returnFlights = flightService.searchFlights(arrival, departure, returnDate);
        List<FlightResponseDTO> returnResponses = new ArrayList<>();
        for (FlightEntity entity : returnFlights) {
            returnResponses.add(FlightConverter.toDTO(entity));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("departureFlights", departureResponses);
        response.put("returnFlights", returnResponses);

        return ResponseEntity.ok(response);
    }

}
