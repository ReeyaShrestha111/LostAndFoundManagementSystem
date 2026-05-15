<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.lostandfound.model.UserModel" %>
<%@ page import="com.lostandfound.model.ItemModel" %>
<%@ page import="java.util.List" %>
<%
    UserModel currentUser = (UserModel) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    List<ItemModel> foundItems = (List<ItemModel>) request.getAttribute("foundItems");
    String searchKeyword = (String) request.getAttribute("searchKeyword");
    if (searchKeyword == null) searchKeyword = "";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Browse Items — Lost & Found</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

<!-- Navbar -->
<nav class="navbar">
    <div class="navbar-brand">
        <span class="icon">🔍</span> Lost &amp; Found
    </div>
    <div class="navbar-nav">
        <span class="navbar-user">👤 <%= currentUser.getFullName() %></span>
        <a href="<%= request.getContextPath() %>/home">Home</a>
        <a href="<%= request.getContextPath() %>/browse" class="active">Browse Items</a>
        <a href="<%= request.getContextPath() %>/report-item">Report Item</a>
        <a href="<%= request.getContextPath() %>/logout" class="btn-logout">Logout</a>
    </div>
</nav>

<div class="page-wrapper">

    <div class="page-header">
        <div>
            <h2>Browse Found Items</h2>
            <p>Search through items that have been turned in — your belongings might be here!</p>
        </div>
        <a href="<%= request.getContextPath() %>/report-item" class="btn btn-accent">+ Report Item</a>
    </div>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="alert alert-danger">⚠️ <%= request.getAttribute("errorMessage") %></div>
    <% } %>

    <!-- Search Bar -->
    <form action="<%= request.getContextPath() %>/browse" method="get">
        <div class="search-bar">
            <input type="text" name="search" placeholder="🔍 Search by keyword, category, colour, location..."
                   value="<%= searchKeyword %>">
            <button type="submit" class="btn btn-primary" style="width:auto;">Search</button>
            <% if (!searchKeyword.isEmpty()) { %>
            <a href="<%= request.getContextPath() %>/browse" class="btn btn-outline">Clear</a>
            <% } %>
        </div>
    </form>

    <% if (!searchKeyword.isEmpty()) { %>
    <p style="margin-bottom:1rem;color:#6b7a8d;font-size:0.88rem;">
        Showing results for: <strong>"<%= searchKeyword %>"</strong>
        (<%= foundItems != null ? foundItems.size() : 0 %> item(s) found)
    </p>
    <% } %>

    <!-- Items Grid -->
    <% if (foundItems != null && !foundItems.isEmpty()) { %>
    <div class="items-grid">
        <% for (ItemModel item : foundItems) { %>
        <div class="item-card">

            <% if (item.getImagePath() != null && !item.getImagePath().isEmpty()) { %>
            <img src="<%= request.getContextPath() %>/<%= item.getImagePath() %>"
                 alt="Item image" class="item-card-img">
            <% } else { %>
            <div class="item-card-img-placeholder">
                <% String cat = item.getCategory();
                   if (cat != null) {
                       if (cat.contains("Electronic"))   out.print("📱");
                       else if (cat.contains("Bag"))     out.print("👜");
                       else if (cat.contains("Clothing"))out.print("👕");
                       else if (cat.contains("ID"))      out.print("🪪");
                       else if (cat.contains("Key"))     out.print("🔑");
                       else if (cat.contains("Wallet"))  out.print("💰");
                       else if (cat.contains("Jewel"))   out.print("💍");
                       else if (cat.contains("Book"))    out.print("📚");
                       else                              out.print("📦");
                   } else { out.print("📦"); } %>
            </div>
            <% } %>

            <div class="item-card-body">
                <div class="item-card-category"><%= item.getCategory() %></div>
                <div class="item-card-title">
                    <%= item.getDescription().length() > 70
                        ? item.getDescription().substring(0, 70) + "..."
                        : item.getDescription() %>
                </div>
                <div class="item-card-desc">
                    <% if (item.getColor() != null && !item.getColor().isEmpty()) { %>
                    Color: <%= item.getColor() %>
                    <% } %>
                    <% if (item.getBrand() != null && !item.getBrand().isEmpty()) { %>
                    | Brand: <%= item.getBrand() %>
                    <% } %>
                </div>
                <div class="item-card-meta">
                    <span>📍 <%= item.getLocation() != null && !item.getLocation().isEmpty() ? item.getLocation() : "N/A" %></span>
                    <span>📅 <%= item.getDateOccurred() %></span>
                </div>
                <div style="margin-top:0.75rem;display:flex;align-items:center;justify-content:space-between;flex-wrap:wrap;gap:0.5rem;">
                    <div>
                        <span class="badge badge-found">Found</span>
                        <span class="badge badge-<%= item.getStatus() %>" style="margin-left:0.4rem;"><%= item.getStatus() %></span>
                    </div>
                    <small style="color:#6b7a8d;">By: <%= item.getReportedBy() %></small>
                </div>
            </div>
        </div>
        <% } %>
    </div>

    <% } else { %>
    <div class="empty-state">
        <div class="empty-icon">🔍</div>
        <h3><%= !searchKeyword.isEmpty() ? "No items match your search" : "No found items yet" %></h3>
        <p><%= !searchKeyword.isEmpty() ? "Try different keywords or clear your search." : "Check back later — found items will appear here." %></p>
        <% if (!searchKeyword.isEmpty()) { %>
        <a href="<%= request.getContextPath() %>/browse" class="btn btn-outline" style="margin-top:1rem;">View All Items</a>
        <% } %>
    </div>
    <% } %>

</div>

<footer>
    &copy; 2026 <span>Lost &amp; Found Management System</span> — Islington College
</footer>

</body>
</html>
