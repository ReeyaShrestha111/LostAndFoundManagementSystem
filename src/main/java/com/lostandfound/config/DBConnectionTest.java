package com.lostandfound.config;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionTest {
    public static void main(String[] args) {
        try (Connection conn = DBConfig.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Database connection successful!");
            } else {
                System.out.println("❌ Failed to establish database connection.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
