package com.example.universitymanagementsystem.controller;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import com.example.universitymanagementsystem.DBconnect;
import com.example.universitymanagementsystem.datamodel.AcademicYear;
import com.example.universitymanagementsystem.datamodel.Course;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddStudentController implements Initializable {
	private final String[] genderList = { "Male", "Female", "Other" };
	private final ObservableList<Course> courseList = FXCollections.observableArrayList();
	private final ObservableList<AcademicYear> academicList = FXCollections.observableArrayList();
	@FXML
	private DatePicker date;
	@FXML
	private ChoiceBox<String> genderOptions;
	@FXML
	private ChoiceBox<Course> courseOptions;
	@FXML
	private ChoiceBox<AcademicYear> academicOptions;
	@FXML
	private TextField name;
	@FXML
	private TextField studentID;
	@FXML
	private TextField surname;
	@FXML
	private AnchorPane sceneAddStudent;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		genderOptions.getItems().addAll(genderList);
		getCourse();

		courseOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (courseOptions.getItems().isEmpty()) {
				getCourse();
			} else {
				getAcademicYear();
				academicList.clear();
				academicOptions.getItems().clear();
				getAcademicYear();
			}
		});
	}

	private void getCourse() {
		try {
			String query = "SELECT* FROM course";
			Statement statement = connectToDB().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				courseList.add(new Course(resultSet.getInt("courseID"), resultSet.getString("courseCode"),
						resultSet.getString("courseName")));
			}
			courseOptions.setItems(courseList);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void getAcademicYear() {
		String course = courseOptions.getValue().getCourseName();
		try {
			String query = "SELECT* FROM timetable WHERE course = ?";
			PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
			prepareStatement.setString(1, course);
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				int ttableID = resultSet.getInt("tTableID");
				LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
				LocalDate endDate = resultSet.getDate("endDate").toLocalDate();
				String course1 = resultSet.getString("course");
				academicList.add(new AcademicYear(ttableID, startDate, endDate, course1));
			}
			academicOptions.setItems(academicList);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void submit() throws SQLException, ClassNotFoundException {
		int studentIDField = Integer.parseInt(studentID.getText());
		String nameField = name.getText();
		String surnameField = surname.getText();
		String genderField = genderOptions.getValue();
		Date dateField = Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.sql.Date sqlDate = new java.sql.Date(dateField.getTime());
		LocalDate birthday = date.getValue();
		Period period = Period.between(birthday, LocalDate.now());
		int age = period.getYears();
		int academicYearIDField = academicOptions.getValue().getTtableID();
		int courseID = courseOptions.getValue().getCourseID();
		String query = "INSERT INTO student (studentID, name, surname, age, DoB, gender, courseID, tTableID)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
		prepareStatement.setInt(1, studentIDField);
		prepareStatement.setString(2, nameField);
		prepareStatement.setString(3, surnameField);
		prepareStatement.setInt(4, age);
		prepareStatement.setDate(5, sqlDate);
		prepareStatement.setString(6, genderField);
		prepareStatement.setInt(7, courseID);
		prepareStatement.setInt(8, academicYearIDField);
		prepareStatement.execute();
		connectToDB().close();
		studentID.clear();
		name.clear();
		surname.clear();
		genderOptions.getItems().clear();
		genderOptions.getItems().addAll(genderList);
		courseOptions.getItems().clear();
		academicOptions.getItems().clear();
		date.getEditor().clear();
	}

	public void generate() throws SQLException, ClassNotFoundException {
		Random random = new Random();
		int ID = random.nextInt(99999999);
		String query = "SELECT studentID FROM student";
		Statement statement = connectToDB().createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			if (resultSet.getInt("studentID") == ID) {
				ID = random.nextInt(99999999);
				studentID.setText(String.valueOf(ID));
			}
		}
		connectToDB().close();
		studentID.setText(String.valueOf(ID));
	}

	public void closeAddStudent() {
		Stage stage = (Stage) sceneAddStudent.getScene().getWindow();
		stage.close();
	}

	private Connection connectToDB() throws SQLException, ClassNotFoundException {
		DBconnect dBconnect = new DBconnect();
		return dBconnect.getConnection();
	}

}
