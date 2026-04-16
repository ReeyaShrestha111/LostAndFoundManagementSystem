package com.lostandfound.service;

import com.lostandfound.config.DBConfig;
import com.lostandfound.model.User;
import java.sql.*;

// This class contains all business logic related to users
public class UserService {

    // This method checks email and password against the database
    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password_hash = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // These lines put the values into the query safely
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setRole(rs.getString("role"));
                u.setFullName(rs.getString("full_name"));
                u.setEmail(rs.getString("email"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // This method inserts a new user into the database
    public boolean register(User user) {
        String sql = "INSERT INTO users (role, full_name, email, phone, student_id, password_hash) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getRole());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getStudentId());
            ps.setString(6, user.getPasswordHash());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
