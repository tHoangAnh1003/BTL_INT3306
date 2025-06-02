package com.airline.repository.impl;

import com.airline.repository.AirportRepository;

import java.sql.*;

import org.springframework.stereotype.Repository;

@Repository
public class AirportRepositoryImpl implements AirportRepository {

	private final Connection connection;

    public AirportRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long findAirportIdByName(String airportName) {
        String sql = "SELECT airport_id FROM airports WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, airportName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("airport_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return null;
    }
    
    @Override
    public String findNameById(Long airportId) {
        String sql = "SELECT name FROM airports WHERE airport_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, airportId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    @Override
    public String findAll(Long airportId) {
        String sql = "SELECT name FROM airports";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, airportId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }
}
