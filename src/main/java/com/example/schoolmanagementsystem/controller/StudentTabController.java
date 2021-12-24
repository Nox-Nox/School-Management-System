package com.example.schoolmanagementsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
	}

	public void reset() {
		filterStudent.getItems().clear();
		filterStudent.getItems().addAll(searchOption);
	}

	public void goBack(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/schoolmanagementsystem/Dashboard.fxml")));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
