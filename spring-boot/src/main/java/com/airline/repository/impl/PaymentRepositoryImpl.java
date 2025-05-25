package com.airline.repository.impl;

import com.airline.repository.PaymentRepository;
import com.airline.repository.entity.PaymentEntity;

import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepositoryImpl implements PaymentRepository {

    private final Connection connection;

    public PaymentRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addPayment(PaymentEntity payment) {
        String sql = "INSERT INTO payments (booking_id, amount, payment_date, payment_method, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, payment.getBookingId());
            ps.setBigDecimal(2, payment.getAmount());
            ps.setTimestamp(3, Timestamp.valueOf(payment.getPaymentDate()));
            ps.setString(4, payment.getPaymentMethod());
            ps.setString(5, payment.getStatus());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    payment.setPaymentId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    @Override
    public PaymentEntity getPaymentById(Long id) {
        String sql = "SELECT * FROM payments WHERE payment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPayment(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PaymentEntity> getAllPayments() {
        List<PaymentEntity> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                payments.add(mapResultSetToPayment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public void updatePayment(PaymentEntity payment) {
        String sql = "UPDATE payments SET booking_id = ?, amount = ?, payment_date = ?, payment_method = ?, status = ? WHERE payment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, payment.getBookingId());
            ps.setBigDecimal(2, payment.getAmount());
            ps.setTimestamp(3, Timestamp.valueOf(payment.getPaymentDate()));
            ps.setString(4, payment.getPaymentMethod());
            ps.setString(5, payment.getStatus());
            ps.setLong(6, payment.getPaymentId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePayment(Long id) {
        String sql = "DELETE FROM payments WHERE payment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PaymentEntity> getPaymentsByBookingId(Long bookingId) {
        List<PaymentEntity> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE booking_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    payments.add(mapResultSetToPayment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    private PaymentEntity mapResultSetToPayment(ResultSet rs) throws SQLException {
        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentId(rs.getLong("payment_id"));
        payment.setBookingId(rs.getLong("booking_id"));
        payment.setAmount(rs.getBigDecimal("amount"));
        Timestamp ts = rs.getTimestamp("payment_date");
        if (ts != null) {
            payment.setPaymentDate(ts.toLocalDateTime());
        }
        payment.setPaymentMethod(rs.getString("payment_method"));
        payment.setStatus(rs.getString("status"));
        return payment;
    }
}
