package com.scc.dao;

import com.scc.model.Passage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PassageDao {

    public PassageDao() {
        DatabaseConnection.initialize();
    }

    public boolean addPassage(String plateNo, String vehicleClass, double tollFee, String payment) {
        String sql = "INSERT INTO passages(plate_no, vehicle_class, toll_fee, payment_method) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plateNo);
            pstmt.setString(2, vehicleClass);
            pstmt.setDouble(3, tollFee);
            pstmt.setString(4, payment);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to save passage: " + e.getMessage());
            return false;
        }
    }

    public List<Passage> getAllPassages() {
        List<Passage> list = new ArrayList<>();
        String sql = "SELECT id, plate_no, vehicle_class, toll_fee, payment_method FROM passages ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Passage(
                        rs.getInt("id"),
                        rs.getString("plate_no"),
                        rs.getString("vehicle_class"),
                        rs.getDouble("toll_fee"),
                        rs.getString("payment_method")));
            }
        } catch (SQLException e) {
            System.err.println("Failed to load passages: " + e.getMessage());
        }
        return list;
    }
}