package com.example.project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class ResultListController {

    @FXML
    private Label roomLabel;
    @FXML
    private TableView<ResultModel> resultTable;
    @FXML
    private TableColumn<ResultModel, String> studentColumn;
    @FXML
    private TableColumn<ResultModel, Number> scoreColumn;
    @FXML
    private TableColumn<ResultModel, Number> totalColumn;

    private final ResultDatabase db = new ResultDatabase();
    private final ObservableList<ResultModel> results = FXCollections.observableArrayList();
    private String roomId;

    @FXML
    private void initialize() {
        studentColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStudentName()));
        scoreColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getScore()));
        totalColumn.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getTotal()));

        resultTable.setItems(results);
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
        roomLabel.setText("Room: " + roomId);
        loadResults();
    }

    private void loadResults() {
        results.clear();
        List<ResultModel> list = db.getResultsByRoomId(roomId);
        results.addAll(list);
    }
}
