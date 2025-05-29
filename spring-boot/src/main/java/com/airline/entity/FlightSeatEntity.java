package com.airline.entity;

import javax.persistence.*;

@Entity
@Table(name = "flight_seats")
public class FlightSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private FlightEntity flight;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "class")
    private String seatClass;

    @Column(name = "price")
    private Double price;

    @Column(name = "is_booked")
    private Boolean isBooked;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FlightEntity getFlight() {
		return flight;
	}

	public void setFlight(FlightEntity flight) {
		this.flight = flight;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getIsBooked() {
		return isBooked;
	}

	public void setIsBooked(Boolean isBooked) {
		this.isBooked = isBooked;
	}
    
    
}
