<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Server Error — Lost & Found</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<div style="min-height:100vh;display:flex;align-items:center;justify-content:center;background:var(--bg,#f4f6f9);">
    <div style="text-align:center;padding:3rem;">
        <div style="font-size:5rem;margin-bottom:1rem;">⚙️</div>
        <h1 style="font-family:'Playfair Display',serif;font-size:2.5rem;color:#1a3c5e;margin-bottom:0.5rem;">500 — Server Error</h1>
        <p style="color:#6b7a8d;margin-bottom:1.5rem;">Something went wrong on our end. Please try again later.</p>
        <a href="<%= request.getContextPath() %>/login"
           style="background:#1a3c5e;color:#fff;padding:0.7rem 1.5rem;border-radius:8px;text-decoration:none;font-weight:600;">
            Return to Login
        </a>
    </div>
</div>
</body>
</html>
