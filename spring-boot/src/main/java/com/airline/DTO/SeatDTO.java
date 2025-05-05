package com.airline.DTO;

public class SeatDTO {
    private Long seatId;
    private String seatNumber;
    private String seatClass;
    private Double price;
    private Boolean isBooked;

    public SeatDTO(Long seatId, String seatNumber, String seatClass, Double price, Boolean isBooked) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.price = price;
        this.isBooked = isBooked;
    }

    public Long getSeatId() {
        return seatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getIsBooked() {
        return isBooked;
    }

    @Override
    public String toString() {
        return "SeatDTO{" +
               "seatId=" + seatId +
               ", seatNumber='" + seatNumber + '\'' +
               ", seatClass='" + seatClass + '\'' +
               ", price=" + price +
               ", isBooked=" + isBooked +
               '}';
    }
}

