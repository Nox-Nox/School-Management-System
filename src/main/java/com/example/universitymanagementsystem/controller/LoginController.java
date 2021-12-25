package com.example.universitymanagementsystem.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import com.example.universitymanagementsystem.DBconnect;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private PasswordField passWord;
	@FXML
	private TextField userName;

	public void logIn(ActionEvent e) throws SQLException, ClassNotFoundException, IOException {
		String username = userName.getText();
		String pass = passWord.getText();

		String query = "SELECT username, password FROM admin";
		Statement statement = connectToDB().createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		boolean x = false;
		while (resultSet.next()) {
			if (resultSet.getString("username").equals(username) && resultSet.getString("password").equals(pass)) {
				x = true;
				break;
			}
		}
		if (!x) {
			System.out.println("wrong");
		} else {
			Parent root = FXMLLoader.load(Objects
					.requireNonNull(getClass().getResource("/com/example/universitymanagementsystem/Dashboard.fxml")));
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		connectToDB().close();
	}

	private Connection connectToDB() throws SQLException, ClassNotFoundException {
		DBconnect dBconnect = new DBconnect();
		return dBconnect.getConnection();
	}
}