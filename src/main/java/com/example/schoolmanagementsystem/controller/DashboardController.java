package com.example.schoolmanagementsystem.controller;

import static java.util.Objects.requireNonNull;
import static javafx.fxml.FXMLLoader.load;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.example.schoolmanagementsystem.DBconnect;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

	ObservableList<PieChart.Data> pieList = FXCollections.observableArrayList();
	@FXML
	private AnchorPane sceneDashboard;
	@FXML
	private PieChart summaryPie;

	public void switchToStudent(ActionEvent e) throws IOException {
		Parent root = load(
				requireNonNull(getClass().getResource("/com/example/schoolmanagementsystem/Tabs/StudentTab.fxml")));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToProfessor(ActionEvent e) throws IOException {
		Parent root = load(
				requireNonNull(getClass().getResource("/com/example/schoolmanagementsystem/Tabs/ProfessorTab.fxml")));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToSchedule(ActionEvent e) throws IOException {
		Parent root = load(requireNonNull(
				getClass().getResource("/com/example/schoolmanagementsystem/Tabs/AcademicYearControllerTab.fxml")));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void addStudent() throws IOException {
		Stage stage = new Stage();
		Parent root = load(
				requireNonNull(getClass().getResource("/com/example/schoolmanagementsystem/addStudent.fxml")));
		stage.setTitle("Add new student");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}

	public void addProfessor() throws IOException {
		Stage stage = new Stage();
		Parent root = load(
				requireNonNull(getClass().getResource("/com/example/schoolmanagementsystem/addProfessor.fxml")));
		stage.setTitle("Add new professor");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}

	public void addCourse() throws IOException {
		Stage stage = new Stage();
		Parent root = load(
				requireNonNull(getClass().getResource("/com/example/schoolmanagementsystem/addCourse.fxml")));
		stage.setTitle("Add new course");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}

	public void addModule() throws IOException {
		Stage stage = new Stage();
		Parent root = load(
				requireNonNull(getClass().getResource("/com/example/schoolmanagementsystem/addModule.fxml")));
		stage.setTitle("Add new module");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}

	public void addAcademicYear() throws IOException {
		Stage stage = new Stage();
		Parent root = load(
				requireNonNull(getClass().getResource("/com/example/schoolmanagementsystem/addAcademicYear.fxml")));
		stage.setTitle("Add new academic year");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}

	public void closeDashboard() {
		Stage stage = (Stage) sceneDashboard.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> sceneDashboard.requestFocus());
		try {
			getSummary();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection connectToDB() throws SQLException, ClassNotFoundException {
		DBconnect dBconnect = new DBconnect();
		Connection connection = dBconnect.getConnection();
		return connection;
	}

	private void getSummary() throws SQLException, ClassNotFoundException {
		String query = "SELECT count(*) AS studentCount FROM student";
		String query1 = "SELECT count(*) AS professorCount FROM professor";
		String query2 = "SELECT count(*) AS courseCount FROM course";
		String query3 = "SELECT count(*) AS moduleCount FROM module";
		Statement statement = connectToDB().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		Statement statement1 = connectToDB().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		Statement statement2 = connectToDB().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		Statement statement3 = connectToDB().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet resultSet = statement.executeQuery(query);
		ResultSet resultSet1 = statement1.executeQuery(query1);
		ResultSet resultSet2 = statement2.executeQuery(query2);
		ResultSet resultSet3 = statement3.executeQuery(query3);

		if (resultSet.next()) {
			int rows = resultSet.getInt("studentCount");

			pieList.add(new PieChart.Data("Students", rows));
			System.out.println(rows);
		}

		if (resultSet1.next()) {
			int rows = resultSet1.getInt("professorCount");

			pieList.add(new PieChart.Data("Professors", rows));
			System.out.println(rows);
		}

		if (resultSet2.next()) {
			int rows = resultSet2.getInt("courseCount");

			pieList.add(new PieChart.Data("Courses", rows));
			System.out.println(rows);
		}

		if (resultSet3.next()) {
			int rows = resultSet3.getInt("moduleCount");

			pieList.add(new PieChart.Data("Modules", rows));
			System.out.println(rows);
		}
		summaryPie.setData(pieList);
		summaryPie.setTitle("Data Base Summary");

	}

}
