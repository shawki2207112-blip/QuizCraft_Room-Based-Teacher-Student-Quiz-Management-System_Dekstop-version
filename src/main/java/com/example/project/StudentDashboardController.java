package com.example.project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentDashboardController {

    @FXML
    private TextField roomIdField;
    @FXML
    private VBox rootVBox;
    @FXML
    private TextField nameField;

    @FXML
    private void initialize() {
        Platform.runLater(() -> rootVBox.requestFocus());
    }

    @FXML
    private void onStartExamClicked(ActionEvent event) {
        String roomId = roomIdField.getText().trim();
        String studentName = nameField.getText().trim();

        if (roomId.isEmpty()) {
            roomIdField.clear();
            roomIdField.setPromptText("Room id is required");
            return;
        }
        if (studentName.isEmpty()) {
            nameField.clear();
            nameField.setPromptText("Name is required");
            return;
        }


    }
}
