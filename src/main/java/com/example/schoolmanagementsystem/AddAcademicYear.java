package com.example.schoolmanagementsystem;

import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

public class AddAcademicYear implements Initializable {
	private Date startDateField;
	private Date endDateField;
	private String courseField;
	private List<String> courses = new ArrayList<>();
	@FXML
	private ChoiceBox<String> courseChoiceBox;
	@FXML
	private DatePicker endDatePicker;
	@FXML
	private DatePicker startDatePicker;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			DBconnect dbConnect = new DBconnect();
			Connection connectDB = dbConnect.getConnection();
			String query = "SELECT courseName FROM course";
			Statement statement = connectDB.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()){
				courses.add(resultSet.getString("courseName"));
			}
			courseChoiceBox.getItems().addAll(courses);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void submit() throws SQLException, ClassNotFoundException {
		startDateField = Date.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.sql.Date sqlStartDate = new java.sql.Date(startDateField.getTime());
		endDateField = Date.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.sql.Date sqlEndDate = new java.sql.Date(endDateField.getTime());
		courseField = courseChoiceBox.getValue();
		DBconnect dbConnect = new DBconnect();
		Connection connectDB = dbConnect.getConnection();
		String query = "INSERT INTO timetable (startDate, endDate, course)" + "VALUES (?, ?, ?)";
		PreparedStatement preparedStatement = connectDB.prepareStatement(query);
		preparedStatement.setDate(1, sqlStartDate);
		preparedStatement.setDate(2, sqlEndDate);
		preparedStatement.setString(3,courseField);
		preparedStatement.execute();
		connectDB.close();
	}
}
