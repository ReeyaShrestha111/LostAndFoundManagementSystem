package com.lostandfound.controller;

import com.lostandfound.config.DBConfig;
import com.lostandfound.model.UserModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * LoginServlet - Handles user login (GET loads page, POST validates credentials)
 * URL: /login
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // If already logged in, redirect away from login page
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            UserModel user = (UserModel) session.getAttribute("user");
            if ("admin".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }
            return;
        }
        // Forward to login.jsp
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userEmail    = request.getParameter("userEmail").trim();
        String userPassword = request.getParameter("userPassword").trim();

        // Basic validation
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            request.setAttribute("errorMessage", "Please fill in all fields.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBConfig.getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userEmail);
            stmt.setString(2, userPassword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Build user model from DB
                UserModel user = new UserModel();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setStudentId(rs.getString("student_id"));
                user.setRole(rs.getString("role"));

                // Store in session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Redirect based on role
                if ("admin".equals(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                } else {
                    response.sendRedirect(request.getContextPath() + "/home");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid email or password. Please try again.");
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Something went wrong. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}
