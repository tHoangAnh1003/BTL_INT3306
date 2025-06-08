package com.airline.builder;

public class PassengerBuilder {
    private Long passengerId;
    private String fullName;
    private String email;
    private String phone;
    private String passportNumber;

    private PassengerBuilder(Builder builder) {
        this.passengerId = builder.passengerId;
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.phone = builder.phone;
        this.passportNumber = builder.passportNumber;
    }

    public static class Builder {
        private Long passengerId;
        private String fullName;
        private String email;
        private String phone;
        private String passportNumber;

        public Builder setPassengerId(Long passengerId) {
            this.passengerId = passengerId;
            return this;
        }
        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }
        public Builder setPassportNumber(String passportNumber) {
            this.passportNumber = passportNumber;
            return this;
        }
        public PassengerBuilder build() {
            return new PassengerBuilder(this);
        }
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
}
