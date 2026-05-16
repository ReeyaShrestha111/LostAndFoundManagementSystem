		<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
		<%@ page import="com.lostandfound.model.UserModel" %>
		<%@ page import="com.lostandfound.model.ItemModel" %>
		<%@ page import="java.util.List" %>
		<%
		    UserModel currentUser = (UserModel) session.getAttribute("user");
		    if (currentUser == null || !"admin".equals(currentUser.getRole())) {
		        response.sendRedirect(request.getContextPath() + "/login");
		        return;
		    }
		
		    Object itemsObj = request.getAttribute("allItems");
		    List<ItemModel> allItems = null;
		    if (itemsObj instanceof List<?>) {
		        try {
		            allItems = (List<ItemModel>) itemsObj;
		        } catch (ClassCastException e) {
		            allItems = null; // fallback if type mismatch
		        }
		    }
		
		    Integer totalUsersObj   = (Integer) request.getAttribute("totalUsers");
		    Integer totalItemsObj   = (Integer) request.getAttribute("totalItems");
		    Integer pendingItemsObj = (Integer) request.getAttribute("pendingItems");
		
		    int totalUsers   = totalUsersObj != null ? totalUsersObj : 0;
		    int totalItems   = totalItemsObj != null ? totalItemsObj : 0;
		    int pendingItems = pendingItemsObj != null ? pendingItemsObj : 0;
		%>
		<!DOCTYPE html>
		<html lang="en">
		<head>
		    <meta charset="UTF-8">
		    <meta name="viewport" content="width=device-width, initial-scale=1.0">
		    <title>Admin Dashboard - Lost &amp; Found</title>
		    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
		</head>
		<body>
		
		<!-- Navbar -->
		<nav class="navbar">
		    <div class="navbar-brand">
		        <span class="icon">🔍</span> Lost &amp; Found <span style="font-size:0.75rem;opacity:0.7;margin-left:0.5rem;">[ADMIN]</span>
		    </div>
		    <div class="navbar-nav">
		        <span class="navbar-user">🛡️ <%= currentUser.getFullName() %></span>
		        <a href="<%= request.getContextPath() %>/admin/dashboard" class="active">Dashboard</a>
		        <a href="<%= request.getContextPath() %>/logout" class="btn-logout">Logout</a>
		    </div>
		</nav>
		
		<div class="page-wrapper">
		
		    <div class="page-header">
		        <div>
		            <h2>Admin Dashboard</h2>
		            <p>Manage all reported items and monitor the system</p>
		        </div>
		    </div>
		
		    <!-- Stats Cards -->
		    <div class="stats-grid">
		        <div class="stat-card">
		            <div class="stat-number"><%= totalUsers %></div>
		            <div class="stat-label">👥 Registered Users</div>
		        </div>
		        <div class="stat-card accent-border">
		            <div class="stat-number"><%= totalItems %></div>
		            <div class="stat-label">📦 Total Items</div>
		        </div>
		        <div class="stat-card success-border">
		            <div class="stat-number"><%= pendingItems %></div>
		            <div class="stat-label">⏳ Pending Items</div>
		        </div>
		    </div>
		
		    <!-- All Items Table -->
		    <div class="section-card">
		        <h3>📋 All Reported Items</h3>
		
		        <% if (request.getAttribute("errorMessage") != null) { %>
		        <div class="alert alert-danger"><%= request.getAttribute("errorMessage") %></div>
		        <% } %>
		
		        <div class="table-wrapper">
		            <table>
		                <thead>
		                    <tr>
		                        <th>#</th>
		                        <th>Type</th>
		                        <th>Category</th>
		                        <th>Description</th>
		                        <th>Location</th>
		                        <th>Date</th>
		                        <th>Reported By</th>
		                        <th>Status</th>
		                        <th>Action</th>
		                    </tr>
		                </thead>
		                <tbody>
		                <% if (allItems != null && !allItems.isEmpty()) {
		                       for (ItemModel item : allItems) { %>
		                <tr>
		                    <td><%= item.getId() %></td>
		                    <td><span class="badge badge-<%= item.getType() %>"><%= item.getType() %></span></td>
		                    <td><%= item.getCategory() %></td>
		                    <td style="max-width:200px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">
		                        <%= item.getDescription() %>
		                    </td>
		                    <td><%= item.getLocation() != null ? item.getLocation() : "—" %></td>
		                    <td><%= item.getDateOccurred() %></td>
		                    <td><%= item.getReportedBy() %></td>
		                    <td><span class="badge badge-<%= item.getStatus() %>"><%= item.getStatus() %></span></td>
		                    <td>
		                        <!-- Quick status update form -->
		                        <form action="<%= request.getContextPath() %>/admin/dashboard" method="post"
		                              style="display:flex;gap:0.4rem;flex-wrap:wrap;">
		                            <input type="hidden" name="itemId" value="<%= item.getId() %>">
		                            <select name="newStatus" style="padding:0.3rem 0.5rem;border-radius:6px;border:1px solid #dde3ec;font-size:0.8rem;">
		                                <option value="pending"  <%= "pending".equals(item.getStatus()) ? "selected" : "" %>>Pending</option>
		                                <option value="claimed"  <%= "claimed".equals(item.getStatus()) ? "selected" : "" %>>Claimed</option>
		                                <option value="resolved" <%= "resolved".equals(item.getStatus()) ? "selected" : "" %>>Resolved</option>
		                            </select>
		                            <button type="submit" class="btn btn-success btn-sm">Update</button>
		                        </form>
		                    </td>
		                </tr>
		                <% }
		                   } else { %>
		                <tr>
		                    <td colspan="9" style="text-align:center;color:#6b7a8d;padding:2rem;">
		                        No items reported yet.
		                    </td>
		                </tr>
		                <% } %>
		                </tbody>
		            </table>
		        </div>
		    </div>
		
		</div>
		
		<footer>
		    &copy; 2026 <span>Lost &amp; Found Management System</span> - Islington College
		</footer>
		
		</body>
		</html>
				