package com.airline.DTO;

public class PassengerDTO {
    private Long passengerId;
    private String fullName;
    private String email;
    private String phone;
    private String passportNumber;

    public PassengerDTO(Long passengerId, String fullName, String email, String phone, String passportNumber) {
        this.passengerId = passengerId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.passportNumber = passportNumber;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    @Override
    public String toString() {
        return "PassengerDTO{" +
               "passengerId=" + passengerId +
               ", fullName='" + fullName + '\'' +
               ", email='" + email + '\'' +
               ", phone='" + phone + '\'' +
               ", passportNumber='" + passportNumber + '\'' +
               '}';
    }
}

