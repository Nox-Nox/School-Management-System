package com.example.universitymanagementsystem.controller;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.universitymanagementsystem.DBconnect;
import com.example.universitymanagementsystem.datamodel.Course;
import com.example.universitymanagementsystem.datamodel.Professor;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddModuleController implements Initializable {
	private final ObservableList<Course> courseList = FXCollections.observableArrayList();
	private final ObservableList<Professor> proList = FXCollections.observableArrayList();
	@FXML
	private ChoiceBox<Course> courseOptions;
	@FXML
	private ChoiceBox<Professor> professorOptions;
	@FXML
	private TextField moduleCode;
	@FXML
	private TextField moduleName;
	@FXML
	private AnchorPane sceneAddModule;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getCourse();

		courseOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (courseOptions.getItems().isEmpty()) {
				getCourse();
			} else {
				getProfessor();
				proList.clear();
				professorOptions.getItems().clear();
				getProfessor();
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
			connectToDB().close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private void getProfessor() {
		String course_code = courseOptions.getValue().getCourseCode();
		try {
			String query = "SELECT professor.* FROM professor JOIN prof_course_junction ON (professor.professor_ID=prof_course_junction.professor_ID) JOIN course ON (course.courseID=prof_course_junction.courseID) WHERE course.courseCode = ?";
			PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
			prepareStatement.setString(1, course_code);
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				int professor_ID = resultSet.getInt("professor_ID");
				int professorID = resultSet.getInt("professorID");
				String name = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				String gender = resultSet.getString("gender");
				int age = resultSet.getInt("age");
				LocalDate DoB = resultSet.getDate("DoB").toLocalDate();
				proList.add(new Professor(professor_ID, professorID, name, surname, gender, age, DoB));
			}
			professorOptions.setItems(proList);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void submit() throws SQLException, ClassNotFoundException {
		String moduleNameField = moduleName.getText();
		String moduleCodeField = moduleCode.getText();
		int courseID = courseOptions.getValue().getCourseID();
		String query1 = "INSERT INTO module (moduleCode, moduleName, courseID)" + "VALUES(?, ?, ?)";
		PreparedStatement prepareStatement1 = connectToDB().prepareStatement(query1);
		prepareStatement1.setString(1, moduleCodeField);
		prepareStatement1.setString(2, moduleNameField);
		prepareStatement1.setInt(3, courseID);
		prepareStatement1.execute();
		connectToDB().close();
		moduleName.clear();
		moduleCode.clear();
		professorOptions.getItems().clear();
		courseOptions.getItems().clear();
		Platform.runLater(() -> moduleName.requestFocus());
	}

	public void closeAddModule() {
		Stage stage = (Stage) sceneAddModule.getScene().getWindow();
		stage.close();
	}

	private Connection connectToDB() throws SQLException, ClassNotFoundException {
		DBconnect dBconnect = new DBconnect();
		return dBconnect.getConnection();
	}
}
