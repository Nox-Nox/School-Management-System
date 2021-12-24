package com.example.schoolmanagementsystem.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.schoolmanagementsystem.DBconnect;

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
	private Stage stage;
	private Scene scene;
	private Parent root;

	public void logIn(ActionEvent e) throws SQLException, ClassNotFoundException, IOException {
		String username = userName.getText();
		String pass = passWord.getText();

		DBconnect dbConnect = new DBconnect();
		Connection connectDB = dbConnect.getConnection();
		String query = "SELECT username, password FROM admin";
		Statement statement = connectDB.createStatement();
		ResultSet queryOut = statement.executeQuery(query);
		boolean x = false;

		while (queryOut.next()) {
			if (queryOut.getString("username").equals(username) && queryOut.getString("password").equals(pass)) {
				System.out.println("logged in");
				System.out.println(queryOut.getString("username") + " " + queryOut.getString("password"));
				x = true;
				break;
			}
		}
		if (x == false) {
			System.out.println("wrong");
		} else {
			root = FXMLLoader.load(getClass().getResource("/com/example/schoolmanagementsystem/Dashboard.fxml"));
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		}
		connectDB.close();
	}
}