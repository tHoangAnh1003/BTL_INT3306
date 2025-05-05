package com.airline.builder;

import com.airline.DTO.PassengerDTO;

public class PassengerBuilder {
    private Long passengerId;
    private String fullName;
    private String email;
    private String phone;
    private String passportNumber;

    public PassengerBuilder setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
        return this;
    }

    public PassengerBuilder setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public PassengerBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public PassengerBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public PassengerBuilder setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    public PassengerDTO build() {
        return new PassengerDTO(passengerId, fullName, email, phone, passportNumber);
    }
}

