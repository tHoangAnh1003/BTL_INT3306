package com.airline.repository.impl;

import com.airline.repository.PaymentRepository;
import com.airline.repository.entity.PaymentEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final Connection connection;

    public PaymentRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<PaymentEntity> findAll() {
        List<PaymentEntity> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                payments.add(mapResultSetToPayment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public PaymentEntity findById(Long id) {
        String sql = "SELECT * FROM payments WHERE payment_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToPayment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(PaymentEntity payment) {
        String sql = "INSERT INTO payments (booking_id, amount, payment_date, payment_method, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, payment.getBookingId());
            pstmt.setDouble(2, payment.getAmount());
            pstmt.setTimestamp(3, Timestamp.valueOf(payment.getPaymentDate()));
            pstmt.setString(4, payment.getPaymentMethod());
            pstmt.setString(5, payment.getStatus());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    payment.setPaymentId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(PaymentEntity payment) {
        String sql = "UPDATE payments SET booking_id=?, amount=?, payment_date=?, payment_method=?, status=? WHERE payment_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, payment.getBookingId());
            pstmt.setDouble(2, payment.getAmount());
            pstmt.setTimestamp(3, Timestamp.valueOf(payment.getPaymentDate()));
            pstmt.setString(4, payment.getPaymentMethod());
            pstmt.setString(5, payment.getStatus());
            pstmt.setLong(6, payment.getPaymentId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM payments WHERE payment_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PaymentEntity mapResultSetToPayment(ResultSet rs) throws SQLException {
        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentId(rs.getLong("payment_id"));
        payment.setBookingId(rs.getLong("booking_id"));
        payment.setAmount(rs.getDouble("amount"));
        payment.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
        payment.setPaymentMethod(rs.getString("payment_method"));
        payment.setStatus(rs.getString("status"));
        return payment;
    }
}
