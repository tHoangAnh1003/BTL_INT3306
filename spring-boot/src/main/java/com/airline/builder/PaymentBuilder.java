package com.airline.builder;

import java.time.LocalDateTime;

public class PaymentBuilder {
    private Long paymentId;
    private Long bookingId;
    private Double amount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String status;

    private PaymentBuilder(Builder builder) {
        this.paymentId = builder.paymentId;
        this.bookingId = builder.bookingId;
        this.amount = builder.amount;
        this.paymentDate = builder.paymentDate;
        this.paymentMethod = builder.paymentMethod;
        this.status = builder.status;
    }

    public static class Builder {
        private Long paymentId;
        private Long bookingId;
        private Double amount;
        private LocalDateTime paymentDate;
        private String paymentMethod;
        private String status;

        public Builder setPaymentId(Long paymentId) {
            this.paymentId = paymentId;
            return this;
        }
        public Builder setBookingId(Long bookingId) {
            this.bookingId = bookingId;
            return this;
        }
        public Builder setAmount(Double amount) {
            this.amount = amount;
            return this;
        }
        public Builder setPaymentDate(LocalDateTime paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }
        public Builder setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }
        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }
        public PaymentBuilder build() {
            return new PaymentBuilder(this);
        }
    }

    public Long getPaymentId() { return paymentId; }
    public Long getBookingId() { return bookingId; }
    public Double getAmount() { return amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getStatus() { return status; }
}
	