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
		String nameField = name.getText();
		String surnameField = surname.getText();
		String genderField = gender.getValue();
		Date dateField = Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		java.sql.Date sqlDate = new java.sql.Date(dateField.getTime());
		LocalDate birthday = date.getValue();
		Period period = Period.between(birthday, LocalDate.now());
		int age = period.getYears();
		String query = "INSERT INTO professor (professorID, name, surname, gender, age, DoB)"
				+ "VALUES(?, ?, ?, ?, ?, ?)";
		PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
		prepareStatement.setInt(1, professorIDField);
		prepareStatement.setString(2, nameField);
		prepareStatement.setString(3, surnameField);
		prepareStatement.setString(4, genderField);
		prepareStatement.setInt(5, age);
		prepareStatement.setDate(6, sqlDate);
		prepareStatement.execute();
		connectToDB().close();
		Stage stage = (Stage) sceneAddProfessor.getScene().getWindow();
		stage.close();
	}

	public void generate() throws SQLException, ClassNotFoundException {
		Random random = new Random();
		int ID = random.nextInt(99999);
		professorID.setText(String.valueOf(ID));
		String query = "SELECT professorID FROM professor";
		Statement statement = connectToDB().createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			if (resultSet.getInt("professorID") == ID) {
				ID = random.nextInt(99999999);
				professorID.setText(String.valueOf(ID));
			}
		}
		connectToDB().close();
		professorID.setText(String.valueOf(ID));
	}

	public void closeAddProfessor() {
		Stage stage = (Stage) sceneAddProfessor.getScene().getWindow();
		stage.close();
	}

	private Connection connectToDB() throws SQLException, ClassNotFoundException {
		DBconnect dBconnect = new DBconnect();
		return dBconnect.getConnection();
	}
}
