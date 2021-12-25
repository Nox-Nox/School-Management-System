package com.example.universitymanagementsystem.datamodel;

import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class AcademicYear {
	SimpleIntegerProperty ttableID;
	SimpleObjectProperty startDate;
	SimpleObjectProperty endDate;
	SimpleStringProperty course;

	public AcademicYear(int ttableID, LocalDate startDate, LocalDate endDate, String course) {
		this.ttableID = new SimpleIntegerProperty(ttableID);
		this.startDate = new SimpleObjectProperty(startDate);
		this.endDate = new SimpleObjectProperty(endDate);
		this.course = new SimpleStringProperty(course);
	}

	public int getTtableID() {
		return ttableID.get();
	}

	public void setTtableID(int ttableID) {
		this.ttableID.set(ttableID);
	}

	public SimpleIntegerProperty ttableIDProperty() {
		return ttableID;
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

	@Override
	public String toString() {
		return startDate.getValue() + "/" + endDate.getValue();
	}
}
