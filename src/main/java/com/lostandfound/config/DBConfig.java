package com.lostandfound.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConfig - Database Configuration Class
 * Handles MySQL connection using JDBC
 */
public class DBConfig {

    // Adjust database name to match your actual schema in MySQL
	private static final String Db_Name = "lostfound_db";
    private static final String URL      = "jdbc:mysql://localhost:3306/"+ Db_Name;
    private static final String USERNAME = "root";       // default XAMPP user
    private static final String PASSWORD = "";           // default XAMPP password (empty)

    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Ensure mysql-connector-java.jar is in WEB-INF/lib.", e);
        }
    }
}
