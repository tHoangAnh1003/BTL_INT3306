package com.airline.repository.impl;

import com.airline.repository.BookingRepository;
import com.airline.repository.entity.BookingEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

    private final Connection connection;

    public BookingRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<BookingEntity> findAll() {
        List<BookingEntity> bookings = new ArrayList<>();
        String sql = "SELECT booking_id, passenger_id, flight_id, seat_id, booking_date, status FROM bookings";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bookings.add(mapRowToBookingEntity(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public BookingEntity findById(Long id) {
        String sql = "SELECT booking_id, passenger_id, flight_id, seat_id, booking_date, status FROM bookings WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToBookingEntity(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(BookingEntity booking) {
        String sql = "INSERT INTO bookings (passenger_id, flight_id, seat_id, booking_date, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, booking.getPassengerId());
            stmt.setLong(2, booking.getFlightId());
            stmt.setLong(3, booking.getSeatId());
            stmt.setTimestamp(4, Timestamp.valueOf(booking.getBookingDate()));
            stmt.setString(5, booking.getStatus());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating booking failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    booking.setBookingId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating booking failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(BookingEntity booking) {
        String sql = "UPDATE bookings SET passenger_id = ?, flight_id = ?, seat_id = ?, booking_date = ?, status = ? WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, booking.getPassengerId());
            stmt.setLong(2, booking.getFlightId());
            stmt.setLong(3, booking.getSeatId());
            stmt.setTimestamp(4, Timestamp.valueOf(booking.getBookingDate()));
            stmt.setString(5, booking.getStatus());
            stmt.setLong(6, booking.getBookingId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method để map ResultSet sang BookingEntity
    private BookingEntity mapRowToBookingEntity(ResultSet rs) throws SQLException {
        BookingEntity booking = new BookingEntity();
        booking.setBookingId(rs.getLong("booking_id"));
        booking.setPassengerId(rs.getLong("passenger_id"));
        booking.setFlightId(rs.getLong("flight_id"));
        booking.setSeatId(rs.getLong("seat_id"));
        Timestamp ts = rs.getTimestamp("booking_date");
        if (ts != null) {
            booking.setBookingDate(ts.toLocalDateTime());
        }
        booking.setStatus(rs.getString("status"));
        return booking;
    }
}
