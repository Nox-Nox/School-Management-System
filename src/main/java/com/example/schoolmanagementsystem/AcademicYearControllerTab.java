package com.example.schoolmanagementsystem;

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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AcademicYearControllerTab implements Initializable {
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
	@FXML
	private Button addModule;
	@FXML
	private Button addStudent;
	@FXML
	private Button createAcademicYear;
	@FXML
	private Button deleteModule;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startDateColumn.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
		endDateColumn.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
		courseColumn.setCellValueFactory(new PropertyValueFactory<>("Course"));

		try {
			DBconnect dbConnect = new DBconnect();
			Connection connectDB = dbConnect.getConnection();
			String query = "SELECT* FROM timetable";
			Statement statement = connectDB.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()){
				LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
				LocalDate endDate = resultSet.getDate("endDate").toLocalDate();
				String course = resultSet.getString("course");
				academicYearList.add(new AcademicYear(startDate, endDate, course));
			}
			AcademicYearTableView.setItems(academicYearList);
			connectDB.close();

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

	public void academicYearWindow() throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addAcademicYear.fxml")));
		stage.setTitle("Add new academic year");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}

}
