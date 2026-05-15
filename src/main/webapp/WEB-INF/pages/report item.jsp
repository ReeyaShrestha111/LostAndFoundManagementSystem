<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.lostandfound.model.UserModel" %>
<%
    UserModel currentUser = (UserModel) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Report Item — Lost & Found</title>
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
        <a href="<%= request.getContextPath() %>/browse">Browse Items</a>
        <a href="<%= request.getContextPath() %>/report-item" class="active">Report Item</a>
        <a href="<%= request.getContextPath() %>/logout" class="btn-logout">Logout</a>
    </div>
</nav>

<div class="page-wrapper">

    <div class="page-header">
        <div>
            <h2>Report an Item</h2>
            <p>Fill in the details below to report a lost or found item</p>
        </div>
        <a href="<%= request.getContextPath() %>/home" class="btn btn-outline">← Back to Home</a>
    </div>

    <% if (request.getAttribute("errorMessage") != null) { %>
    <div class="alert alert-danger">⚠️ <%= request.getAttribute("errorMessage") %></div>
    <% } %>

    <div class="section-card" style="max-width:700px;">
        <h3>📝 Item Details</h3>

        <%-- enctype="multipart/form-data" is required for file uploads --%>
        <form action="<%= request.getContextPath() %>/report-item" method="post"
              enctype="multipart/form-data">

            <!-- Type: Lost or Found -->
            <div class="form-group">
                <label>I am reporting a... *</label>
                <div style="display:flex;gap:1rem;margin-top:0.4rem;">
                    <label style="display:flex;align-items:center;gap:0.4rem;font-weight:400;cursor:pointer;">
                        <input type="radio" name="type" value="lost" required> 🚨 Lost Item
                    </label>
                    <label style="display:flex;align-items:center;gap:0.4rem;font-weight:400;cursor:pointer;">
                        <input type="radio" name="type" value="found"> ✅ Found Item
                    </label>
                </div>
            </div>

            <!-- Category -->
            <div class="form-group">
                <label for="category">Category *</label>
                <select id="category" name="category" required>
                    <option value="">— Select a category —</option>
                    <option value="Electronics">Electronics (Phone, Laptop, etc.)</option>
                    <option value="Bag & Accessories">Bag &amp; Accessories</option>
                    <option value="Clothing">Clothing</option>
                    <option value="ID & Documents">ID &amp; Documents</option>
                    <option value="Keys">Keys</option>
                    <option value="Wallet & Money">Wallet &amp; Money</option>
                    <option value="Jewellery">Jewellery</option>
                    <option value="Books & Stationery">Books &amp; Stationery</option>
                    <option value="Sports Equipment">Sports Equipment</option>
                    <option value="Other">Other</option>
                </select>
            </div>

            <!-- Description -->
            <div class="form-group">
                <label for="description">Description *</label>
                <textarea id="description" name="description" required
                          placeholder="Describe the item in detail — colour, size, any unique markings..."></textarea>
            </div>

            <!-- Color & Brand -->
            <div class="form-row">
                <div class="form-group">
                    <label for="color">Color</label>
                    <input type="text" id="color" name="color" placeholder="e.g. Black">
                </div>
                <div class="form-group">
                    <label for="brand">Brand / Make</label>
                    <input type="text" id="brand" name="brand" placeholder="e.g. Samsung">
                </div>
            </div>

            <!-- Date & Location -->
            <div class="form-row">
                <div class="form-group">
                    <label for="dateOccurred">Date Lost/Found *</label>
                    <input type="date" id="dateOccurred" name="dateOccurred" required>
                </div>
                <div class="form-group">
                    <label for="location">Location</label>
                    <input type="text" id="location" name="location"
                           placeholder="e.g. Library, Block A">
                </div>
            </div>

            <!-- Image Upload -->
            <div class="form-group">
                <label for="itemImage">Upload Image (optional)</label>
                <input type="file" id="itemImage" name="itemImage"
                       accept="image/jpeg,image/png,image/jpg,image/webp"
                       style="padding:0.5rem;">
                <small style="color:#6b7a8d;font-size:0.8rem;">Max 10MB — JPG, PNG, or WEBP</small>
            </div>

            <div style="display:flex;gap:1rem;margin-top:0.5rem;flex-wrap:wrap;">
                <button type="submit" class="btn btn-primary" style="flex:1;">📤 Submit Report</button>
                <a href="<%= request.getContextPath() %>/home" class="btn btn-outline" style="flex:1;">Cancel</a>
            </div>

        </form>
    </div>

</div>

<footer>
    &copy; 2026 <span>Lost &amp; Found Management System</span> — Islington College
</footer>

</body>
</html>
