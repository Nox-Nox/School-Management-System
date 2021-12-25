package com.example.schoolmanagementsystem.datamodel;

import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {
	SimpleIntegerProperty student_ID;
	SimpleIntegerProperty studentID;
	SimpleStringProperty name;
	SimpleStringProperty surname;
	SimpleStringProperty gender;
	SimpleIntegerProperty age;
	SimpleObjectProperty DoB;

	public Student(Integer student_ID, Integer studentID, String name, String surname, String gender, Integer age,
			LocalDate DoB) {
		this.student_ID = new SimpleIntegerProperty(student_ID);
		this.studentID = new SimpleIntegerProperty(studentID);
		this.name = new SimpleStringProperty(name);
		this.surname = new SimpleStringProperty(surname);
		this.gender = new SimpleStringProperty(gender);
		this.age = new SimpleIntegerProperty(age);
		this.DoB = new SimpleObjectProperty(DoB);
	}

	public int getStudent_ID() {
		return student_ID.get();
	}

	public void setStudent_ID(int student_ID) {
		this.student_ID.set(student_ID);
	}

	public SimpleIntegerProperty student_IDProperty() {
		return student_ID;
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

	public void setDoB(Object doB) {
		this.DoB.set(doB);
	}

	public SimpleObjectProperty doBProperty() {
		return DoB;
	}

	@Override
	public String toString() {
		return studentID.getValue() + "  " + name.getValue() + "  " + surname.getValue();
	}
}