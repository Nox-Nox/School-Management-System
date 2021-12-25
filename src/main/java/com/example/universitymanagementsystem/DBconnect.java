package com.example.universitymanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {
	public Connection dbconnect;

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		String dbName = "school";
		String dbUser = "root";
		String dbPass = "DianaWhore99";
		String url = "jdbc:mysql://localhost:3306/" + dbName;
		// mysql jConnector class name inside the jar folder of the driver
		Class.forName("com.mysql.cj.jdbc.Driver");
		dbconnect = DriverManager.getConnection(url, dbUser, dbPass);
		return dbconnect;
	}
}
