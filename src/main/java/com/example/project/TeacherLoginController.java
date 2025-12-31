package com.example.project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TeacherLoginController {

    @FXML
    private VBox rootBox;

    @FXML
    private TextField nameField;

    @FXML
    private void initialize() {
        Platform.runLater(() -> rootBox.requestFocus());
    }

    @FXML
    private void onLoginClicked(ActionEvent event) {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            nameField.clear();
            nameField.setPromptText("Please enter a name");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApp.class.getResource("dashboard_t.fxml")
            );
            Scene scene = new Scene(loader.load());

            DashboardTController controller = loader.getController();
            controller.setTeacherName(name);

            Stage stage = MainApp.getPrimaryStage();
            stage.setScene(scene);
            stage.setTitle("Teacher Dashboard");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
