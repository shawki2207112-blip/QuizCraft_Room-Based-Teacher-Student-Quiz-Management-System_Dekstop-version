package com.example.project;

import java.util.List;

public class QuestionModel {

    private int id;
    private String roomId;
    private String question;
    private String imagePath;
    private List<String> options;
    private String correctAnswer;

    public QuestionModel(int id, String roomId, String question,
                         String imagePath, List<String> options,
                         String correctAnswer) {
        this.id = id;
        this.roomId = roomId;
        this.question = question;
        this.imagePath = imagePath;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public int getId() { return id; }
    public String getRoomId() { return roomId; }
    public String getQuestion() { return question; }
    public String getImagePath() { return imagePath; }
    public List<String> getOptions() { return options; }
    public String getCorrectAnswer() { return correctAnswer; }
}
