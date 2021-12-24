package com.example.schoolmanagementsystem.datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Module {
	SimpleIntegerProperty moduleID;
	SimpleStringProperty moduleCode;
	SimpleStringProperty moduleName;

	public Module(int moduleID, String moduleCode, String moduleName) {
		this.moduleID = new SimpleIntegerProperty(moduleID);
		this.moduleCode = new SimpleStringProperty(moduleCode);
		this.moduleName = new SimpleStringProperty(moduleName);
	}

	public int getModuleID() {
		return moduleID.get();
	}

	public void setModuleID(int moduleID) {
		this.moduleID.set(moduleID);
	}

	public SimpleIntegerProperty moduleIDProperty() {
		return moduleID;
	}

	public String getModuleCode() {
		return moduleCode.get();
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode.set(moduleCode);
	}

	public SimpleStringProperty moduleCodeProperty() {
		return moduleCode;
	}

	public String getModuleName() {
		return moduleName.get();
	}

	public void setModuleName(String moduleName) {
		this.moduleName.set(moduleName);
	}

	public SimpleStringProperty moduleNameProperty() {
		return moduleName;
	}

	@Override
	public String toString() {
		return moduleCode.getValue() + " " + moduleName.getValue();
	}
}
