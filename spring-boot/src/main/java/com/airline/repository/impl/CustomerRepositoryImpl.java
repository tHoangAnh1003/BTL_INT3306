package com.airline.repository.impl;

import com.airline.repository.CustomerRepository;
import com.airline.repository.entity.CustomerEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final Connection connection;

    public CustomerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<CustomerEntity> findAll() {
        List<CustomerEntity> customers = new ArrayList<>();
        String sql = "SELECT passenger_id, full_name, email, phone, passport_number FROM passengers";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(mapRowToCustomer(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public CustomerEntity findById(Long id) {
        String sql = "SELECT passenger_id, full_name, email, phone, passport_number FROM passengers WHERE passenger_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCustomer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(CustomerEntity customer) {
        String sql = "INSERT INTO passengers (full_name, email, phone, passport_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getFullName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getPassportNumber());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating customer failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setPassengerId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating customer failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CustomerEntity customer) {
        String sql = "UPDATE passengers SET full_name = ?, email = ?, phone = ?, passport_number = ? WHERE passenger_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getFullName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getPassportNumber());
            stmt.setLong(5, customer.getPassengerId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM passengers WHERE passenger_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CustomerEntity findByEmail(String email) {
        String sql = "SELECT passenger_id, full_name, email, phone, passport_number FROM passengers WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCustomer(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private CustomerEntity mapRowToCustomer(ResultSet rs) throws SQLException {
        CustomerEntity customer = new CustomerEntity();
        customer.setPassengerId(rs.getLong("passenger_id"));
        customer.setFullName(rs.getString("full_name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setPassportNumber(rs.getString("passport_number"));
        return customer;
    }
}
