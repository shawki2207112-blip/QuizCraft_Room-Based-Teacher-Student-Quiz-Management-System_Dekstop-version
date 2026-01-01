package com.example.project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardTController {

    @FXML
    private VBox rootVBox;

    @FXML
    private TextField roomIdField;

    @FXML
    private Label welcomeLabel;

    @FXML
    private void initialize() {
        Platform.runLater(() -> rootVBox.requestFocus());
    }

    public void setTeacherName(String teacherName) {
        if (welcomeLabel != null && teacherName != null && !teacherName.isEmpty()) {
            welcomeLabel.setText("Welcome, " + teacherName);
        }
    }

    private String getRoomIdFromUI() {
        return roomIdField.getText() == null ? "" : roomIdField.getText().trim();
    }

    @FXML
    private void onCreateQuestionClicked(ActionEvent event) {
        String roomId = getRoomIdFromUI();
        if (roomId.isEmpty()) return;

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("create_question.fxml")
            );
            Scene scene = new Scene(loader.load());

            CreateQuestionController controller = loader.getController();
            controller.setRoomId(roomId);

            Stage stage = new Stage();
            stage.setTitle("Create Question - Room " + roomId);
            stage.setScene(scene);
            stage.initOwner(((Stage) ((javafx.scene.Node) event.getSource())
                    .getScene().getWindow()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onPreviewQuestionClicked(ActionEvent event) {
        openQuestionListWithMode(event, "PREVIEW");
    }

    @FXML
    private void onUpdateQuestionClicked(ActionEvent event) {
        openQuestionListWithMode(event, "UPDATE");
    }

    @FXML
    private void onDeleteQuestionClicked(ActionEvent event) {
        openQuestionListWithMode(event, "DELETE");
    }

    private void openQuestionListWithMode(ActionEvent event, String mode) {
        String roomId = getRoomIdFromUI();
        if (roomId.isEmpty()) return;

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("question_list.fxml")
            );
            Scene scene = new Scene(loader.load());

            QuestionListController controller = loader.getController();
            controller.setRoomId(roomId);
            controller.setMode(mode);

            Stage stage = new Stage();
            stage.setTitle("Questions - Room " + roomId + " (" + mode.toLowerCase() + ")");
            stage.setScene(scene);
            stage.initOwner(((Stage) ((javafx.scene.Node) event.getSource())
                    .getScene().getWindow()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
