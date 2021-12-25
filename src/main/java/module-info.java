module com.example.schoolmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    opens com.example.universitymanagementsystem to javafx.fxml;
    exports com.example.universitymanagementsystem;
    exports com.example.universitymanagementsystem.datamodel;
    opens com.example.universitymanagementsystem.datamodel to javafx.fxml;
    exports com.example.universitymanagementsystem.controller;
    opens com.example.universitymanagementsystem.controller to javafx.fxml;
}