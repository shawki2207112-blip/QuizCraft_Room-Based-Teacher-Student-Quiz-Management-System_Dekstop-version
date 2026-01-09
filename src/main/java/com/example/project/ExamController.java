package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;

public class ExamController {

    @FXML
    private Label questionLabel;

    @FXML
    private ImageView questionImageView;

    @FXML
    private VBox imageContainer;

    @FXML
    private VBox optionsContainer;

    @FXML
    private Button nextButton;

    private final ToggleGroup optionsGroup = new ToggleGroup();
    private final QuestionDatabase db = new QuestionDatabase();
    private List<QuestionModel> questions;
    private int index = 0;
    private int score = 0;
    private String roomId;
    private String studentName;

    public void startExam(String roomId, String studentName) {
        this.roomId = roomId;
        this.studentName = studentName;

        questions = db.getQuestionsByRoomId(roomId);
        if (questions == null || questions.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION,
                    "No questions for this room").showAndWait();
            closeWindow();
            return;
        }

        Collections.shuffle(questions);
        loadQuestion();
        nextButton.setOnAction(e -> checkAnswerAndNext());
    }

    private void loadQuestion() {
        if (index >= questions.size()) {
            ResultDatabase rdb = new ResultDatabase();
            rdb.insertResult(roomId, studentName, score, questions.size());
            new Alert(Alert.AlertType.INFORMATION,
                    "Exam finished! Score: " + score + "/" + questions.size())
                    .showAndWait();
            closeWindow();
            return;
        }

        QuestionModel q = questions.get(index);
        questionLabel.setText(q.getQuestion());

        String path = q.getImagePath();
        if (path != null && !path.isEmpty()) {
            File f = new File(path);
            if (f.exists()) {
                try {
                    String uri = f.toURI().toURL().toString();
                    questionImageView.setImage(new Image(uri, true));
                    imageContainer.setManaged(true);
                    imageContainer.setVisible(true);
                } catch (MalformedURLException e) {
                    imageContainer.setManaged(false);
                    imageContainer.setVisible(false);
                }
            } else {
                imageContainer.setManaged(false);
                imageContainer.setVisible(false);
            }
        } else {
            imageContainer.setManaged(false);
            imageContainer.setVisible(false);
        }

        optionsContainer.getChildren().clear();
        optionsGroup.getToggles().clear();

        for (String opt : q.getOptions()) {
            RadioButton rb = new RadioButton(opt);
            rb.setToggleGroup(optionsGroup);
            rb.setWrapText(true);
            optionsContainer.getChildren().add(rb);
        }

        nextButton.setText(index == questions.size() - 1 ? "Finish" : "Next");
    }

    private void checkAnswerAndNext() {
        Toggle selected = optionsGroup.getSelectedToggle();
        if (selected != null) {
            String chosen = ((RadioButton) selected).getText();
            if (chosen.equals(questions.get(index).getCorrectAnswer())) {
                score++;
            }
        }
        index++;
        loadQuestion();
    }

    private void closeWindow() {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        stage.close();
    }
}
