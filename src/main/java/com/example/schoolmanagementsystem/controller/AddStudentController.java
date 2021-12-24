package com.example.schoolmanagementsystem.controller;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;

import com.example.schoolmanagementsystem.DBconnect;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddStudentController implements Initializable {
	private final String[] genderList = { "Male", "Female", "Other" };
	private Integer studentIDField;
	private String nameField;
	private String surnameField;
	private String genderField;
	private String courseNameField;
	private Date dateField;
	private Integer academicYearIDField;
	private List<String> courseList = new ArrayList<>();
	private List<String> academicList = new ArrayList<>();
	@FXML
	private DatePicker date;
	@FXML
	private ChoiceBox<String> genderOptions;
	@FXML
	private ChoiceBox<String> courseOptions;
	@FXML
	private ChoiceBox<String> academicOptions;
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
		courseOptions.setOnAction(e -> {
			getAcademicYear();
			courseOptions.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
				academicList.clear();
				academicOptions.getItems().clear();
				getAcademicYear();
			}));
		});
	}

	private void getCourse() {
		try {
			String query = "SELECT DISTINCT courseName FROM course";
			Statement statement = connectToDB().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				courseList.add(resultSet.getString("courseName"));
			}
			courseOptions.getItems().addAll(courseList);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void getAcademicYear() {
		String course = courseOptions.getValue();
		try {
			String query = "SELECT tTableID, startDate, endDate FROM timetable WHERE course = ?";
			PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
			prepareStatement.setString(1, course);
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				academicList.add(resultSet.getInt("tTableID") + " " + resultSet.getDate("startDate") + "/"
						+ resultSet.getDate("endDate"));
			}
			academicOptions.getItems().addAll(academicList);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void submit() throws SQLException, ClassNotFoundException {
		studentIDField = Integer.parseInt(studentID.getText());
		nameField = name.getText();
		surnameField = surname.getText();
		genderField = genderOptions.getValue();
		courseNameField = courseOptions.getValue();
		dateField = Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.sql.Date sqlDate = new java.sql.Date(dateField.getTime());
		LocalDate birthday = date.getValue();
		Period period = Period.between(birthday, LocalDate.now());
		int age = period.getYears();
		String[] formatAcademicID = academicOptions.getValue().split(" ");
		academicYearIDField = Integer.parseInt(formatAcademicID[0]);

		String query = "SELECT courseID FROM course WHERE courseName = ?";
		PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
		prepareStatement.setString(1, courseNameField);
		ResultSet resultSet = prepareStatement.executeQuery();
		int courseIndex = 0;
		if (resultSet.next()) {
			courseIndex = resultSet.getInt("courseID");
		}

		String query1 = "INSERT INTO student (studentID, name, surname, age, DoB, gender, courseID, tTableID)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement prepareStmt1 = connectToDB().prepareStatement(query1);
		prepareStmt1.setInt(1, studentIDField);
		prepareStmt1.setString(2, nameField);
		prepareStmt1.setString(3, surnameField);
		prepareStmt1.setInt(4, age);
		prepareStmt1.setDate(5, sqlDate);
		prepareStmt1.setString(6, genderField);
		prepareStmt1.setInt(7, courseIndex);
		prepareStmt1.setInt(8, academicYearIDField);
		prepareStmt1.execute();
		connectToDB().close();
		Stage stage = (Stage) sceneAddStudent.getScene().getWindow();
		stage.close();
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
		Connection connection = dBconnect.getConnection();
		return connection;
	}

}
