<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login — Lost & Found</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

<div class="auth-page">
    <div class="auth-card">

        <div class="auth-header">
            <span class="logo-icon">🔍</span>
            <h1>Lost & Found</h1>
            <p>Management System — Islington College</p>
        </div>

        <%-- Show success message after registration --%>
        <% if ("true".equals(request.getParameter("registered"))) { %>
        <div class="alert alert-success">✅ Account created! Please log in.</div>
        <% } %>

        <%-- Show error message from servlet --%>
        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="alert alert-danger">⚠️ <%= request.getAttribute("errorMessage") %></div>
        <% } %>

        <form action="<%= request.getContextPath() %>/login" method="post">

            <div class="form-group">
                <label for="userEmail">Email Address</label>
                <input type="email" id="userEmail" name="userEmail"
                       placeholder="student@example.com" required
                       value="<%= request.getParameter("userEmail") != null ? request.getParameter("userEmail") : "" %>">
            </div>

            <div class="form-group">
                <label for="userPassword">Password</label>
                <input type="password" id="userPassword" name="userPassword"
                       placeholder="Enter your password" required>
            </div>

            <button type="submit" class="btn btn-primary">🔐 Login</button>
        </form>

        <div class="auth-footer">
            Don't have an account? <a href="<%= request.getContextPath() %>/register">Register here</a>
        </div>

    </div>
</div>

</body>
</html>
