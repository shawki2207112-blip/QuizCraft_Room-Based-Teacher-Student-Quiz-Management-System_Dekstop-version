package com.example.project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDatabase {

    private static final String DB_URL = "jdbc:sqlite:questions.db";

    public ResultDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (Statement st = conn.createStatement()) {
                st.execute(
                        "CREATE TABLE IF NOT EXISTS results (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "roomId TEXT," +
                                "studentName TEXT," +
                                "score INTEGER," +
                                "total INTEGER" +
                                ")"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertResult(String roomId, String studentName, int score, int total) {
        String sql = "INSERT INTO results(roomId,studentName,score,total) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomId);
            ps.setString(2, studentName);
            ps.setInt(3, score);
            ps.setInt(4, total);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ResultModel> getResultsByRoomId(String roomId) {
        List<ResultModel> list = new ArrayList<>();
        String sql = "SELECT * FROM results WHERE roomId = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ResultModel(
                            rs.getInt("id"),
                            rs.getString("roomId"),
                            rs.getString("studentName"),
                            rs.getInt("score"),
                            rs.getInt("total")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
