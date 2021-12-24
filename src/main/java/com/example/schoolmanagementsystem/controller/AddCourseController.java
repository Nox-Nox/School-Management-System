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
	AnchorPane sceneAddCourse;
	@FXML
	private TextField courseCode;
	@FXML
	private TextField courseName;
	@FXML
	private ChoiceBox<String> profOptions;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			DBconnect dbConnect = new DBconnect();
			Connection connectDB = dbConnect.getConnection();
			String query = "SELECT professorID, name, surname FROM professor";
			Statement statement = connectDB.createStatement();
			ResultSet queryOut = statement.executeQuery(query);

			while (queryOut.next()) {
				proList.add(queryOut.getInt("professorID") + " " + queryOut.getString("name") + " "
						+ queryOut.getString("surname"));
			}
			profOptions.getItems().addAll(proList);
			connectDB.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void submit() throws SQLException, ClassNotFoundException{
		String courseNameField = courseName.getText();
		String courseCodeField = courseCode.getText();
		String profIDField = profOptions.getValue();
		String[] id = profIDField.split(" ");
		int profID = Integer.parseInt(id[0]);

		DBconnect dbConnect = new DBconnect();
		Connection connectDB = dbConnect.getConnection();
		String query = "SELECT professor_ID FROM professor WHERE professorID = ?";
		PreparedStatement prepareStmt = connectDB.prepareStatement(query);
		prepareStmt.setInt(1, profID);
		ResultSet resultSet = prepareStmt.executeQuery();
		int profIndex = 0;

		if (resultSet.next()) {
			profIndex = resultSet.getInt("professor_ID");
		}

		String query1 = "INSERT INTO course (courseName, courseCode)" + "VALUES(?, ?)";
		PreparedStatement prepareStmt1 = connectDB.prepareStatement(query1);
		prepareStmt1.setString(1, courseNameField);
		prepareStmt1.setString(2, courseCodeField);
		prepareStmt1.execute();
		prepareStmt1.close();

		String query2 = "SELECT courseID FROM course WHERE courseName = ?";
		PreparedStatement preparedStmt2 = connectDB.prepareStatement(query2);
		preparedStmt2.setString(1, courseNameField);
		ResultSet resultSet1 = preparedStmt2.executeQuery();
		int courseIndex = 0;

		if (resultSet1.next()) {
			courseIndex = resultSet1.getInt("courseID");
		}

		String query3 = "INSERT INTO prof_course_junction (professor_ID, courseID)" + "VALUES(?, ?)";
		PreparedStatement prepareStmt3 = connectDB.prepareStatement(query3);
		prepareStmt3.setInt(1, profIndex);
		prepareStmt3.setInt(2, courseIndex);
		prepareStmt3.execute();
		prepareStmt3.close();
		connectDB.close();
		courseCode.clear();
		courseName.clear();
		Platform.runLater(() -> courseName.requestFocus());
	}

	public void closeAddCourse() {
		Stage stage = (Stage) sceneAddCourse.getScene().getWindow();
		stage.close();
	}
}
