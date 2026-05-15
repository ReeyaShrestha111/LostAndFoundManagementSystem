<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register — Lost & Found</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

<div class="auth-page">
    <div class="auth-card">

        <div class="auth-header">
            <span class="logo-icon">📋</span>
            <h1>Create Account</h1>
            <p>Join the Lost & Found system</p>
        </div>

        <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="alert alert-danger">⚠️ <%= request.getAttribute("errorMessage") %></div>
        <% } %>

        <form action="<%= request.getContextPath() %>/register" method="post">

            <div class="form-group">
                <label for="fullName">Full Name *</label>
                <input type="text" id="fullName" name="fullName"
                       placeholder="e.g. Riya Shrestha" required>
            </div>

            <div class="form-group">
                <label for="userEmail">Email Address *</label>
                <input type="email" id="userEmail" name="userEmail"
                       placeholder="student@example.com" required>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="tel" id="phone" name="phone"
                           placeholder="e.g. 9841000000">
                </div>
                <div class="form-group">
                    <label for="studentId">Student ID</label>
                    <input type="text" id="studentId" name="studentId"
                           placeholder="e.g. NP01AI4A240121">
                </div>
            </div>

            <div class="form-group">
                <label for="userPassword">Password *</label>
                <input type="password" id="userPassword" name="userPassword"
                       placeholder="Minimum 6 characters" required minlength="6">
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm Password *</label>
                <input type="password" id="confirmPassword" name="confirmPassword"
                       placeholder="Re-enter your password" required>
            </div>

            <button type="submit" class="btn btn-primary">✅ Create Account</button>
        </form>

        <div class="auth-footer">
            Already have an account? <a href="<%= request.getContextPath() %>/login">Login here</a>
        </div>

    </div>
</div>

</body>
</html>
