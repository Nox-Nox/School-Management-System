package com.example.schoolmanagementsystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;

public class Course {
	SimpleStringProperty courseCode;
	SimpleStringProperty courseName;
	SimpleStringProperty module;
	SimpleStringProperty type; // it is an exam, lecture or coursework
	SimpleStringProperty classroom;
	SimpleDateFormat date;
	SimpleIntegerProperty grade;

	public Course(String courseCode, String courseName, String module, String type, String classroom, String date, Integer grade) {
		this.courseCode = new SimpleStringProperty(courseCode);
		this.courseName = new SimpleStringProperty(courseName);
		this.module = new SimpleStringProperty(module);
		this.type = new SimpleStringProperty(type);
		this.classroom = new SimpleStringProperty(classroom);
		this.date = new SimpleDateFormat(date);
		this.grade = new SimpleIntegerProperty(grade);
	}

	public String getCourseCode() {
		return courseCode.get();
	}

	public SimpleStringProperty courseCodeProperty() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode.set(courseCode);
	}

	public String getCourseName() {
		return courseName.get();
	}

	public void setCourseName(String courseName) {
		this.courseName.set(courseName);
	}

	public SimpleStringProperty courseNameProperty() {
		return courseName;
	}

	public String getModule() {
		return module.get();
	}

	public void setModule(String module) {
		this.module.set(module);
	}

	public SimpleStringProperty moduleProperty() {
		return module;
	}

	public String getType() {
		return type.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public SimpleStringProperty typeProperty() {
		return type;
	}

	public String getClassroom() {
		return classroom.get();
	}

	public void setClassroom(String classroom) {
		this.classroom.set(classroom);
	}

	public SimpleStringProperty classroomProperty() {
		return classroom;
	}

	public SimpleDateFormat getDate() {
		return date;
	}

	public void setDate(SimpleDateFormat date) {
		this.date = date;
	}

	public int getGrade() {
		return grade.get();
	}

	public void setGrade(int grade) {
		this.grade.set(grade);
	}

	public SimpleIntegerProperty gradeProperty() {
		return grade;
	}
}
