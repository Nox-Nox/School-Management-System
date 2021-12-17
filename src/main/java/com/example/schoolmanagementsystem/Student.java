package com.example.schoolmanagementsystem;

import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {
	SimpleIntegerProperty studentID;
	SimpleStringProperty name;
	SimpleStringProperty surname;
	SimpleStringProperty gender;
	SimpleIntegerProperty age;
	SimpleObjectProperty DoB;

	public Student(Integer studentID, String name, String surname, String gender, Integer age, LocalDate DoB) {

		this.studentID = new SimpleIntegerProperty(studentID);
		this.name = new SimpleStringProperty(name);
		this.surname = new SimpleStringProperty(surname);
		this.gender = new SimpleStringProperty(gender);
		this.age = new SimpleIntegerProperty(age);
		this.DoB = new SimpleObjectProperty(DoB);

	}

	public int getStudentID() {
		return studentID.get();
	}

	public void setStudentID(int studentID) {
		this.studentID.set(studentID);
	}

	public SimpleIntegerProperty studentIDProperty() {
		return studentID;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public String getSurname() {
		return surname.get();
	}

	public void setSurname(String surname) {
		this.surname.set(surname);
	}

	public SimpleStringProperty surnameProperty() {
		return surname;
	}

	public String getGender() {
		return gender.get();
	}

	public void setGender(String gender) {
		this.gender.set(gender);
	}

	public SimpleStringProperty genderProperty() {
		return gender;
	}

	public int getAge() {
		return age.get();
	}

	public void setAge(int age) {
		this.age.set(age);
	}

	public SimpleIntegerProperty ageProperty() {
		return age;
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
	@Override
	public String toString() {
		return studentID + " " + name + " " + surname + " " + gender + " " + age+ " " +DoB;
	}
}
