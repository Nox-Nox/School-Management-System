package com.example.universitymanagementsystem.datamodel;

import javafx.beans.property.SimpleStringProperty;

public class Admin {
	SimpleStringProperty username;
	SimpleStringProperty password;

	public Admin(String username, String password) {
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
	}

	public String getUsername() {
		return username.get();
	}

	public void setUsername(String username) {
		this.username.set(username);
	}

	public SimpleStringProperty usernameProperty() {
		return username;
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public SimpleStringProperty passwordProperty() {
		return password;
	}
}
