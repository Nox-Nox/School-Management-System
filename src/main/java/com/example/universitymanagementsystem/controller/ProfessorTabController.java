package com.example.universitymanagementsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.universitymanagementsystem.DBconnect;
import com.example.universitymanagementsystem.datamodel.Course;
import com.example.universitymanagementsystem.datamodel.Professor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ProfessorTabController implements Initializable {
	private final String[] searchOption = { "ProfessorID", "Name", "Surname" };
	private final ObservableList<Professor> professorList = FXCollections.observableArrayList();
	private final ObservableList<Course> courseListView = FXCollections.observableArrayList();
	private final ObservableList<Course> courseList = FXCollections.observableArrayList();
	private final FilteredList<Professor> filteredList = new FilteredList<>(professorList, b -> true);
	private final SortedList<Professor> professorSortedList = new SortedList<>(filteredList);
	@FXML
	private TableView<Professor> professorTable;
	@FXML
	private TableColumn<Professor, Integer> professorID;
	@FXML
	private TableColumn<Professor, String> name;
	@FXML
	private TableColumn<Professor, String> surname;
	@FXML
	private TextField searchProfessor;
	@FXML
	private ChoiceBox<String> filterProfessor;
	@FXML
	private ChoiceBox<Course> courseOptions;
	@FXML
	private ListView<Course> professorCourseView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		filterProfessor.getItems().addAll(searchOption);
		professorID.setCellValueFactory(new PropertyValueFactory<>("ProfessorID"));
		name.setCellValueFactory(new PropertyValueFactory<>("Name"));
		surname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
		try {
			getCourse();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			String query = "SELECT* FROM professor";
			Statement statement = connectToDB().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Integer professor_ID = resultSet.getInt("professor_ID");
				Integer professorID = resultSet.getInt("professorID");
				String name = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				String gender = resultSet.getString("gender");
				Integer age = resultSet.getInt("age");
				LocalDate date = LocalDate.parse(resultSet.getString("DoB"));
				professorList.add(new Professor(professor_ID, professorID, name, surname, gender, age, date));
			}
			professorTable.setItems(professorList);
			connectToDB().close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		searchProfessor.textProperty()
				.addListener((observable, oldValue, newValue) -> filteredList.setPredicate(Professor -> {
					if (newValue.isBlank() || newValue.isEmpty()) {
						return true;
					}
					String searchWord = newValue.toLowerCase();
					if (!filterProfessor.getSelectionModel().isEmpty()) {
						if (filterProfessor.getValue().equals("ProfessorID")) {
							return Professor.professorIDProperty().toString().contains(searchWord);
						} else if (filterProfessor.getValue().equals("Name")) {
							return Professor.getName().toLowerCase().contains(searchWord);
						} else if (filterProfessor.getValue().equals("Surname")) {
							return Professor.getSurname().toLowerCase().contains(searchWord);
						}
					} else if (Professor.professorIDProperty().toString().contains(searchWord)) {
						return true;
					} else if (Professor.getName().toLowerCase().contains(searchWord)) {
						return true;
					} else
						return Professor.getSurname().toLowerCase().contains(searchWord);
					return false;
				}));
		professorSortedList.comparatorProperty().bind(professorTable.comparatorProperty());
		professorTable.setItems(professorSortedList);
		professorTable.setOnMouseClicked(e -> {
			int professorID = professorTable.getSelectionModel().selectedItemProperty().getValue().getProfessorID();
			try {
				getCourseByProfessorID(professorID);
				courseListView.clear();
				professorCourseView.getItems().clear();
				getCourseByProfessorID(professorID);
			} catch (SQLException | ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		});
	}

	public void addCourse() throws SQLException, ClassNotFoundException {
		int professor_ID = professorTable.getSelectionModel().selectedItemProperty().getValue().getProfessor_ID();
		int courseID = courseOptions.getValue().getCourseID();
		int oldSize = professorCourseView.getItems().size();
		Course selectedCourse = courseOptions.getValue();
		String professorName = professorTable.getSelectionModel().selectedItemProperty().getValue().getName();
		String query = "INSERT INTO prof_course_junction (professor_ID, courseID)" + "VALUES(?, ?)";
		PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
		prepareStatement.setInt(1, professor_ID);
		prepareStatement.setInt(2, courseID);
		prepareStatement.execute();
		connectToDB().close();
		professorCourseView.getItems().add(selectedCourse);
		int newSize = professorCourseView.getItems().size();
		if (oldSize > newSize) {
			professorCourseView.setItems(courseListView);
		}
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText("Course added to professor " + professorName);
		alert.showAndWait();
	}

	public void removeCourse() throws SQLException, ClassNotFoundException {
		int professor_ID = professorTable.getSelectionModel().selectedItemProperty().getValue().getProfessor_ID();
		int courseID = professorCourseView.getSelectionModel().selectedItemProperty().getValue().getCourseID();
		int indexOfCourse = professorCourseView.getSelectionModel().getSelectedIndex();
		int oldSize = professorCourseView.getItems().size();
		String professorName = professorTable.getSelectionModel().selectedItemProperty().getValue().getName();
		String query = "DELETE prof_course_junction FROM prof_course_junction JOIN professor ON (prof_course_junction.professor_ID=professor.professor_ID) JOIN course ON (prof_course_junction.courseID=course.courseID) WHERE prof_course_junction.courseID = ? AND prof_course_junction.professor_ID = ?";
		PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
		prepareStatement.setInt(1, courseID);
		prepareStatement.setInt(2, professor_ID);
		prepareStatement.execute();
		connectToDB().close();
		professorCourseView.getItems().remove(indexOfCourse);
		int newSize = professorCourseView.getItems().size();
		if (oldSize < newSize) {
			professorCourseView.setItems(courseListView);
		}
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText("Course removed from professor " + professorName);
		alert.showAndWait();
	}

	private void getCourse() throws SQLException, ClassNotFoundException {
		String query = "SELECT* FROM course";
		Statement statement = connectToDB().createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			courseList.add(new Course(resultSet.getInt("courseID"), resultSet.getString("courseCode"),
					resultSet.getString("courseName")));
		}
		courseOptions.setItems(courseList);
	}

	private void getCourseByProfessorID(int professorID) throws SQLException, ClassNotFoundException {
		String query = "SELECT course.* FROM course JOIN prof_course_junction ON (course.courseID=prof_course_junction.courseID) JOIN professor ON (prof_course_junction.professor_ID=professor.professor_ID) WHERE professorID = ?";
		PreparedStatement prepareStatement = connectToDB().prepareStatement(query);
		prepareStatement.setInt(1, professorID);
		ResultSet resultSet = prepareStatement.executeQuery();
		while (resultSet.next()) {
			courseListView.add(new Course(resultSet.getInt("courseID"), resultSet.getString("courseCode"),
					resultSet.getString("courseName")));
		}
		professorCourseView.setItems(courseListView);
		connectToDB().close();
	}

	public void reset() {
		filterProfessor.getItems().clear();
		filterProfessor.getItems().addAll(searchOption);
	}

	public void goBack(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(
				Objects.requireNonNull(getClass().getResource("/com/example/universitymanagementsystem/Dashboard.fxml")));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	private Connection connectToDB() throws SQLException, ClassNotFoundException {
		DBconnect dBconnect = new DBconnect();
		return dBconnect.getConnection();
	}
}
