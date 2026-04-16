<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login - Lost and Found</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> }
</head>
<body>
<div class="container">
<%@ include file="header.jsp" %>
<h2 style="text-align:center; margin:20px 0;">Login</h2>
<% if (request.getAttribute("error") != null) { %>
<p class="error">${requestScope.error}</p>
<% } %>
<form action="${pageContext.request.contextPath}/login" method="post">
<input type="email" name="email" placeholder="Email" required>
<input type="password" name="password" placeholder="password" required>
<button type="submit">Login</button>
</form>
<p style="text-align:center; margin-top:15px;">New user? <a href="${pageContext.request.contextPath}/register">Register here</a></p>
<%@ include file="footer.jsp" %>
</div>
</body>
</html>