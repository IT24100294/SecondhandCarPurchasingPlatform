<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reports</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<nav>
    <h2>Admin Panel</h2>
    <ul>
        <li><a href="dashboard.jsp">Dashboard</a></li>
        <li><a href="users.jsp">User Management</a></li>
        <li><a href="reports.jsp" class="active">Reports</a></li>
    </ul>
</nav>
<div class="main">
    <h1>Report Generation</h1>
    <div class="log-section">
        <h2>System Logs</h2>
        <pre>
2025-05-02 11:30:17: Car listing #1045 updated by admin
2025-05-02 11:15:42: New user 'janesmith' created
2025-05-02 10:58:33: Failed login attempt for user 'unknown'
2025-05-02 10:45:12: Car listing #1042 deleted by admin
2025-05-02 10:30:05: User 'michaelb' profile updated
2025-05-02 10:15:22: User 'admin' logged in successfully
    </pre>
    </div>
    <div class="log-section">
        <h2>Summary</h2>
        <ul>
            <li>Total Users: <%= request.getAttribute("userCount") != null ? request.getAttribute("userCount") : "120" %></li>
            <li>Total Listings: <%= request.getAttribute("listingCount") != null ? request.getAttribute("listingCount") : "58" %></li>
            <li>Total Sales: <%= request.getAttribute("salesCount") != null ? request.getAttribute("salesCount") : "34" %></li>
        </ul>
    </div>
</div>
</body>
</html>

