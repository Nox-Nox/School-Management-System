package com.example.schoolmanagementsystem;

import com.example.schoolmanagementsystem.datamodel.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        DBconnect dbConnect = new DBconnect();
        Connection connectDB = dbConnect.getConnection();
        String query = "SELECT* FROM student";
        Statement statement = connectDB.createStatement();
        ResultSet queryOut = statement.executeQuery(query);

        while (queryOut.next()){
            Integer studentid = queryOut.getInt("studentID");
            String name = queryOut.getString("name");
            String surname = queryOut.getString("surname");
            Integer age = queryOut.getInt("age");
            LocalDate date = LocalDate.parse(queryOut.getString("DoB"));
            String gender = queryOut.getString("gender");
            System.out.println(studentid+" "+name+" "+surname+" "+age+" "+date+" "+gender);
            studentList.add(new Student(studentid, name, surname, gender, age, date));
        }
        for (Student c : studentList){
			System.out.println(c.getStudentID());
		}
    }
}
