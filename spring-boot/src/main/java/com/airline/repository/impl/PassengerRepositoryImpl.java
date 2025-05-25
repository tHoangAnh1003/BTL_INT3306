package com.airline.repository.impl;

import com.airline.repository.PassengerRepository;
import com.airline.repository.entity.PassengerEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class PassengerRepositoryImpl implements PassengerRepository {

    private final Connection connection;

    public PassengerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<PassengerEntity> findAll() {
        List<PassengerEntity> passengers = new ArrayList<>();
        String sql = "SELECT * FROM passengers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                passengers.add(mapResultSetToPassenger(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengers;
    }

    @Override
    public PassengerEntity findById(Long id) {
        String sql = "SELECT * FROM passengers WHERE passenger_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToPassenger(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(PassengerEntity passenger) {
        String sql = "INSERT INTO passengers (full_name, email, phone, passport_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, passenger.getFullName());
            pstmt.setString(2, passenger.getEmail());
            pstmt.setString(3, passenger.getPhone());
            pstmt.setString(4, passenger.getPassportNumber());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    passenger.setPassengerId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(PassengerEntity passenger) {
        String sql = "UPDATE passengers SET full_name = ?, email = ?, phone = ?, passport_number = ? WHERE passenger_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, passenger.getFullName());
            pstmt.setString(2, passenger.getEmail());
            pstmt.setString(3, passenger.getPhone());
            pstmt.setString(4, passenger.getPassportNumber());
            pstmt.setLong(5, passenger.getPassengerId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM passengers WHERE passenger_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PassengerEntity mapResultSetToPassenger(ResultSet rs) throws SQLException {
        PassengerEntity passenger = new PassengerEntity();
        passenger.setPassengerId(rs.getLong("passenger_id"));
        passenger.setFullName(rs.getString("full_name"));
        passenger.setEmail(rs.getString("email"));
        passenger.setPhone(rs.getString("phone"));
        passenger.setPassportNumber(rs.getString("passport_number"));
        return passenger;
    }
}
