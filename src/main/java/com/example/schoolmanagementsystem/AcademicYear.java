package com.example.schoolmanagementsystem;

import java.time.LocalDate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class AcademicYear {
	SimpleObjectProperty startDate;
	SimpleObjectProperty endDate;
	SimpleStringProperty course;

	public AcademicYear(LocalDate startDate, LocalDate endDate, String course) {
		this.startDate = new SimpleObjectProperty(startDate);
		this.endDate = new SimpleObjectProperty(endDate);
		this.course = new SimpleStringProperty(course);
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

}
