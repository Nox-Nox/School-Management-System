package com.example.universitymanagementsystem.controller;

import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import com.example.universitymanagementsystem.DBconnect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddAcademicYearController implements Initializable {
	private final ObservableList<String> courses = FXCollections.observableArrayList();
	@FXML
	private ChoiceBox<String> courseChoiceBox;
	@FXML
	private DatePicker endDatePicker;
	@FXML
	private DatePicker startDatePicker;
	@FXML
	private AnchorPane sceneAddAcademicYear;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			String query = "SELECT courseName FROM course";
			Statement statement = connectToDB().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				courses.add(resultSet.getString("courseName"));
			}
			courseChoiceBox.setItems(courses);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void submit() throws SQLException, ClassNotFoundException {
		Date startDateField = Date.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.sql.Date sqlStartDate = new java.sql.Date(startDateField.getTime());
		Date endDateField = Date.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.sql.Date sqlEndDate = new java.sql.Date(endDateField.getTime());
		String courseField = courseChoiceBox.getValue();
		String query = "INSERT INTO timetable (startDate, endDate, course)" + "VALUES (?, ?, ?)";
		PreparedStatement preparedStatement = connectToDB().prepareStatement(query);
		preparedStatement.setDate(1, sqlStartDate);
		preparedStatement.setDate(2, sqlEndDate);
		preparedStatement.setString(3, courseField);
		preparedStatement.execute();
		connectToDB().close();
		Stage stage = (Stage) sceneAddAcademicYear.getScene().getWindow();
		stage.close();
	}

	public void closeAcademicYear() {
		Stage stage = (Stage) sceneAddAcademicYear.getScene().getWindow();
		stage.close();
	}

	private Connection connectToDB() throws SQLException, ClassNotFoundException {
		DBconnect dBconnect = new DBconnect();
		return dBconnect.getConnection();
	}
}
