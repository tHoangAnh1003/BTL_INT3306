package com.airline.api.controller;

import com.airline.entity.UserEntity;
import com.airline.repository.BookingRepository;
import com.airline.repository.FlightRepository;
import com.airline.repository.FlightSeatRepository;
import com.airline.repository.PassengerRepository;
import com.airline.security.JwtAuthenticationFilter;
import com.airline.DTO.booking.BookingRequestDTO;
import com.airline.DTO.booking.BookingResponseDTO;
import com.airline.DTO.booking.BookingStatisticsDTO;
import com.airline.DTO.booking.BookingSummaryDTO;
import com.airline.converter.BookingConverter;
import com.airline.entity.BookingEntity;
import com.airline.entity.FlightEntity;
import com.airline.entity.FlightSeatEntity;
import com.airline.entity.PassengerEntity;
import com.airline.service.BookingService;
import com.airline.utils.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private FlightSeatRepository flightSeatRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@GetMapping
	public ResponseEntity<List<BookingResponseDTO>> getAll(HttpServletRequest request) {
		UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
		List<BookingEntity> bookings = AuthUtil.isAdmin(user) ? bookingService.getAllBookings()
				: bookingService.getBookingsByPassengerId(user.getId());

		List<BookingResponseDTO> dtoList = bookings.stream().map(BookingConverter::toDTO).collect(Collectors.toList());
		return ResponseEntity.ok(dtoList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookingResponseDTO> getById(@PathVariable Long id) {
		BookingEntity booking = bookingService.getBookingById(id);
		return ResponseEntity.ok(BookingConverter.toDTO(booking));
	}

	@PostMapping
	public ResponseEntity<?> createBooking(@RequestBody BookingRequestDTO request, HttpServletRequest httpRequest) {
	    try {
	        UserEntity user = (UserEntity) httpRequest.getAttribute(JwtAuthenticationFilter.USER_ATTR);
	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
	        }

	        Long userId = user.getId();

	        if (request.getDepartureAirportCode() == null || request.getArrivalAirportCode() == null || request.getDepartureDate() == null) {
	            return ResponseEntity.badRequest().body("Missing departure/arrival airport code or departure date");
	        }

	        LocalDate departureDate;
	        try {
	            departureDate = LocalDate.parse(request.getDepartureDate());
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Invalid date format: " + request.getDepartureDate());
	        }

	        LocalDateTime startOfDay = departureDate.atStartOfDay();
	        LocalDateTime endOfDay = departureDate.atTime(LocalTime.MAX);

	        List<FlightEntity> matchedFlights = flightRepository.findByRouteAndDateRange(
	                request.getDepartureAirportCode(),
	                request.getArrivalAirportCode(),
	                startOfDay,
	                endOfDay
	        );

	        if (matchedFlights.isEmpty()) {
	            return ResponseEntity.badRequest().body("No flights found matching the criteria.");
	        }

	        FlightEntity selectedFlight = matchedFlights.get(0);

	        // Tìm ghế trống
	        List<FlightSeatEntity> availableSeats = flightSeatRepository.findByFlightAndIsBookedFalse(selectedFlight);
	        if (availableSeats.isEmpty()) {
	            return ResponseEntity.badRequest().body("No available seats for this flight.");
	        }
	        
	        FlightSeatEntity selectedSeat = availableSeats.get(new Random().nextInt(availableSeats.size()));

	        Optional<PassengerEntity> optionalPassenger = passengerRepository.findByUserId(userId);
	        if (!optionalPassenger.isPresent()) {
	            return ResponseEntity.badRequest().body("Passenger not found for user id: " + userId);
	        }
	        
	        BookingEntity booking = new BookingEntity();
	        booking.setPassenger(optionalPassenger.get());
	        booking.setFlight(selectedFlight);
	        booking.setSeat(selectedSeat);
	        booking.setBookingDate(LocalDateTime.now());
	        booking.setStatus(request.getStatus() != null ? request.getStatus() : "CONFIRMED");

	        bookingRepository.save(booking);

	        selectedSeat.setIsBooked(true);
	        flightSeatRepository.save(selectedSeat);

	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Booking created successfully");
	        response.put("bookingId", booking.getId());
	        response.put("flightNumber", selectedFlight.getFlightNumber());
	        response.put("seatNumber", selectedSeat.getSeatNumber());
	        response.put("departureTime", selectedFlight.getDepartureTime());

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.internalServerError().body("Error while creating booking: " + e.getMessage());
	    }
	}


	@PutMapping("/{id}")
	public ResponseEntity<BookingEntity> updateBooking(HttpServletRequest request, @PathVariable Long id,
			@RequestBody BookingEntity booking) {
		UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
		BookingEntity existing = bookingService.getBookingById(id);

		if (!AuthUtil.isAdmin(user) && !existing.getPassengerId().equals(user.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không có quyền sửa đặt vé này.");
		}

		booking.setId(id);
		bookingService.updateBooking(booking);
		return ResponseEntity.ok(booking);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBooking(HttpServletRequest request, @PathVariable Long id) {
		UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
		BookingEntity existing = bookingService.getBookingById(id);

		if (!AuthUtil.isAdmin(user) && !existing.getId().equals(user.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không có quyền hủy đặt vé này.");
		}

		bookingService.deleteBooking(id);
		return ResponseEntity.ok(Collections.singletonMap("msg", "Đã xóa thành công"));
	}

	@GetMapping("/passenger/{passengerId}")
	public ResponseEntity<List<BookingEntity>> getByPassenger(HttpServletRequest request,
			@PathVariable Long passengerId) {
		UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
		if (AuthUtil.isAdmin(user) || user.getId().equals(passengerId)) {
			return ResponseEntity.ok(bookingService.getBookingsByPassengerId(passengerId));
		}
		throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
	}

	@DeleteMapping("/cancel/{bookingId}")
	public ResponseEntity<?> cancelBooking(HttpServletRequest request, @PathVariable Long bookingId) {
		UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bạn cần đăng nhập");
		}

		try {
			bookingService.cancelBooking(bookingId, user.getId());
			return ResponseEntity.ok("Hủy vé thành công");
		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@GetMapping("/statistics")
	public ResponseEntity<List<BookingStatisticsDTO>> getBookingStatistics(HttpServletRequest request) {
		UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);
		if (!AuthUtil.isAdmin(user)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}

		List<BookingStatisticsDTO> stats = bookingService.getBookingStatistics();
		return ResponseEntity.ok(stats);
	}

	@GetMapping("/my")
	public ResponseEntity<?> getMyBookings(HttpServletRequest request) {
		UserEntity user = (UserEntity) request.getAttribute(JwtAuthenticationFilter.USER_ATTR);

		if (!AuthUtil.isCustomer(user)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(Collections.singletonMap("msg", "Chỉ khách hàng mới được xem danh sách vé đã đặt"));
		}

		List<BookingEntity> bookings = bookingRepository.findByPassenger_Id(user.getId());
		List<BookingSummaryDTO> response = new ArrayList<>();

		for (BookingEntity booking : bookings) {
			response.add(BookingConverter.toSummaryDTO(booking));
		}

		return ResponseEntity.ok(response);
	}

}
