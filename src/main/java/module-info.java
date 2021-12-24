module com.example.schoolmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    opens com.example.schoolmanagementsystem to javafx.fxml;
    exports com.example.schoolmanagementsystem;
    exports com.example.schoolmanagementsystem.datamodel;
    opens com.example.schoolmanagementsystem.datamodel to javafx.fxml;
    exports com.example.schoolmanagementsystem.controller;
    opens com.example.schoolmanagementsystem.controller to javafx.fxml;
}