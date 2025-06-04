package com.airline.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.airline.entity.AircraftEntity;
import com.airline.service.AircraftService;

@RestController
@RequestMapping("/api/aircrafts")
public class AircraftController {

	@Autowired
    private AircraftService aircraftService;


    public static class CreateAircraftRequest {
        public String airline;
        public String model;
        public int capacity;
		public String getAirline() {
			return airline;
		}
		public void setAirline(String airline) {
			this.airline = airline;
		}
		public String getModel() {
			return model;
		}
		public void setModel(String model) {
			this.model = model;
		}
		public int getCapacity() {
			return capacity;
		}
		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}
    }

    @PostMapping
    public ResponseEntity<?> createAircraft(@RequestBody CreateAircraftRequest request) {
        try {
            AircraftEntity created = aircraftService.createAircraft(
                request.airline, request.model, request.capacity
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
