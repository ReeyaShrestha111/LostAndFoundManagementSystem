package com.lostandfound.controller;

import com.lostandfound.config.DBConfig;
import com.lostandfound.model.ItemModel;

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
 * BrowseItemsServlet - Displays all found items with optional search
 * URL: /browse
 */
@WebServlet("/browse")
public class BrowseItemsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // ✅ best practice for Serializable classes

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String searchKeyword = request.getParameter("search");
        List<ItemModel> foundItems = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection()) {
            String sql;
            PreparedStatement stmt;

            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                // Search by keyword in description or category
                sql = "SELECT i.*, u.full_name AS reported_by FROM items i " +
                      "JOIN users u ON i.user_id = u.id " +
                      "WHERE i.type = 'found' AND i.status = 'pending' " +
                      "AND (i.category LIKE ? OR i.description LIKE ? OR i.color LIKE ? OR i.location LIKE ?) " +
                      "ORDER BY i.created_at DESC";
                stmt = conn.prepareStatement(sql);
                String kw = "%" + searchKeyword.trim() + "%";
                stmt.setString(1, kw);
                stmt.setString(2, kw);
                stmt.setString(3, kw);
                stmt.setString(4, kw);
            } else {
                sql = "SELECT i.*, u.full_name AS reported_by FROM items i " +
                      "JOIN users u ON i.user_id = u.id " +
                      "WHERE i.type = 'found' AND i.status = 'pending' " +
                      "ORDER BY i.created_at DESC";
                stmt = conn.prepareStatement(sql);
            }

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
                foundItems.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Could not load items.");
        }

        request.setAttribute("foundItems", foundItems);
        request.setAttribute("searchKeyword", searchKeyword);
        request.getRequestDispatcher("/WEB-INF/pages/browse.jsp").forward(request, response);
    }
}
