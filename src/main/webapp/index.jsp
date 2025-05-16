<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="/PG197/css/style.css">
</head>
<body>
<nav>
    <h2>Admin Panel</h2>
    <ul>
        <li><a href="/PG197/Frontend/dashboard.jsp" class="active">Dashboard</a></li>
        <li><a href="/PG197/Frontend/users.jsp">User Management</a></li>
        <li><a href="/PG197/Frontend/reports.jsp">Reports</a></li>
    </ul>
</nav>
<div class="main">
    <h1>Dashboard Overview</h1>
    <div class="stats">
        <div class="stat-card">
            <h3>Total Users</h3>
            <p><%= request.getAttribute("userCount") != null ? request.getAttribute("userCount") : "120" %></p>
        </div>
        <div class="stat-card">
            <h3>Total Listings</h3>
            <p><%= request.getAttribute("listingCount") != null ? request.getAttribute("listingCount") : "58" %></p>
        </div>
        <div class="stat-card">
            <h3>Sales</h3>
            <p><%= request.getAttribute("salesCount") != null ? request.getAttribute("salesCount") : "34" %></p>
        </div>
    </div>
    <div class="log-section">
        <h2>Recent Activity</h2>
        <ul>
            <li>User <b>janesmith</b> registered (2025-05-02)</li>
            <li>Car listing #1045 updated by <b>admin</b></li>
            <li>User <b>johndoe</b> purchased a car</li>
        </ul>
    </div>
</div>
</body>
</html>
