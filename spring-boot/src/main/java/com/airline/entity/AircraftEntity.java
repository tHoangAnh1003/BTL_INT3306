package com.airline.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "aircrafts")
public class AircraftEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aircraft_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private AirlineEntity airline;

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

	@Column(name = "model")
    private String model;

    @Column(name = "capacity")
    private int capacity;
}

