package com.example.schoolmanagementsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.schoolmanagementsystem.DBconnect;
import com.example.schoolmanagementsystem.datamodel.AcademicYear;
import com.example.schoolmanagementsystem.datamodel.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AcademicYearTabController implements Initializable {
	private final ObservableList<AcademicYear> academicYearList = FXCollections.observableArrayList();
	private final ObservableList<Student> studentList = FXCollections.observableArrayList();
	@FXML
	private TableView<AcademicYear> academicYearTableView;
	@FXML
	private TableColumn<AcademicYear, Date> startDateColumn;
	@FXML
	private TableColumn<AcademicYear, Date> endDateColumn;
	@FXML
	private TableColumn<AcademicYear, String> courseColumn;
	@FXML
	private TableView<Student> studentTableView;
	@FXML
	private TableColumn<Student, Integer> studentIDColumn;
	@FXML
	private TableColumn<Student, String> studentNameColumn;
	@FXML
	private TableColumn<Student, String> studentSurnameColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startDateColumn.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
		endDateColumn.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
		courseColumn.setCellValueFactory(new PropertyValueFactory<>("Course"));
		studentIDColumn.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
		studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
		studentSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("Surname"));

		try {
			getAcademicYear();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		academicYearTableView.setOnMouseClicked(e -> {
			int tTableID = academicYearTableView.getSelectionModel().selectedItemProperty().getValue().getTtableID();
			try {
				getStudentByAcademicYearID(tTableID);
				studentList.clear();
				studentTableView.getItems().clear();
				getStudentByAcademicYearID(tTableID);
			} catch (SQLException | ClassNotFoundException c) {
				c.printStackTrace();
			}
		});
	}

	public void removeStudent() throws SQLException, ClassNotFoundException {
		int student_ID = studentTableView.getSelectionModel().selectedItemProperty().getValue().getStudent_ID();
		String query = "UPDATE student SET tTableID = NULL WHERE student_ID = ?";
		int oldSize = studentTableView.getItems().size();
		int indexOfStudent = studentTableView.getSelectionModel().getSelectedIndex();
		AcademicYear selectedYear = academicYearTableView.getSelectionModel().selectedItemProperty().getValue();
		PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
		prepareStatement.setInt(1, student_ID);
		prepareStatement.execute();
		connectToDB().close();
		studentTableView.getItems().remove(indexOfStudent);
		int newSize = studentTableView.getItems().size();
		if (oldSize < newSize) {
			studentTableView.setItems(studentList);
		}
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText("Student removed from the academic year " + selectedYear);
		alert.showAndWait();
	}

	private void getStudentByAcademicYearID(int tTableID) throws SQLException, ClassNotFoundException {
		String query = "SELECT* FROM student WHERE tTableID = ?";
		PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
		prepareStatement.setInt(1, tTableID);
		ResultSet resultSet = prepareStatement.executeQuery();
		while (resultSet.next()) {
			int student_ID = resultSet.getInt("student_ID");
			int studentID = resultSet.getInt("studentID");
			String name = resultSet.getString("name");
			String surname = resultSet.getString("surname");
			int age = resultSet.getInt("age");
			LocalDate DoB = resultSet.getDate("DoB").toLocalDate();
			String gender = resultSet.getString("gender");
			studentList.add(new Student(student_ID, studentID, name, surname, gender, age, DoB));
		}
		studentTableView.setItems(studentList);
		connectToDB().close();
	}

	private void getAcademicYear() throws SQLException, ClassNotFoundException {
		String query = "SELECT* FROM timetable";
		Statement statement = connectToDB().createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			int ttableID = resultSet.getInt("tTableID");
			LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
			LocalDate endDate = resultSet.getDate("endDate").toLocalDate();
			String course = resultSet.getString("course");
			academicYearList.add(new AcademicYear(ttableID, startDate, endDate, course));
		}
		academicYearTableView.setItems(academicYearList);
		connectToDB().close();
	}

	public void goBack(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(
				Objects.requireNonNull(getClass().getResource("/com/example/schoolmanagementsystem/Dashboard.fxml")));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void academicYearWindow() throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addAcademicYear.fxml")));
		stage.setTitle("Add new academic year");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}

	private Connection connectToDB() throws SQLException, ClassNotFoundException {
		DBconnect dBconnect = new DBconnect();
		Connection connection = dBconnect.getConnection();
		return connection;
	}

}
