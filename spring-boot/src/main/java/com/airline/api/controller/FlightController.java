package com.airline.api.controller;

import com.airline.repository.entity.UserEntity;
import com.airline.DTO.FlightDTO;
import com.airline.converter.FlightConverter;
import com.airline.repository.entity.FlightEntity;
import com.airline.service.FlightService;
import com.airline.service.UserService;
import com.airline.utils.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@Autowired
	private FlightConverter flightConverter;

	// 1. Get All Flights
	@GetMapping
    public List<FlightDTO> getAll() {
        List<FlightEntity> entities = flightService.getAllFlights();
        return flightConverter.toDTOList(entities);
    }

	// 2. Get Flights by ID
	@GetMapping("/{id}")
	public ResponseEntity<FlightDTO> getById(@PathVariable Long id) {
        FlightEntity entity = flightService.getFlightById(id);
        if (entity == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(flightConverter.toDTO(entity));
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
	public List<FlightDTO> searchFlights(
            @RequestParam String departure,
            @RequestParam String arrival,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) String status
    ) {
        List<FlightEntity> flights = flightService.searchFlights(departure, arrival, departureDate, flightId, status);
        return flightConverter.toDTOList(flights);
    }
}
