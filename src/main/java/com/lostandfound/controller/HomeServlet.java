package com.lostandfound.controller;

import com.lostandfound.config.DBConfig;
import com.lostandfound.model.ItemModel;
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
import java.util.ArrayList;
import java.util.List;

/**
 * HomeServlet - Loads the member home/dashboard page
 * URL: /home
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UserModel user = (UserModel) session.getAttribute("user");
        // Admin should not see member home
        if ("admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        // Fetch recent found items to display on home page
        List<ItemModel> recentItems = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection()) {
            String sql = "SELECT i.*, u.full_name AS reported_by FROM items i " +
                         "JOIN users u ON i.user_id = u.id " +
                         "WHERE i.type = 'found' AND i.status = 'pending' " +
                         "ORDER BY i.created_at DESC LIMIT 6";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ItemModel item = new ItemModel();
                item.setId(rs.getInt("id"));
                item.setUserId(rs.getInt("user_id"));
                item.setType(rs.getString("type"));
                item.setCategory(rs.getString("category"));
                item.setDescription(rs.getString("description"));
                item.setColor(rs.getString("color"));
                item.setBrand(rs.getString("brand"));
                item.setDateOccurred(rs.getString("date_occurred"));
                item.setLocation(rs.getString("location"));
                item.setImagePath(rs.getString("image_path"));
                item.setStatus(rs.getString("status"));
                item.setCreatedAt(rs.getString("created_at"));
                item.setReportedBy(rs.getString("reported_by"));
                recentItems.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Could not load items.");
        }

        request.setAttribute("recentItems", recentItems);
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }
}
