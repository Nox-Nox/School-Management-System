package com.example.schoolmanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;

public class AddStudentController implements Initializable {
	private final String[] genderOptions = { "Male", "Female", "Other" };
	private List<String> courseOptions = new ArrayList<>();
	Integer studentIDField;
	String nameField;
	String surnameField;
	String genderField;
	String courseIDField;
	Date dateField;
	@FXML
	private TextField age;
	@FXML
	private DatePicker date;
	@FXML
	private ChoiceBox<String> gender;
	@FXML
	private ChoiceBox<String> course;
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
		gender.getItems().addAll(genderOptions);
		try {
		DBconnect dBconnect = new DBconnect();
		Connection connectDB = dBconnect.getConnection();
		String query = "SELECT DISTINCT courseName FROM course";
			Statement statement = connectDB.createStatement();
			ResultSet queryOut = statement.executeQuery(query);

			while (queryOut.next()){
				courseOptions.add(queryOut.getString("courseName"));
			}
			course.getItems().addAll(courseOptions);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public void submit() throws SQLException, ClassNotFoundException {
		studentIDField = Integer.parseInt(studentID.getText());
		nameField = name.getText();
		surnameField = surname.getText();
		genderField = gender.getValue();
		courseIDField = course.getValue();
		dateField = Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.sql.Date sqlDate = new java.sql.Date(dateField.getTime());

		LocalDate birthday = date.getValue();
		Period period = Period.between(birthday, LocalDate.now());

		int age = period.getYears();

		DBconnect dbConnect = new DBconnect();
		Connection connectDB = dbConnect.getConnection();
		String query = "INSERT INTO student (studentID, name, surname, age, DoB, gender, courseID)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement prepareStmt = connectDB.prepareStatement(query);
		prepareStmt.setInt(1, studentIDField);
		prepareStmt.setString(2, nameField);
		prepareStmt.setString(3, surnameField);
		prepareStmt.setInt(4, age);
		prepareStmt.setDate(5, sqlDate);
		prepareStmt.setString(6, genderField);
		prepareStmt.setNull(7, Types.INTEGER);
		prepareStmt.execute();
		connectDB.close();
		Stage stage = (Stage) sceneAddStudent.getScene().getWindow();
		stage.close();
	}

	public void generate() {
		Random random = new Random();
		int ID = random.nextInt(99999999);
		studentID.setText(String.valueOf(ID));
	}

	public void closeAddStudent() {
		Stage stage = (Stage) sceneAddStudent.getScene().getWindow();
		stage.close();
	}

}
