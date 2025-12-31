package com.example.project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardTController {

    @FXML
    private VBox rootVBox;

    @FXML
    private TextField roomIdField;

    @FXML
    private TextField nameField;

    @FXML
    private Button createQuestionButton;

    @FXML
    private void initialize() {
        Platform.runLater(() -> rootVBox.requestFocus());
    }

    public void setTeacherName(String teacherName) {
        if (nameField != null) {
            nameField.setText(teacherName);
        }
    }


    private String getRoomIdFromUI() {
        return roomIdField.getText() == null ? "" : roomIdField.getText().trim();
    }

    @FXML
    private void onStartExamClicked(ActionEvent event) {
        String roomId = getRoomIdFromUI();
        String teacherName = nameField.getText() == null ? "" : nameField.getText().trim();

        if (roomId.isEmpty()) {
            roomIdField.clear();
            roomIdField.setPromptText("Room id is required");
            return;
        }
        if (teacherName.isEmpty()) {
            nameField.clear();
            nameField.setPromptText("Name is required");
            return;
        }
    }

    @FXML
    private void onCreateQuestionClicked(ActionEvent event) {
        String roomId = getRoomIdFromUI();
        if (roomId.isEmpty()) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("create_question.fxml")
            );
            Scene scene = new Scene(loader.load());

            CreateQuestionController controller = loader.getController();
            controller.setRoomId(roomId);

            Stage owner = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene().getWindow();

            Stage stage = new Stage();
            stage.setTitle("Create Question - Room " + roomId);
            stage.setScene(scene);
            stage.initOwner(owner);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
