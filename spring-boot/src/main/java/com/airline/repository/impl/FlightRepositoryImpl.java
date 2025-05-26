package com.airline.repository.impl;

import com.airline.repository.AirportRepository;
import com.airline.repository.FlightRepository;
import com.airline.repository.entity.FlightEntity;
import com.airline.utils.ConnectionUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class FlightRepositoryImpl implements FlightRepository {

	    private final Connection connection;
	    private final AirportRepository airportRepository;
	
	    public FlightRepositoryImpl(Connection connection) {
	        this.connection = connection;
	        this.airportRepository = new AirportRepositoryImpl(connection);
	    }

	    @Override
	    public List<FlightEntity> searchFlights(String departureAirportName, String arrivalAirportName, LocalDate departureDate, Long flightId, String status) {
	    	List<FlightEntity> flights = new ArrayList<>();
	
	        Long departureAirportId = null;
	        Long arrivalAirportId = null;
	
	        if (departureAirportName != null && !departureAirportName.isEmpty()) {
	            departureAirportId = airportRepository.findAirportIdByName(departureAirportName);
	            if (departureAirportId == null) {
	                return flights;  
	            }
	        }
	
	        if (arrivalAirportName != null && !arrivalAirportName.isEmpty()) {
	            arrivalAirportId = airportRepository.findAirportIdByName(arrivalAirportName);
	            if (arrivalAirportId == null) {
	                return flights; 
	            }
	        }
	
	        StringBuilder sql = new StringBuilder("SELECT * FROM flights WHERE 1=1");
	        List<Object> params = new ArrayList<>();
	
	        if (departureAirportId != null) {
	            sql.append(" AND departure_airport = ?");
	            params.add(departureAirportId);
	        }
	        if (arrivalAirportId != null) {
	            sql.append(" AND arrival_airport = ?");
	            params.add(arrivalAirportId);
	        }
	        if (departureDate != null) {
	            sql.append(" AND DATE(departure_time) = ?");
	            params.add(Date.valueOf(departureDate));
	        }
	        if (flightId != null) {
	            sql.append(" AND flight_id = ?");
	            params.add(flightId);
	        }
	        if (status != null && !status.isEmpty()) {
	            sql.append(" AND status = ?");
	            params.add(status);
	        }

        	try (Connection conn = ConnectionUtil.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql.toString())) {

	            for (int i = 0; i < params.size(); i++) {
	                ps.setObject(i + 1, params.get(i));
	            }
	
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                FlightEntity flight = new FlightEntity();
	                flight.setFlightId(rs.getLong("flight_id"));
	                flight.setAirlineId(rs.getLong("airline_id"));
	                flight.setFlightNumber(rs.getString("flight_number"));
	                flight.setDepartureAirport(rs.getLong("departure_airport"));
	                flight.setArrivalAirport(rs.getLong("arrival_airport"));
	                flight.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
	                flight.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
	                flight.setStatus(rs.getString("status"));
	                flights.add(flight);
	            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }

    
    @Override
    public List<FlightEntity> findAll() {
        List<FlightEntity> flights = new ArrayList<>();
        String sql = "SELECT * FROM flights";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                flights.add(mapResultSetToFlight(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    @Override
    public FlightEntity findById(Long id) {
        String sql = "SELECT * FROM flights WHERE flight_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToFlight(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(FlightEntity flight) {
        String sql = "INSERT INTO flights (airline_id, flight_number, departure_airport, arrival_airport, departure_time, arrival_time, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        	pstmt.setLong(1, flight.getAirlineId());
            pstmt.setString(2, flight.getFlightNumber());
            pstmt.setLong(3, flight.getDepartureAirport());
            pstmt.setLong(4, flight.getArrivalAirport());
            pstmt.setTimestamp(5, Timestamp.valueOf(flight.getDepartureTime()));
            pstmt.setTimestamp(6, Timestamp.valueOf(flight.getArrivalTime()));
            pstmt.setString(7, flight.getStatus());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {	
                ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    flight.setFlightId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(FlightEntity flight) {
        String sql = "UPDATE flights SET airline_id=?, flight_number=?, departure_airport=?, arrival_airport=?, departure_time=?, arrival_time=?, status=? WHERE flight_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, flight.getAirlineId());
            pstmt.setString(2, flight.getFlightNumber());
            pstmt.setLong(3, flight.getDepartureAirport());
            pstmt.setLong(4, flight.getArrivalAirport());
            pstmt.setTimestamp(5, Timestamp.valueOf(flight.getDepartureTime()));
            pstmt.setTimestamp(6, Timestamp.valueOf(flight.getArrivalTime()));
            pstmt.setString(7, flight.getStatus());
            pstmt.setLong(8, flight.getFlightId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM flights WHERE flight_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private FlightEntity mapResultSetToFlight(ResultSet rs) throws SQLException {
        FlightEntity flight = new FlightEntity();
        flight.setFlightId(rs.getLong("flight_id"));
        flight.setAirlineId(rs.getLong("airline_id"));
        flight.setFlightNumber(rs.getString("flight_number"));
        flight.setDepartureAirport(rs.getLong("departure_airport"));
        flight.setArrivalAirport(rs.getLong("arrival_airport"));
        flight.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
        flight.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
        flight.setStatus(rs.getString("status"));
        return flight;
    }
}
