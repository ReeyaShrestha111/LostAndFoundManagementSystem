package com.lostandfound.controller;

import com.lostandfound.model.User;
import com.lostandfound.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

// This servlet handles all registration requests
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService service = new UserService();

    // This method shows the registration page
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    // This method processes the registration form
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        User user = new User();
        user.setFullName(req.getParameter("fullName"));
        user.setEmail(req.getParameter("email"));
        user.setPhone(req.getParameter("phone"));
        user.setStudentId(req.getParameter("studentId"));
        user.setPasswordHash(req.getParameter("password"));
        user.setRole("member");

        if (service.register(user)) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            req.setAttribute("error", "Registration failed - Email or Student ID may already exist");
            req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
        }
    }
}
