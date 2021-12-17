package com.example.schoolmanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {
	private final String[] genderOptions = { "Male", "Female", "Other" };
	Integer studentIDField;
	String nameField;
	String surnameField;
	String genderField;
	Date dateField;
	@FXML
	private TextField age;
	@FXML
	private DatePicker date;
	@FXML
	private ChoiceBox<String> gender;
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
	}

	public void submit() throws SQLException, ClassNotFoundException {
		studentIDField = Integer.parseInt(studentID.getText());
		nameField = name.getText();
		surnameField = surname.getText();
		genderField = gender.getValue();
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
