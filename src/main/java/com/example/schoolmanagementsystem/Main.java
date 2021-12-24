package com.example.schoolmanagementsystem;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));

		Scene scene = new Scene(root);
		stage.setTitle("Hello!");
		stage.setScene(scene);
		stage.show();
	}
}