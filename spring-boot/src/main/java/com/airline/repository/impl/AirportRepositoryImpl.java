package com.airline.repository.impl;

import com.airline.repository.AirportRepository;

import javax.sql.DataSource;
import java.sql.*;

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
}
