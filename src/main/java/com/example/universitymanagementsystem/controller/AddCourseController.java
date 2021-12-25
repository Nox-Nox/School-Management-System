package com.example.universitymanagementsystem.controller;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.universitymanagementsystem.DBconnect;
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

public class AddCourseController implements Initializable {
	private final ObservableList<Professor> proList = FXCollections.observableArrayList();
	@FXML
	private AnchorPane sceneAddCourse;
	@FXML
	private TextField courseCode;
	@FXML
	private TextField courseName;
	@FXML
	private ChoiceBox<Professor> profOptions;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			String query = "SELECT* FROM professor";
			Statement statement = connectToDB().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
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
			profOptions.setItems(proList);
			connectToDB().close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void submit() throws SQLException, ClassNotFoundException {
		String courseNameField = courseName.getText();
		String courseCodeField = courseCode.getText();
		int profIDField = profOptions.getValue().getProfessor_ID();
		String query = "INSERT INTO course (courseName, courseCode)" + "VALUES(?, ?)";
		PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
		prepareStatement.setString(1, courseNameField);
		prepareStatement.setString(2, courseCodeField);
		prepareStatement.execute();
		prepareStatement.close();
		String query1 = "SELECT courseID FROM course WHERE courseName = ?";
		PreparedStatement prepareStatement1 = connectToDB().prepareStatement(query1);
		prepareStatement1.setString(1, courseNameField);
		ResultSet resultSet1 = prepareStatement1.executeQuery();
		int courseIndex = 0;
		if (resultSet1.next()) {
			courseIndex = resultSet1.getInt("courseID");
		}
		String query2 = "INSERT INTO prof_course_junction (professor_ID, courseID)" + "VALUES(?, ?)";
		PreparedStatement prepareStatement2 = connectToDB().prepareStatement(query2);
		prepareStatement2.setInt(1, profIDField);
		prepareStatement2.setInt(2, courseIndex);
		prepareStatement2.execute();
		connectToDB().close();
		courseCode.clear();
		courseName.clear();
		profOptions.getItems().clear();
		profOptions.setItems(proList);
		Platform.runLater(() -> courseName.requestFocus());
	}

	public void closeAddCourse() {
		Stage stage = (Stage) sceneAddCourse.getScene().getWindow();
		stage.close();
	}

	private Connection connectToDB() throws SQLException, ClassNotFoundException {
		DBconnect dBconnect = new DBconnect();
		return dBconnect.getConnection();
	}
}
