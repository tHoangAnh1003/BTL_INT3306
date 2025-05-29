package com.airline.converter;

import java.util.Map;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.airline.builder.PaymentBuilder;
import com.airline.entity.PaymentEntity;
import com.airline.utils.MapUtils;

@Component
public class PaymentConverter {

    public PaymentEntity toPaymentEntity(Map<String, Object> map) {
        PaymentEntity payment = new PaymentEntity();
        payment.setId(MapUtils.getObject(map, "payment_id", Long.class));
        payment.setId(MapUtils.getObject(map, "booking_id", Long.class));
        payment.setAmount(MapUtils.getObject(map, "amount", Double.class));

        Timestamp payDate = MapUtils.getObject(map, "payment_date", Timestamp.class);
        payment.setPaymentDate(payDate != null ? payDate.toLocalDateTime() : null);

        payment.setPaymentMethod(MapUtils.getObject(map, "payment_method", String.class));
        payment.setStatus(MapUtils.getObject(map, "status", String.class));
        return payment;
    }

    public PaymentBuilder toPaymentBuilder(Map<String, Object> map) {
        Timestamp payDate = MapUtils.getObject(map, "payment_date", Timestamp.class);
        return new PaymentBuilder.Builder()
                .setPaymentId(MapUtils.getObject(map, "payment_id", Long.class))
                .setBookingId(MapUtils.getObject(map, "booking_id", Long.class))
                .setAmount(MapUtils.getObject(map, "amount", Double.class))
                .setPaymentDate(payDate != null ? payDate.toLocalDateTime() : null)
                .setPaymentMethod(MapUtils.getObject(map, "payment_method", String.class))
                .setStatus(MapUtils.getObject(map, "status", String.class))
                .build();
    }
}
