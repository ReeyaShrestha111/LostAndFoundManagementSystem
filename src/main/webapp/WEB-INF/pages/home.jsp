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
    List<ItemModel> recentItems = (List<ItemModel>) request.getAttribute("recentItems");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home — Lost & Found</title>
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
        <a href="<%= request.getContextPath() %>/home" class="active">Home</a>
        <a href="<%= request.getContextPath() %>/browse">Browse Items</a>
        <a href="<%= request.getContextPath() %>/report-item">Report Item</a>
        <a href="<%= request.getContextPath() %>/logout" class="btn-logout">Logout</a>
    </div>
</nav>

<div class="page-wrapper">

    <%-- Success message after reporting --%>
    <% if ("true".equals(request.getParameter("reported"))) { %>
    <div class="alert alert-success">✅ Your item has been reported successfully!</div>
    <% } %>

    <!-- Welcome Banner -->
    <div class="welcome-banner">
        <div>
            <h2>Welcome back, <%= currentUser.getFullName().split(" ")[0] %>! 👋</h2>
            <p>Browse recently found items below, or report something you've lost or found.</p>
        </div>
        <div style="display:flex;gap:0.75rem;flex-wrap:wrap;">
            <a href="<%= request.getContextPath() %>/report-item" class="btn btn-accent">+ Report Item</a>
            <a href="<%= request.getContextPath() %>/browse" class="btn btn-outline" style="color:#fff;border-color:rgba(255,255,255,0.5);">Browse All</a>
        </div>
    </div>

    <!-- Recent Found Items -->
    <div class="page-header">
        <div>
            <h2>Recently Found Items</h2>
            <p>Items that have been turned in and are awaiting their owners</p>
        </div>
        <a href="<%= request.getContextPath() %>/browse" class="btn btn-outline">View All →</a>
    </div>

    <% if (recentItems != null && !recentItems.isEmpty()) { %>
    <div class="items-grid">
        <% for (ItemModel item : recentItems) { %>
        <div class="item-card">

            <% if (item.getImagePath() != null && !item.getImagePath().isEmpty()) { %>
            <img src="<%= request.getContextPath() %>/<%= item.getImagePath() %>"
                 alt="Item image" class="item-card-img">
            <% } else { %>
            <div class="item-card-img-placeholder">📦</div>
            <% } %>

            <div class="item-card-body">
                <div class="item-card-category"><%= item.getCategory() %></div>
                <div class="item-card-title">
                    <%= item.getDescription().length() > 60
                        ? item.getDescription().substring(0, 60) + "..."
                        : item.getDescription() %>
                </div>
                <% if (item.getColor() != null && !item.getColor().isEmpty()) { %>
                <div class="item-card-desc">Color: <%= item.getColor() %>
                    <% if (item.getBrand() != null && !item.getBrand().isEmpty()) { %>
                    | Brand: <%= item.getBrand() %>
                    <% } %>
                </div>
                <% } %>
                <div class="item-card-meta">
                    <span>📍 <%= item.getLocation() != null && !item.getLocation().isEmpty() ? item.getLocation() : "N/A" %></span>
                    <span>📅 <%= item.getDateOccurred() %></span>
                </div>
                <div style="margin-top:0.75rem;">
                    <span class="badge badge-found">Found</span>
                    <span class="badge badge-pending" style="margin-left:0.4rem;"><%= item.getStatus() %></span>
                </div>
            </div>
        </div>
        <% } %>
    </div>
    <% } else { %>
    <div class="empty-state">
        <div class="empty-icon">📭</div>
        <h3>No found items yet</h3>
        <p>Be the first to report a found item and help reunite it with its owner!</p>
    </div>
    <% } %>

</div>

<footer>
    &copy; 2026 <span>Lost &amp; Found Management System</span> — Islington College
</footer>

</body>
</html>
