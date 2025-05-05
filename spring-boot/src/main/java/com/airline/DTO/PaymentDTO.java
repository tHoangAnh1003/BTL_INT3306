package com.airline.DTO;

public class PaymentDTO {
    private Long paymentId;
    private Long bookingId;
    private Double amount;
    private String paymentMethod;
    private String status;

    public PaymentDTO(Long paymentId, Long bookingId, Double amount, String paymentMethod, String status) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
               "paymentId=" + paymentId +
               ", bookingId=" + bookingId +
               ", amount=" + amount +
               ", paymentMethod='" + paymentMethod + '\'' +
               ", status='" + status + '\'' +
               '}';
    }
}

