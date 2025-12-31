package com.example.project;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDatabase {

    private static final String DB_URL = "jdbc:sqlite:questions.db";

    public QuestionDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (Statement st = conn.createStatement()) {
                st.execute(
                        "CREATE TABLE IF NOT EXISTS questions (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "roomId TEXT NOT NULL," +
                                "question TEXT NOT NULL," +
                                "imagePath TEXT," +
                                "options TEXT NOT NULL," +
                                "correctAnswer TEXT NOT NULL" +
                                ")"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertQuestion(String roomId,
                               String question,
                               String imagePath,
                               List<String> options,
                               String correctAnswer) {
        String sql = "INSERT INTO questions(roomId,question,imagePath,options,correctAnswer) " +
                "VALUES(?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, roomId);
            ps.setString(2, question);
            ps.setString(3, imagePath);
            ps.setString(4, String.join("|", options));
            ps.setString(5, correctAnswer);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<QuestionModel> getQuestionsByRoomId(String roomId) {
        List<QuestionModel> list = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE roomId = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String optsStr = rs.getString("options");
                    List<String> opts = new ArrayList<>(
                            Arrays.asList(optsStr.split("\\|"))
                    );
                    list.add(new QuestionModel(
                            rs.getInt("id"),
                            rs.getString("roomId"),
                            rs.getString("question"),
                            rs.getString("imagePath"),
                            opts,
                            rs.getString("correctAnswer")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
