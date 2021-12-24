package com.example.schoolmanagementsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.schoolmanagementsystem.DBconnect;
import com.example.schoolmanagementsystem.datamodel.Student;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class StudentTabController implements Initializable {
	private final String[] searchOption = { "StudentID", "Name", "Surname" };
	private final ObservableList<Student> studentList = FXCollections.observableArrayList();
	private final FilteredList<Student> filteredList = new FilteredList<>(studentList, b -> true);
	private final SortedList<Student> studentSortedList = new SortedList<>(filteredList);
	private List<String> moduleList = new ArrayList<>();
	@FXML
	private TableView<Student> studentTable;
	@FXML
	private TableColumn<Student, Integer> studentID;
	@FXML
	private TableColumn<Student, String> name;
	@FXML
	private TableColumn<Student, String> surname;
	@FXML
	private TextField searchStudent;
	@FXML
	private ChoiceBox<String> filterStudent;
	@FXML
	private ChoiceBox<String> moduleOptions;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		moduleOptions.getItems().addAll(palceholder);
		filterStudent.getItems().addAll(searchOption);
		studentID.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
		name.setCellValueFactory(new PropertyValueFactory<>("Name"));
		surname.setCellValueFactory(new PropertyValueFactory<>("Surname"));

		try {
			DBconnect dbConnect = new DBconnect();
			Connection connectDB = dbConnect.getConnection();
			String query = "SELECT* FROM student";
			Statement statement = connectDB.createStatement();
			ResultSet queryOut = statement.executeQuery(query);

			while (queryOut.next()) {
				Integer studentid = queryOut.getInt("studentID");
				String name = queryOut.getString("name");
				String surname = queryOut.getString("surname");
				Integer age = queryOut.getInt("age");
				LocalDate date = LocalDate.parse(queryOut.getString("DoB"));
				String gender = queryOut.getString("gender");
				studentList.add(new Student(studentid, name, surname, gender, age, date));
			}
			studentTable.setItems(studentList);
			queryOut.close();
			statement.close();
			connectDB.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		searchStudent.textProperty()
				.addListener((observable, oldValue, newValue) -> filteredList.setPredicate(Student -> {
					if (newValue.isBlank() || newValue.isEmpty()) {
						return true;
					}
					String searchWord = newValue.toLowerCase();
					if (!filterStudent.getSelectionModel().isEmpty()) {
						if (filterStudent.getValue().equals("StudentID")) {
							return Student.studentIDProperty().toString().contains(searchWord);
						} else if (filterStudent.getValue().equals("Name")) {
							return Student.getName().toLowerCase().contains(searchWord);
						} else if (filterStudent.getValue().equals("Surname")) {
							return Student.getSurname().toLowerCase().contains(searchWord);
						}
					} else if (Student.studentIDProperty().toString().contains(searchWord)) {
						return true;
					} else if (Student.getName().toLowerCase().contains(searchWord)) {
						return true;
					} else
						return Student.getSurname().toLowerCase().contains(searchWord);
					return false;
				}));
		studentSortedList.comparatorProperty().bind(studentTable.comparatorProperty());
		studentTable.setItems(studentSortedList);

		studentTable.setOnMouseClicked(e -> {
			Student selectedRow = studentTable.getSelectionModel().selectedItemProperty().getValue();
			int studentID = selectedRow.getStudentID();
			System.out.println(studentID);
			try {
				getModule(studentID);
			} catch (SQLException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		});

	}

//	public void addModule() throws SQLException, ClassNotFoundException {
//		Student selectedRow = studentTable.getSelectionModel().selectedItemProperty().getValue();
//		int studentID = selectedRow.getStudentID();
//		int student_ID = 0;
//		int courseID = 0;
//		DBconnect dbConnect = new DBconnect();
//		Connection connection = dbConnect.getConnection();
//		String query = "SELECT student_ID, courseID FROM student WHERE studentID = ?";
//		PreparedStatement preparedStatement = connection.prepareStatement(query);
//		preparedStatement.setInt(1, studentID);
//		ResultSet resultSet = preparedStatement.executeQuery();
//
//		if (resultSet.next()) {
//			student_ID = resultSet.getInt("student_ID");
//			courseID = resultSet.getInt("courseID");
//		}
//
//		String query1 = "SELECT  moduleID, moduleCode, moduleName from module where courseID = ?";
//		PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
//		preparedStatement1.setInt(1, courseID);
//		ResultSet resultSet1 = preparedStatement1.executeQuery();
//
//		while (resultSet1.next()){
//			moduleList.add(new Module(resultSet1.getInt("moduleID"), resultSet1.getString("moduleCode"), resultSet1.getString("moduleName")));
//		}
//		moduleOptions.getItems().addAll(String.valueOf(moduleList));
//	}

	public void getModule(int studentID) throws SQLException, ClassNotFoundException {
		int courseID = 0;
		DBconnect dbConnect = new DBconnect();
		Connection connection = dbConnect.getConnection();
		String query = "SELECT courseID FROM student WHERE studentID = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, studentID);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			courseID = resultSet.getInt("courseID");
		}
		String query1 = "SELECT  moduleID, moduleCode, moduleName from module where courseID = ?";
		PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
		preparedStatement1.setInt(1, courseID);
		ResultSet resultSet1 = preparedStatement1.executeQuery();

		while (resultSet1.next()) {
			moduleList.add(resultSet1.getInt("moduleID")+" "+resultSet1.getString("moduleCode")+" "+resultSet1.getString("moduleName"));
		}
		moduleOptions.getItems().addAll(String.valueOf(moduleList));
	}

	public void reset() {
		filterStudent.getItems().clear();
		filterStudent.getItems().addAll(searchOption);
	}

	public void goBack(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(
				Objects.requireNonNull(getClass().getResource("/com/example/schoolmanagementsystem/Dashboard.fxml")));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
