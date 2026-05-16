package com.lostandfound.controller;

import com.lostandfound.config.DBConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * RegisterServlet - Handles new user registration
 * URL: /register
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    // ✅ Added to fix the warning
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName  = request.getParameter("fullName").trim();
        String userEmail = request.getParameter("userEmail").trim();
        String phone     = request.getParameter("phone").trim();
        String studentId = request.getParameter("studentId").trim();
        String userPassword    = request.getParameter("userPassword").trim();
        String confirmPassword = request.getParameter("confirmPassword").trim();

        // Validation
        if (fullName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
            request.setAttribute("errorMessage", "Please fill in all required fields.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        if (!userPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBConfig.getConnection()) {

            // Check if email already exists
            String checkSql = "SELECT id FROM users WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, userEmail);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                request.setAttribute("errorMessage", "An account with this email already exists.");
                request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
                return;
            }

            // Insert new user
            String sql = "INSERT INTO users (full_name, email, phone, student_id, password, role) VALUES (?, ?, ?, ?, ?, 'user')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullName);
            stmt.setString(2, userEmail);
            stmt.setString(3, phone);
            stmt.setString(4, studentId);
            stmt.setString(5, userPassword);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                response.sendRedirect(request.getContextPath() + "/login?registered=true");
            } else {
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Something went wrong. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
        }
    }
}
