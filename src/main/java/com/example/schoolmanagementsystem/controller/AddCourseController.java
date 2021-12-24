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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddCourseController implements Initializable {
	private final List<String> proList = new ArrayList<>();
	@FXML
	private AnchorPane sceneAddCourse;
	@FXML
	private TextField courseCode;
	@FXML
	private TextField courseName;
	@FXML
	private ChoiceBox<String> profOptions;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			String query = "SELECT professorID, name, surname FROM professor";
			Statement statement = connectToDB().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				proList.add(resultSet.getInt("professorID") + " " + resultSet.getString("name") + " "
						+ resultSet.getString("surname"));
			}
			profOptions.getItems().addAll(proList);
			connectToDB().close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void submit() throws SQLException, ClassNotFoundException {
		String courseNameField = courseName.getText();
		String courseCodeField = courseCode.getText();
		String profIDField = profOptions.getValue();
		String[] id = profIDField.split(" ");
		int profID = Integer.parseInt(id[0]);
		String query = "SELECT professor_ID FROM professor WHERE professorID = ?";
		PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
		prepareStatement.setInt(1, profID);
		ResultSet resultSet = prepareStatement.executeQuery();
		int profIndex = 0;
		if (resultSet.next()) {
			profIndex = resultSet.getInt("professor_ID");
		}
		String query1 = "INSERT INTO course (courseName, courseCode)" + "VALUES(?, ?)";
		PreparedStatement prepareStatement1 = connectToDB().prepareStatement(query1);
		prepareStatement1.setString(1, courseNameField);
		prepareStatement1.setString(2, courseCodeField);
		prepareStatement1.execute();
		prepareStatement1.close();
		String query2 = "SELECT courseID FROM course WHERE courseName = ?";
		PreparedStatement prepareStatement2 = connectToDB().prepareStatement(query2);
		prepareStatement2.setString(1, courseNameField);
		ResultSet resultSet1 = prepareStatement2.executeQuery();
		int courseIndex = 0;
		if (resultSet1.next()) {
			courseIndex = resultSet1.getInt("courseID");
		}
		String query3 = "INSERT INTO prof_course_junction (professor_ID, courseID)" + "VALUES(?, ?)";
		PreparedStatement prepareStatement3 = connectToDB().prepareStatement(query3);
		prepareStatement3.setInt(1, profIndex);
		prepareStatement3.setInt(2, courseIndex);
		prepareStatement3.execute();
		connectToDB().close();
		courseCode.clear();
		courseName.clear();
		Platform.runLater(() -> courseName.requestFocus());
	}

	public void closeAddCourse() {
		Stage stage = (Stage) sceneAddCourse.getScene().getWindow();
		stage.close();
	}

	private Connection connectToDB() throws SQLException, ClassNotFoundException {
		DBconnect dBconnect = new DBconnect();
		Connection connection = dBconnect.getConnection();
		return connection;
	}
}
