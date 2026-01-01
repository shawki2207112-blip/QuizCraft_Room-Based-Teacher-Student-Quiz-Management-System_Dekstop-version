package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class QuestionPreviewController {

    @FXML
    private Label questionLabel;
    @FXML
    private ImageView imageView;
    @FXML
    private ListView<String> optionsList;
    @FXML
    private Label answerLabel;

    public void setQuestion(QuestionModel question) {
        questionLabel.setText(question.getQuestion());
        optionsList.getItems().setAll(question.getOptions());
        answerLabel.setText("Correct answer: " + question.getCorrectAnswer());

        String path = question.getImagePath();
        if (path != null && !path.isEmpty()) {
            File file = new File(path);
            if (file.exists()) {
                try {
                    String uri = file.toURI().toURL().toString();
                    imageView.setImage(new Image(uri, true));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void onCloseClicked() {
        Stage stage = (Stage) questionLabel.getScene().getWindow();
        stage.close();
    }
}
