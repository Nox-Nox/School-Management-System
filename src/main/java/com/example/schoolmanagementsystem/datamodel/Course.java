package com.example.schoolmanagementsystem.datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Course {
	SimpleIntegerProperty courseID;
	SimpleStringProperty courseCode;
	SimpleStringProperty courseName;


	public Course(Integer courseID, String courseCode, String courseName) {
		this.courseID = new SimpleIntegerProperty(courseID);
		this.courseCode = new SimpleStringProperty(courseCode);
		this.courseName = new SimpleStringProperty(courseName);

	}

	public int getCourseID() {
		return courseID.get();
	}

	public SimpleIntegerProperty courseIDProperty() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID.set(courseID);
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

	public SimpleStringProperty courseNameProperty() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName.set(courseName);
	}

	@Override
	public String toString() {
		return courseCode.getValue() + " " + courseName.getValue();
	}
}
