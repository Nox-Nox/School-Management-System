package com.example.schoolmanagementsystem;

import java.time.LocalDate;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class TimeTable {
	SimpleStringProperty academicYear;
	SimpleObjectProperty startDate;
	SimpleObjectProperty endDate;
	SimpleStringProperty course;
	SimpleListProperty<SimpleStringProperty> students;

	public TimeTable(String academicYear, LocalDate startDate, LocalDate endDate, String course,
			ObservableList<SimpleStringProperty> students) {
		this.academicYear = new SimpleStringProperty(academicYear);
		this.startDate = new SimpleObjectProperty(startDate);
		this.endDate = new SimpleObjectProperty(endDate);
		this.course = new SimpleStringProperty(course);
		this.students = new SimpleListProperty<>(students);
	}

	public String getAcademicYear() {
		return academicYear.get();
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear.set(academicYear);
	}

	public SimpleStringProperty academicYearProperty() {
		return academicYear;
	}

	public Object getStartDate() {
		return startDate.get();
	}

	public void setStartDate(Object startDate) {
		this.startDate.set(startDate);
	}

	public SimpleObjectProperty startDateProperty() {
		return startDate;
	}

	public Object getEndDate() {
		return endDate.get();
	}

	public void setEndDate(Object endDate) {
		this.endDate.set(endDate);
	}

	public SimpleObjectProperty endDateProperty() {
		return endDate;
	}

	public String getCourse() {
		return course.get();
	}

	public void setCourse(String course) {
		this.course.set(course);
	}

	public SimpleStringProperty courseProperty() {
		return course;
	}

	public ObservableList<SimpleStringProperty> getStudents() {
		return students.get();
	}

	public void setStudents(ObservableList<SimpleStringProperty> students) {
		this.students.set(students);
	}

	public SimpleListProperty<SimpleStringProperty> studentsProperty() {
		return students;
	}
}
