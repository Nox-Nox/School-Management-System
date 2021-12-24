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
import com.example.schoolmanagementsystem.datamodel.Professor;

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

public class ProfessorTabController implements Initializable {
	private final String[] searchOption = { "ProfessorID", "Name", "Surname" };
	private final ObservableList<Professor> professorList = FXCollections.observableArrayList();
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		filterProfessor.getItems().addAll(searchOption);
		professorID.setCellValueFactory(new PropertyValueFactory<>("ProfessorID"));
		name.setCellValueFactory(new PropertyValueFactory<>("Name"));
		surname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
		try {

			String query = "SELECT* FROM professor";
			Statement statement = connectToDB().createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Integer professorid = resultSet.getInt("professorID");
				String name = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				String gender = resultSet.getString("gender");
				Integer age = resultSet.getInt("age");
				LocalDate date = LocalDate.parse(resultSet.getString("DoB"));

				professorList.add(new Professor(professorid, name, surname, gender, age, date));
			}
			professorTable.setItems(professorList);
			resultSet.close();
			statement.close();
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
	}

	public void reset() {
		filterProfessor.getItems().clear();
		filterProfessor.getItems().addAll(searchOption);
	}

	public void goBack(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/schoolmanagementsystem/Dashboard.fxml")));
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
