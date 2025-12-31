package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DashboardTController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private TextField roomIdField;

    private String teacherName;

    public void setTeacherName(String name) {
        this.teacherName = name;
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + name);
        }
    }

    private String getRoomId() {
        return roomIdField != null ? roomIdField.getText().trim() : "";
    }

    @FXML
    private void onCreateQuestion() {
        String roomId = getRoomId();
    }

    @FXML
    private void onUpdateQuestion() {
        String roomId = getRoomId();
    }

    @FXML
    private void onDeleteQuestion() {
        String roomId = getRoomId();
    }

    @FXML
    private void onPreviewQuestion() {
        String roomId = getRoomId();
    }

    @FXML
    private void onViewAllResults() {
        String roomId = getRoomId();
    }
}
