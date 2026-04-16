package com.lostandfound.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This line defines the class that manages database connections
public class DBConfig {
    
    // These lines store the connection details
    private static final String URL = "jdbc:mysql://localhost:3306/lostandfound?useSSL=false&serverTimezone=Asia/Kathmandu";
    private static final String USER = "root";
    private static final String PASS = "";

    // This method creates and returns a new database connection
    public static Connection getConnection() throws SQLException {
        try {
            // This line loads the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
        // This line actually connects to the database
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
