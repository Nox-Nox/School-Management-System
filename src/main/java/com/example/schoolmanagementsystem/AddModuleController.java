package com.example.schoolmanagementsystem;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddModuleController implements Initializable {
	List<String> courseList = new ArrayList<String>();
	List<String> proList = new ArrayList<String>();
	String moduleNameField;
	String moduleCodeField;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
		courseOptions.setOnAction(this::check); // " :: " method reference operator
	}

	public void getProfessor() {
		String course = courseOptions.getValue();
		String[] code = course.split(" ");
		String course_code = code[0];

		try {
			DBconnect dBconnect = new DBconnect();
			Connection connectDB = dBconnect.getConnection();
			String query = "SELECT professor.professorID, professor.name, professor.surname FROM course INNER JOIN professor on course.professor_ID=professor.professor_ID WHERE courseCode = ?";
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

	public void check(ActionEvent event) {
		getProfessor();
		courseOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			proList.clear();
			professorOptions.getItems().clear();
			getProfessor();
		});
	}

	public void submit() throws SQLException, ClassNotFoundException {
		moduleNameField = moduleName.getText();
		moduleCodeField = moduleCode.getText();
		String[] id = professorOptions.getValue().split(" ");
		String prof_id = id[0];
		int courseID = 0;

		DBconnect dbConnect = new DBconnect();
		Connection connectDB = dbConnect.getConnection();
		String query = "SELECT course.courseID FROM course INNER JOIN professor ON course.professor_ID=professor.professor_ID WHERE professorID = ?";
		PreparedStatement prepareStmt = connectDB.prepareStatement(query);
		prepareStmt.setString(1, prof_id);
		ResultSet queryOut = prepareStmt.executeQuery();

		if (queryOut.next()) {
			courseID = queryOut.getInt("courseID");
		}
		queryOut.close();

		String query1 = "INSERT INTO module (moduleCode, moduleName, courseID)" + "VALUES(?, ?, ?)";
		PreparedStatement prepareSmt1 = connectDB.prepareStatement(query1);
		prepareSmt1.setString(1, moduleCodeField);
		prepareSmt1.setString(2, moduleNameField);
		prepareSmt1.setInt(3, courseID);
		prepareSmt1.execute();
		prepareSmt1.close();
		connectDB.close();
		professorOptions.getItems().clear();
		moduleCode.clear();
		moduleName.clear();
		Platform.runLater(() -> moduleName.requestFocus());
	}

	public void closeAddModule() {
		Stage stage = (Stage) sceneAddModule.getScene().getWindow();
		stage.close();
	}
}
