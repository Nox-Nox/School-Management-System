package com.example.schoolmanagementsystem;

import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Professor {
	SimpleIntegerProperty professorID;
	SimpleStringProperty name;
	SimpleStringProperty surname;
	SimpleIntegerProperty age;
	SimpleStringProperty gender;
	SimpleObjectProperty DoB;

	public Professor(Integer professorID, String name, String surname, String gender, Integer age, LocalDate DoB) {
		this.professorID = new SimpleIntegerProperty(professorID);
		this.name = new SimpleStringProperty(name);
		this.surname = new SimpleStringProperty(surname);
		this.age = new SimpleIntegerProperty(age);
		this.gender = new SimpleStringProperty(gender);
		this.DoB = new SimpleObjectProperty(DoB);
	}

	public int getProfessorID() {
		return professorID.get();
	}

	public SimpleIntegerProperty professorIDProperty() {
		return professorID;
	}

	public void setProfessorID(int professorID) {
		this.professorID.set(professorID);
	}

	public String getName() {
		return name.get();
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getSurname() {
		return surname.get();
	}

	public SimpleStringProperty surnameProperty() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname.set(surname);
	}

	public int getAge() {
		return age.get();
	}

	public SimpleIntegerProperty ageProperty() {
		return age;
	}

	public void setAge(int age) {
		this.age.set(age);
	}

	public String getGender() {
		return gender.get();
	}

	public SimpleStringProperty genderProperty() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender.set(gender);
	}

	public Object getDoB() {
		return DoB.get();
	}

	public SimpleObjectProperty doBProperty() {
		return DoB;
	}

	public void setDoB(Object doB) {
		this.DoB.set(doB);
	}
}
