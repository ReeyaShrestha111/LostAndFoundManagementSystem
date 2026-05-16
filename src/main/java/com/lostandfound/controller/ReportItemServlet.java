package com.lostandfound.controller;

import com.lostandfound.config.DBConfig;
import com.lostandfound.model.UserModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * ReportItemServlet - Allows users to report a lost or found item
 * URL: /report-item
 * Uses @MultipartConfig for file upload support
 */
@WebServlet("/report-item")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,       // 1 MB
    maxFileSize       = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize    = 1024 * 1024 * 15   // 15 MB
)
public class ReportItemServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // ✅ Added to fix warning

    // Upload directory inside webapp
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/pages/report_item.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UserModel user = (UserModel) session.getAttribute("user");

        String type         = request.getParameter("type");
        String category     = request.getParameter("category");
        String description  = request.getParameter("description");
        String color        = request.getParameter("color");
        String brand        = request.getParameter("brand");
        String dateOccurred = request.getParameter("dateOccurred");
        String location     = request.getParameter("location");

        // Basic validation
        if (category == null || category.isEmpty() || description == null || description.isEmpty()) {
            request.setAttribute("errorMessage", "Category and description are required.");
            request.getRequestDispatcher("/WEB-INF/pages/report_item.jsp").forward(request, response);
            return;
        }

        // Handle image upload
        String imagePath = null;
        Part filePart = request.getPart("itemImage");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + getFileName(filePart);
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            filePart.write(uploadPath + File.separator + fileName);
            imagePath = UPLOAD_DIR + "/" + fileName;
        }

        try (Connection conn = DBConfig.getConnection()) {
            String sql = "INSERT INTO items (user_id, type, category, description, color, brand, date_occurred, location, image_path) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getId());
            stmt.setString(2, type);
            stmt.setString(3, category);
            stmt.setString(4, description);
            stmt.setString(5, color);
            stmt.setString(6, brand);
            stmt.setString(7, dateOccurred);
            stmt.setString(8, location);
            stmt.setString(9, imagePath);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                response.sendRedirect(request.getContextPath() + "/home?reported=true");
            } else {
                request.setAttribute("errorMessage", "Failed to submit report. Please try again.");
                request.getRequestDispatcher("/WEB-INF/pages/report_item.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Something went wrong. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/pages/report_item.jsp").forward(request, response);
        }
    }

    // Helper to extract filename from Part header
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String token : contentDisp.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "upload_" + System.currentTimeMillis();
    }
}
