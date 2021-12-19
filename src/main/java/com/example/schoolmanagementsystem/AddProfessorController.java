package com.example.schoolmanagementsystem;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddProfessorController implements Initializable {

	private final String[] genderOptions = { "Male", "Female", "Other" };
	int professorIDField;
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
	private TextField professorID;
	@FXML
	private TextField surname;
	@FXML
	private AnchorPane sceneAddProfessor;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gender.getItems().addAll(genderOptions);
	}

	public void submit() throws SQLException, ClassNotFoundException {
		professorIDField = Integer.parseInt(professorID.getText());
		nameField = name.getText();
		surnameField = surname.getText();
		genderField = gender.getValue();
		dateField = Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.sql.Date sqlDate = new java.sql.Date(dateField.getTime());
		LocalDate birthday = date.getValue();
		Period period = Period.between(birthday, LocalDate.now());
		int age = period.getYears();

		DBconnect dBconnect = new DBconnect();
		Connection connectDB = dBconnect.getConnection();
		String query = "INSERT INTO professor (professorID, name, surname, gender, age, DoB)"
				+ "VALUES(?, ?, ?, ?, ?, ?)";
		PreparedStatement prepareStmt = connectDB.prepareStatement(query);
		prepareStmt.setInt(1, professorIDField);
		prepareStmt.setString(2, nameField);
		prepareStmt.setString(3, surnameField);
		prepareStmt.setString(4, genderField);
		prepareStmt.setInt(5, age);
		prepareStmt.setDate(6, sqlDate);
		prepareStmt.execute();
		connectDB.close();
		Stage stage = (Stage) sceneAddProfessor.getScene().getWindow();
		stage.close();
	}

	public void generate() throws SQLException, ClassNotFoundException {
		Random random = new Random();
		int ID = random.nextInt(99999);
		professorID.setText(String.valueOf(ID));
		DBconnect dbConnect = new DBconnect();
		Connection connectDB = dbConnect.getConnection();
		String query = "SELECT professorID FROM professor";
		Statement statement = connectDB.createStatement();
		ResultSet queryOut = statement.executeQuery(query);
		while (queryOut.next()) {
			if (queryOut.getInt("professorID") == ID) {
				ID = random.nextInt(99999999);
				professorID.setText(String.valueOf(ID));
			}
		}
		professorID.setText(String.valueOf(ID));
	}

	public void closeAddProfessor() {
		Stage stage = (Stage) sceneAddProfessor.getScene().getWindow();
		stage.close();
	}
}
