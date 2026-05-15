<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Redirect root URL to login page via servlet
    response.sendRedirect(request.getContextPath() + "/login");
%>
