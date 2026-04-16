package com.lostandfound.controller;

import com.lostandfound.model.User;
import com.lostandfound.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

// This servlet handles all login requests
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService service = new UserService();

    // This method shows the login page
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    // This method processes the login form submission
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = service.login(email, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("currentUser", user);
            if ("admin".equals(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/adminDashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/memberHome");
            }
        } else {
            req.setAttribute("error", "Invalid credentials");
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
        }
    }
}
