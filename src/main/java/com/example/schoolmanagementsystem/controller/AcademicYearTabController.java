package com.example.schoolmanagementsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AcademicYearTabController implements Initializable {
	private final ObservableList<AcademicYear> academicYearList = FXCollections.observableArrayList();
	@FXML
	private TableView<AcademicYear> AcademicYearTableView;
	@FXML
	private TableColumn<AcademicYear, Date> startDateColumn;
	@FXML
	private TableColumn<AcademicYear, Date> endDateColumn;
	@FXML
	private TableColumn<AcademicYear, String> courseColumn;
	@FXML
	private ListView<String> ModuleListView;
	@FXML
	private TableView<Student> StudentTableView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startDateColumn.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
		endDateColumn.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
		courseColumn.setCellValueFactory(new PropertyValueFactory<>("Course"));
		try {
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
			AcademicYearTableView.setItems(academicYearList);
			connectToDB().close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void addStudent() {
	}

	public void deleteStudent() {
	}

	public void addModule() {
	}

	public void deleteModule() {
	}

	public void createAcademicYEar() {
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
