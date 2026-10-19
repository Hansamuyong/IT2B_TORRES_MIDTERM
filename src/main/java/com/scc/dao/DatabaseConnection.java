package com.scc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:ExpresswayTollSystem.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Creates the table if it does not exist yet. IDs start at 1 automatically.
    public static void initialize() {
        String sql = "CREATE TABLE IF NOT EXISTS passages ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "plate_no TEXT NOT NULL, "
                + "vehicle_class TEXT NOT NULL, "
                + "toll_fee REAL NOT NULL, "
                + "payment_method TEXT NOT NULL)";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Database setup error: " + e.getMessage());
        }
    }
}
