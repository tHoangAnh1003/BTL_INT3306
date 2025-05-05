package com.airline.builder;

import com.airline.DTO.PaymentDTO;

public class PaymentBuilder {
    private Long paymentId;
    private Long bookingId;
    private Double amount;
    private String paymentMethod;
    private String status;

    public PaymentBuilder setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    public PaymentBuilder setBookingId(Long bookingId) {
        this.bookingId = bookingId;
        return this;
    }

    public PaymentBuilder setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public PaymentBuilder setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public PaymentBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public PaymentDTO build() {
        return new PaymentDTO(paymentId, bookingId, amount, paymentMethod, status);
    }
}

