package com.lostandfound.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConfig - Database Configuration Class
 * Handles MySQL connection using JDBC
 * Package: com.lostandfound.config
 */
public class DBConfig {

    private static final String URL      = "jdbc:mysql://localhost:3306/lostfound_db?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";  // XAMPP default has no password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Add mysql-connector-java.jar to your project.", e);
        }
    }
}
