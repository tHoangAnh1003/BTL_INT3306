package com.airline.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "flights")
public class FlightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private AirlineEntity airline;

    @Column(name = "flight_number")
    private String flightNumber;

    @ManyToOne
    @JoinColumn(name = "departure_airport")
    private AirportEntity departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_airport")
    private AirportEntity arrivalAirport;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "status")
    private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AirlineEntity getAirline() {
		return airline;
	}

	public void setAirline(AirlineEntity airline) {
		this.airline = airline;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public AirportEntity getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(AirportEntity departureAirport) {
		this.departureAirport = departureAirport;
	}

	public AirportEntity getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(AirportEntity arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
