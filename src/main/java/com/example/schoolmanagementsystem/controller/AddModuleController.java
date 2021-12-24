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
//		courseOptions.setOnAction(this::check); // " :: " method reference operator
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
			DBconnect dBconnect = new DBconnect();
			Connection connectDB = dBconnect.getConnection();
			String query = "SELECT DISTINCT courseName, courseCode FROM course";
			Statement statement = connectDB.createStatement();
			ResultSet queryOut = statement.executeQuery(query);

			while (queryOut.next()) {
				courseList.add(queryOut.getString("courseCode") + " " + queryOut.getString("courseName"));
			}
			courseOptions.getItems().addAll(courseList);
			connectDB.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void getProfessor() {
		String course = courseOptions.getValue();
		String[] code = course.split(" ");
		String course_code = code[0];

		try {
			DBconnect dBconnect = new DBconnect();
			Connection connectDB = dBconnect.getConnection();
			String query = "SELECT professor.professorID, professor.name, professor.surname FROM professor JOIN prof_course_junction ON (professor.professor_ID=prof_course_junction.professor_ID) JOIN course ON (course.courseID=prof_course_junction.courseID) WHERE course.courseCode = ?";
			PreparedStatement prepareStmt = connectDB.prepareStatement(query);
			prepareStmt.setString(1, course_code);
			ResultSet queryOut = prepareStmt.executeQuery();

			while (queryOut.next()) {
				proList.add(queryOut.getInt("professorID") + " " + queryOut.getString("name") + " "
						+ queryOut.getString("surname"));
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

		DBconnect dbConnect = new DBconnect();
		Connection connectDB = dbConnect.getConnection();
		String query = "SELECT course.courseID FROM course JOIN prof_course_junction ON (course.courseID=prof_course_junction.courseID) JOIN professor ON (professor.professor_ID=prof_course_junction.professor_ID) WHERE professorID = ?";
		PreparedStatement prepareStmt = connectDB.prepareStatement(query);
		prepareStmt.setString(1, prof_id);
		ResultSet resultSet = prepareStmt.executeQuery();

		if (resultSet.next()) {
			courseID = resultSet.getInt("courseID");
		}
		resultSet.close();

		String query1 = "INSERT INTO module (moduleCode, moduleName, courseID)" + "VALUES(?, ?, ?)";
		PreparedStatement prepareSmt1 = connectDB.prepareStatement(query1);
		prepareSmt1.setString(1, moduleCodeField);
		prepareSmt1.setString(2, moduleNameField);
		prepareSmt1.setInt(3, courseID);
		prepareSmt1.execute();
		connectDB.close();
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
}
