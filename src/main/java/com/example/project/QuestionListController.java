package com.example.project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class QuestionListController {

    @FXML
    private Label roomLabel;
    @FXML
    private TableView<QuestionModel> questionTable;
    @FXML
    private TableColumn<QuestionModel, Number> idColumn;
    @FXML
    private TableColumn<QuestionModel, String> questionColumn;
    @FXML
    private TableColumn<QuestionModel, String> hasImageColumn;

    private final QuestionDatabase db = new QuestionDatabase();
    private final ObservableList<QuestionModel> questions = FXCollections.observableArrayList();
    private String roomId;
    private String mode = "PREVIEW";

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getId()));
        questionColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getQuestion()));
        hasImageColumn.setCellValueFactory(data -> {
            String img = data.getValue().getImagePath();
            return new SimpleStringProperty(
                    (img != null && !img.isEmpty()) ? "Yes" : "No"
            );
        });

        questionTable.setItems(questions);

        questionTable.setRowFactory(tv -> {
            TableRow<QuestionModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    QuestionModel q = row.getItem();
                    handleRowAction(q);
                }
            });
            return row;
        });
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
        roomLabel.setText("Room: " + roomId);
        loadQuestions();
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    private void loadQuestions() {
        questions.clear();
        List<QuestionModel> list = db.getQuestionsByRoomId(roomId);
        questions.addAll(list);
    }

    private void handleRowAction(QuestionModel question) {
        if (question == null) return;

        switch (mode) {
            case "PREVIEW":
                openPreview(question);
                break;
            case "UPDATE":
                openQuestionEditor(question);
                break;
            case "DELETE":
                db.deleteQuestion(question.getId());
                loadQuestions();
                break;
            default:
                break;
        }
    }

    private void openQuestionEditor(QuestionModel question) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("create_question.fxml")
            );
            Scene scene = new Scene(loader.load());

            CreateQuestionController controller = loader.getController();
            controller.setRoomId(roomId);
            controller.setQuestionToEdit(question, this::loadQuestions);

            Stage stage = new Stage();
            stage.setTitle("Edit Question");
            stage.setScene(scene);
            stage.initOwner(questionTable.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openPreview(QuestionModel question) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("question_preview.fxml")
            );
            Scene scene = new Scene(loader.load());

            QuestionPreviewController controller = loader.getController();
            controller.setQuestion(question);

            Stage stage = new Stage();
            stage.setTitle("Preview Question");
            stage.setScene(scene);
            stage.initOwner(questionTable.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
