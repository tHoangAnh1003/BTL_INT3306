package com.airline.api.controller;

import com.airline.repository.entity.UserEntity;
import com.airline.repository.entity.FlightEntity;
import com.airline.service.FlightService;
import com.airline.service.UserService;
import com.airline.utils.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

	@Autowired
	private FlightService flightService;
	@Autowired
	private UserService userService;

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
    public void create(@RequestBody FlightEntity flight,
                             @RequestParam Long requesterId) {
        UserEntity requester = userService.findById(requesterId);
        if (!AuthUtil.isAdmin(requester) && !AuthUtil.isStaff(requester)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        flightService.createFlight(flight);
    }

	// 4. Update Flight by ID
	@PutMapping("/{id}")
	public void update(@PathVariable Long id,@RequestBody FlightEntity flight, @RequestParam Long requesterId) {
		UserEntity requester = userService.findById(requesterId);
		if (!AuthUtil.isAdmin(requester) && !AuthUtil.isStaff(requester)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
		}

		flight.setFlightId(id);
		flightService.updateFlight(flight);
	}

	// 5. Remove Flight by ID
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id, @RequestParam Long requesterId) {
		UserEntity requester = userService.findById(requesterId);
		
		if (!AuthUtil.isAdmin(requester)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admin can delete");
		}
		
		flightService.deleteFlight(id);
	}

	@GetMapping("/search")
	public List<FlightEntity> searchFlights(@RequestParam String departure, @RequestParam String arrival,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
			@RequestParam(required = false) Long flightId, @RequestParam(required = false) String status) {

		return flightService.searchFlights(departure, arrival, departureDate, flightId, status);
	}
}
