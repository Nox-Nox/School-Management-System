package com.example.schoolmanagementsystem.controller;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.schoolmanagementsystem.DBconnect;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddModuleController implements Initializable {
	private List<String> courseList = new ArrayList<>();
	private List<String> proList = new ArrayList<>();
	private String moduleNameField;
	private String moduleCodeField;
	@FXML
	private ChoiceBox<String> courseOptions;
	@FXML
	private ChoiceBox<String> professorOptions;
	@FXML
	private TextField moduleCode;
	@FXML
	private TextField moduleName;
	@FXML
	private AnchorPane sceneAddModule;
	@FXML
	private Button submitButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getCourse();
		courseOptions.setOnAction(e -> {
			getProfessor();
			courseOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				proList.clear();
				professorOptions.getItems().clear();
				getProfessor();
			});
		});
	}

	private void getCourse() {
		try {
			String query = "SELECT DISTINCT courseName, courseCode FROM course";
			Statement statement = connectToDB().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				courseList.add(resultSet.getString("courseCode") + " " + resultSet.getString("courseName"));
			}
			courseOptions.getItems().addAll(courseList);
			connectToDB().close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void getProfessor() {
		String course = courseOptions.getValue();
		String[] code = course.split(" ");
		String course_code = code[0];
		try {
			String query = "SELECT professor.professorID, professor.name, professor.surname FROM professor JOIN prof_course_junction ON (professor.professor_ID=prof_course_junction.professor_ID) JOIN course ON (course.courseID=prof_course_junction.courseID) WHERE course.courseCode = ?";
			PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
			prepareStatement.setString(1, course_code);
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				proList.add(resultSet.getInt("professorID") + " " + resultSet.getString("name") + " "
						+ resultSet.getString("surname"));
			}
			professorOptions.getItems().addAll(proList);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void submit() throws SQLException, ClassNotFoundException {
		moduleNameField = moduleName.getText();
		moduleCodeField = moduleCode.getText();
		String[] id = professorOptions.getValue().split(" ");
		String prof_id = id[0];
		int courseID = 0;
		String query = "SELECT course.courseID FROM course JOIN prof_course_junction ON (course.courseID=prof_course_junction.courseID) JOIN professor ON (professor.professor_ID=prof_course_junction.professor_ID) WHERE professorID = ?";
		PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
		prepareStatement.setString(1, prof_id);
		ResultSet resultSet = prepareStatement.executeQuery();
		if (resultSet.next()) {
			courseID = resultSet.getInt("courseID");
		}
		resultSet.close();

		String query1 = "INSERT INTO module (moduleCode, moduleName, courseID)" + "VALUES(?, ?, ?)";
		PreparedStatement prepareStatement1 = connectToDB().prepareStatement(query1);
		prepareStatement1.setString(1, moduleCodeField);
		prepareStatement1.setString(2, moduleNameField);
		prepareStatement1.setInt(3, courseID);
		prepareStatement1.execute();
		connectToDB().close();
		professorOptions.getItems().clear();
		submitButton.setOnAction(e -> {
			courseOptions.getItems().clear();
		});
		moduleCode.clear();
		moduleName.clear();
		Platform.runLater(() -> moduleName.requestFocus());
	}

	public void closeAddModule() {
		Stage stage = (Stage) sceneAddModule.getScene().getWindow();
		stage.close();
	}

	private Connection connectToDB() throws SQLException, ClassNotFoundException {
		DBconnect dBconnect = new DBconnect();
		Connection connection = dBconnect.getConnection();
		return connection;
	}
}
