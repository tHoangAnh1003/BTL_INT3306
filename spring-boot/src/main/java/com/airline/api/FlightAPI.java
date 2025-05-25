package com.airline.api;

import com.airline.repository.entity.FlightEntity;
import com.airline.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightAPI {

    @Autowired
    private FlightService flightService;

    // 1. Get All Flights
    @GetMapping
    public List<FlightEntity> getAll() {
        return flightService.getAllFlights();
    }

    // 2. Get Flights by ID
    @GetMapping("/{id}")
    public FlightEntity getById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    // 3. Create new Flight
    @PostMapping
    public FlightEntity create(@RequestBody FlightEntity flight) {
        flightService.createFlight(flight);
        return flight;
    }

    // 4. Update Flight by ID
    @PutMapping("/{id}")
    public FlightEntity update(@PathVariable Long id, @RequestBody FlightEntity flight) {
        flight.setFlightId(id);
        flightService.updateFlight(flight);
        return flight;
    }

    // 5. Remove Flight by ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        flightService.deleteFlight(id);
    }
}
