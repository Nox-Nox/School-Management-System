package com.example.schoolmanagementsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.schoolmanagementsystem.DBconnect;
import com.example.schoolmanagementsystem.datamodel.Module;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class StudentTabController implements Initializable {
	private final String[] searchOption = { "StudentID", "Name", "Surname" };
	private final ObservableList<Student> studentList = FXCollections.observableArrayList();
	private final ObservableList<Module> moduleListView = FXCollections.observableArrayList();
	private final FilteredList<Student> filteredList = new FilteredList<>(studentList, b -> true);
	private final SortedList<Student> studentSortedList = new SortedList<>(filteredList);
	private final ObservableList<Module> moduleList = FXCollections.observableArrayList();
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
	private ChoiceBox<Module> moduleOptions;
	@FXML
	private ListView<Module> studentModuleView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		filterStudent.getItems().addAll(searchOption);
		studentID.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
		name.setCellValueFactory(new PropertyValueFactory<>("Name"));
		surname.setCellValueFactory(new PropertyValueFactory<>("Surname"));

		try {
			String query = "SELECT* FROM student";
			Statement statement = connectToDB().createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Integer studentid = resultSet.getInt("studentID");
				String name = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				Integer age = resultSet.getInt("age");
				LocalDate date = LocalDate.parse(resultSet.getString("DoB"));
				String gender = resultSet.getString("gender");
				studentList.add(new Student(studentid, name, surname, gender, age, date));
			}
			studentTable.setItems(studentList);
			resultSet.close();
			statement.close();
			connectToDB().close();

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
			try {
				getModuleByStudentID(studentID);
				moduleListView.clear();
				studentModuleView.getItems().clear();
				getModuleByStudentID(studentID);
			} catch (SQLException | ClassNotFoundException ec) {
				ec.printStackTrace();
			}
			System.out.println(studentID);
			try {
				getModule(studentID);
				moduleList.clear();
				moduleOptions.getItems().clear();
				getModule(studentID);
			} catch (SQLException | ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		});
	}

	public void addModule() throws SQLException, ClassNotFoundException {
		int studentID = studentTable.getSelectionModel().selectedItemProperty().getValue().getStudentID();
		int modueleID = moduleOptions.getValue().getModuleID();
		int student_ID = 0;
		String query = "SELECT student_ID FROM student WHERE studentID = ?";
		PreparedStatement preparedStatement = connectToDB().prepareStatement(query);
		preparedStatement.setInt(1, studentID);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			student_ID = resultSet.getInt("student_ID");
		}
		String query1 = "INSERT INTO student_module_junction (student_ID, moduleID)" + "VALUES(?, ?)";
		PreparedStatement preparedStatement1 = connectToDB().prepareStatement(query1);
		preparedStatement1.setInt(1, student_ID);
		preparedStatement1.setInt(2, modueleID);
		preparedStatement1.execute();
		connectToDB().close();
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText("Module added");
		alert.showAndWait();
	}

	public void removeModule(){

	}

	private void getModule(int studentID) throws SQLException, ClassNotFoundException {
		int courseID = 0;
		String query = "SELECT courseID FROM student WHERE studentID = ?";
		PreparedStatement preparedStatement = connectToDB().prepareStatement(query);
		preparedStatement.setInt(1, studentID);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			courseID = resultSet.getInt("courseID");
		}
		String query1 = "SELECT  moduleID, moduleCode, moduleName from module where courseID = ?";
		PreparedStatement preparedStatement1 = connectToDB().prepareStatement(query1);
		preparedStatement1.setInt(1, courseID);
		ResultSet resultSet1 = preparedStatement1.executeQuery();

		while (resultSet1.next()) {
			moduleList.add(new Module(resultSet1.getInt("moduleID"), resultSet1.getString("moduleCode"),
					resultSet1.getString("moduleName")));
		}
		moduleOptions.setItems(moduleList);
		connectToDB().close();
	}

	private void getModuleByStudentID(int studentID) throws SQLException, ClassNotFoundException {
		String query = "SELECT module.moduleID, module.moduleCode, module.moduleName FROM module JOIN student_module_junction ON (module.moduleID=student_module_junction.moduleID) JOIN student ON (student.student_ID=student_module_junction.student_ID) WHERE student.studentID = ?";
		PreparedStatement preparedStatement = connectToDB().prepareStatement(query);
		preparedStatement.setInt(1, studentID);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			moduleListView.add(new Module(resultSet.getInt("moduleID"), resultSet.getString("moduleCode"),
					resultSet.getString("moduleName")));
		}
		studentModuleView.getItems().addAll(moduleListView);
		connectToDB().close();
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

	private Connection connectToDB() throws SQLException, ClassNotFoundException {
		DBconnect dBconnect = new DBconnect();
		Connection connection = dBconnect.getConnection();
		return connection;
	}
}
