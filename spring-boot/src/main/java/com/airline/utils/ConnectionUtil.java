package com.airline.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	private static String DB_URL = "jdbc:mysql://localhost:3306/qairline";
	private static String USER = "root";
	private static String PASS = "HoangAnh1003";
	
	public static final Connection getConnection() {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			return conn;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}