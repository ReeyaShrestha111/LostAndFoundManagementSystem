<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member Home - Lost and Found</title>
<link rel="stylesheet" href="${pageCOntext.request.contextPath}/css/style.css"> }
</head>
<body>
<div class="container">
<%@ include file="header.jsp" %>
<h2>Welcome, ${sessionScope.currentUser.fullName}</h2>

<h3>Recently Found Items</h3>
<div class="items-grid">
<!-- Dynamic cards will be added by servlet later -->
</div>
<h3>My Activity</h3>
<p><a href="${pageContext.request.contextPath}/myLostItems">View My Lost Items</a></p>
<p><a href="${pageContext.request.contextPath}/myClaims">View My Claims</a></p>
<%@ include file="footer.jsp" %>
</div>					
</body>		
</html>			