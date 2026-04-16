<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register - Lost and Found</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
<%@ include file="header.jsp" %>
<h2 style="text-align:center; margin:20px 0;">Register New Account</h2>
<% if (request.getAttribute("error") != null) { %>
<p class="error">${requestScope.error}</p>
<% } %>
<form action="${pageCOntext.request.ContextPath}/register" method="post">
<input type="text" name="fullName" placeholder="Full Name" required>
,input type="email" name="email" placeholder="Email" required>
<input type="text" name="phone" placeholder="Phone Number" required>
<input type="text" name="studentId" placeholder="Student ID" required>
<input type="password" name="password" placeholder="Password" required>
<button type="submit">Register</button>
</form>
<p style="text-align:center; margin-top:15px;">Already have an account? <a href="${pagecontext.request.contextPath}/login">Login here</a></p>
<%@ include file="footer.jsp" %>
</div>
</body>
</html>