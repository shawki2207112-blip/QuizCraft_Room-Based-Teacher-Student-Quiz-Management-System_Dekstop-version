package com.example.project;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class CreateQuestionController {

    @FXML
    private TextArea questionArea;

    @FXML
    private VBox optionsBox;

    @FXML
    private ComboBox<Integer> correctIndexCombo;

    @FXML
    private Label imagePathLabel;

    @FXML
    private ImageView previewImageView;

    private final List<TextField> optionFields = new ArrayList<>();

    private String roomId;
    private String imagePath = "";
    private final QuestionDatabase db = new QuestionDatabase();

    private Runnable onSavedCallback;
    private QuestionModel editingQuestion;

    @FXML
    private void initialize() {
        addOptionField();
        addOptionField();
        updateCorrectIndexCombo();
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setOnSavedCallback(Runnable callback) {
        this.onSavedCallback = callback;
    }

    public void setQuestionToEdit(QuestionModel question, Runnable callback) {
        this.editingQuestion = question;
        this.onSavedCallback = callback;

        questionArea.setText(question.getQuestion());
        imagePath = question.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            imagePathLabel.setText(file.getName());
            try {
                String uri = file.toURI().toURL().toString();
                previewImageView.setImage(new Image(uri, true));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        optionsBox.getChildren().clear();
        optionFields.clear();
        for (String opt : question.getOptions()) {
            TextField tf = new TextField(opt);
            optionFields.add(tf);
            optionsBox.getChildren().add(tf);
        }
        if (optionFields.size() < 2) {
            addOptionField();
        }
        updateCorrectIndexCombo();

        int index = question.getOptions().indexOf(question.getCorrectAnswer());
        if (index >= 0) {
            correctIndexCombo.setValue(index + 1);
        }
    }

    @FXML
    private void onChooseImageClicked(ActionEvent event) {
        Stage stage = (Stage) questionArea.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Question Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            imagePath = file.getAbsolutePath();
            imagePathLabel.setText(file.getName());

            try {
                String uri = file.toURI().toURL().toString();
                Image img = new Image(uri, true);
                previewImageView.setImage(img);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onAddOptionClicked(ActionEvent event) {
        addOptionField();
        updateCorrectIndexCombo();
    }

    @FXML
    private void onRemoveOptionClicked(ActionEvent event) {
        if (optionFields.size() > 2) {
            TextField last = optionFields.remove(optionFields.size() - 1);
            optionsBox.getChildren().remove(last);
            updateCorrectIndexCombo();
        }
    }

    @FXML
    private void onSaveClicked(ActionEvent event) {
        String questionText = questionArea.getText().trim();
        List<String> options = new ArrayList<>();

        for (TextField tf : optionFields) {
            String txt = tf.getText().trim();
            if (!txt.isEmpty()) {
                options.add(txt);
            }
        }

        Integer correctIndex = correctIndexCombo.getValue();

        if (roomId == null || roomId.isEmpty()) return;
        if (questionText.isEmpty()) return;
        if (options.size() < 2) return;
        if (correctIndex == null ||
                correctIndex < 1 ||
                correctIndex > options.size()) return;

        String correctAnswer = options.get(correctIndex - 1);

        if (editingQuestion == null) {
            db.insertQuestion(roomId, questionText, imagePath, options, correctAnswer);
        } else {
            db.updateQuestion(editingQuestion.getId(), questionText, imagePath, options, correctAnswer);
        }

        if (onSavedCallback != null) {
            onSavedCallback.run();
        }

        Stage stage = (Stage) questionArea.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCancelClicked(ActionEvent event) {
        Stage stage = (Stage) questionArea.getScene().getWindow();
        stage.close();
    }

    private void addOptionField() {
        TextField tf = new TextField();
        tf.setPromptText("Option " + (optionFields.size() + 1));
        optionFields.add(tf);
        optionsBox.getChildren().add(tf);
    }

    private void updateCorrectIndexCombo() {
        List<Integer> items = new ArrayList<>();
        for (int i = 1; i <= optionFields.size(); i++) {
            items.add(i);
        }
        correctIndexCombo.setItems(FXCollections.observableArrayList(items));
        if (!items.isEmpty() && correctIndexCombo.getValue() == null) {
            correctIndexCombo.setValue(1);
        }
    }
}
