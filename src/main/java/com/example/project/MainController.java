package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private void onTeacherClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("teacher_login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = MainApp.getPrimaryStage();
            stage.setScene(scene);
            stage.setTitle("Teacher Login");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onStudentClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("student_dashboard.fxml"));
            Scene scene= new Scene(loader.load());
            Stage stage = MainApp.getPrimaryStage();
            stage.setScene(scene);
            stage.setTitle("Student Login");
            stage.centerOnScreen();
        }
        catch (Exception e) {e.printStackTrace();}
    }
}
