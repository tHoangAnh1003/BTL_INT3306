package com.airline.api.controller;

import com.airline.DTO.error.ErrorResponse;
import com.airline.DTO.flight.FlightCreateRequest;
import com.airline.DTO.flight.FlightDelayRequest;
import com.airline.DTO.flight.FlightDelayResponse;
import com.airline.DTO.flight.FlightResponseDTO;
import com.airline.converter.FlightConverter;
import com.airline.entity.AirlineEntity;
import com.airline.entity.AirportEntity;
import com.airline.entity.FlightEntity;
import com.airline.entity.UserEntity;
import com.airline.repository.AirlineRepository;
import com.airline.repository.AirportRepository;
import com.airline.security.JwtAuthenticationFilter;
import com.airline.service.FlightService;
import com.airline.utils.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightConverter flightConverter;
    
    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private AirlineRepository airlineRepository;
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
//    @PostMapping
//    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody FlightCreateRequest dto) {
//        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
//
//        if (!AuthUtil.isAdmin(user) && !AuthUtil.isStaff(user)) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Chỉ admin hoặc staff mới được thêm chuyến bay");
//        }
//
//        AirlineEntity airline = airlineRepository.findByName(dto.getAirline());
//        if (airline == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hãng bay không tồn tại");
//        }
//
//        Optional<AirportEntity> depAirportOpt = airportRepository.findByCity(dto.getDepartureAirport());
//        AirportEntity departureAirport = depAirportOpt.orElseThrow(() ->
//            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sân bay đi không tồn tại"));
//
//        Optional<AirportEntity> arrAirportOpt = airportRepository.findByCity(dto.getArrivalAirport());
//        AirportEntity arrivalAirport = arrAirportOpt.orElseThrow(() ->
//            new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sân bay đến không tồn tại"));
//
//        LocalDateTime departureTime = LocalDateTime.parse(dto.getDepartureTime());
//        LocalDateTime arrivalTime = LocalDateTime.parse(dto.getArrivalTime());
//
//        FlightEntity flight = new FlightEntity();
//        flight.setAirline(airline);
//        flight.setFlightNumber(dto.getFlightNumber());
//        flight.setDepartureAirport(departureAirport);
//        flight.setArrivalAirport(arrivalAirport);
//        flight.setDepartureTime(departureTime);
//        flight.setArrivalTime(arrivalTime);
//        flight.setStatus(dto.getStatus());
//
//        flightService.createFlight(flight);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PostMapping
    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody FlightCreateRequest dto) {
        try {
            UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
            
            String userRole = (String) request.getAttribute("userRole");
            System.out.println("User role in controller: " + userRole);
            
            if (!AuthUtil.isAdmin(user) && !AuthUtil.isStaff(user)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Chỉ admin hoặc staff mới được thêm chuyến bay"));
            }

            AirlineEntity airline = airlineRepository.findByName(dto.getAirline());
            if (airline == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Hãng bay không tồn tại"));
            }

            Optional<AirportEntity> depAirportOpt = airportRepository.findByCity(dto.getDepartureAirport());
            if (!depAirportOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Sân bay đi không tồn tại"));
            }
            AirportEntity departureAirport = depAirportOpt.get();

            Optional<AirportEntity> arrAirportOpt = airportRepository.findByCity(dto.getArrivalAirport());
            if (!arrAirportOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Sân bay đến không tồn tại"));
            }
            AirportEntity arrivalAirport = arrAirportOpt.get();

            LocalDateTime departureTime;
            LocalDateTime arrivalTime;
            try {
                departureTime = LocalDateTime.parse(dto.getDepartureTime());
                arrivalTime = LocalDateTime.parse(dto.getArrivalTime());
            } catch (DateTimeParseException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Định dạng ngày giờ không hợp lệ. Ví dụ: 2025-06-15T08:30"));
            }

            FlightEntity flight = new FlightEntity();
            flight.setAirline(airline);
            flight.setFlightNumber(dto.getFlightNumber());
            flight.setDepartureAirport(departureAirport);
            flight.setArrivalAirport(arrivalAirport);
            flight.setDepartureTime(departureTime);
            flight.setArrivalTime(arrivalTime);
            flight.setStatus(dto.getStatus());

            flightService.createFlight(flight);

            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Lỗi server nội bộ"));
        }
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
        AirportEntity departureAirport = airportRepository.findByName(departure)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid departure airport name"));
        AirportEntity arrivalAirport = airportRepository.findByName(arrival)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid arrival airport name"));

        List<FlightEntity> flights = flightService.searchFlights(
                departureAirport.getCode(), arrivalAirport.getCode(), departureDate);

        List<FlightResponseDTO> response = new ArrayList<>();
        for (FlightEntity entity : flights) {
            response.add(FlightConverter.toDTO(entity));
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
        AirportEntity departureAirport = airportRepository.findByName(departure)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid departure airport name"));
        AirportEntity arrivalAirport = airportRepository.findByName(arrival)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid arrival airport name"));

        List<FlightEntity> departureFlights = flightService.searchFlights(
                departureAirport.getCode(), arrivalAirport.getCode(), departureDate);
        List<FlightResponseDTO> departureResponses = new ArrayList<>();
        for (FlightEntity entity : departureFlights) {
            departureResponses.add(FlightConverter.toDTO(entity));
        }
        
        List<FlightEntity> returnFlights = flightService.searchFlights(
                arrivalAirport.getCode(), departureAirport.getCode(), returnDate);
        List<FlightResponseDTO> returnResponses = new ArrayList<>();
        for (FlightEntity entity : returnFlights) {
            returnResponses.add(FlightConverter.toDTO(entity));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("departureFlights", departureResponses);
        response.put("returnFlights", returnResponses);

        return ResponseEntity.ok(response);
    }


    
    @PutMapping("/{id}/delay")
    public ResponseEntity<?> delayFlight(
            HttpServletRequest request,
            @PathVariable("id") Long flightId,
            @RequestBody FlightDelayRequest dto
    ) {
        UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
        if (!AuthUtil.isAdmin(user) && !AuthUtil.isStaff(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Chỉ admin hoặc staff mới được phép delay chuyến bay");
        }

        LocalDateTime newDep = dto.getParsedNewDepartureTime();
        LocalDateTime newArr = dto.getParsedNewArrivalTime();

        flightService.delayFlight(flightId, newDep, newArr);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

        FlightDelayResponse response = new FlightDelayResponse(	
            "Cập nhật giờ khởi hành và thời gian đến thành công",
            newDep.format(formatter),
            newArr.format(formatter)
        );

        return ResponseEntity.ok(response);
    }


}
