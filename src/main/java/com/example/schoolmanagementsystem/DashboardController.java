package com.example.schoolmanagementsystem;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class DashboardController implements Initializable {

	@FXML
	private AnchorPane sceneDashboard;

	public void switchToStudent(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("StudentTab.fxml"));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();


	}

	public void switchToProfessor(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ProfessorTab.fxml"));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchToSchedule(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AcademicYearControllerTab.fxml"));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void addStudent() throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addStudent.fxml")));
		stage.setTitle("Add new student");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}

	public void addProfessor() throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addProfessor.fxml")));
		stage.setTitle("Add new professor");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}

	public void addCourse() throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addCourse.fxml")));
		stage.setTitle("Add new course");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}

	public void addModule() throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addModule.fxml")));
		stage.setTitle("Add new module");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}

	public void addAcademicYear() throws IOException {
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addAcademicYear.fxml")));
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
		Platform.runLater( () -> sceneDashboard.requestFocus() );
	}
}
