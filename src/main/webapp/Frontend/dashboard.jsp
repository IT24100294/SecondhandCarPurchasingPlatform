<%--noinspection JSPUnresolvedVariable --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<nav>
    <h2>Admin Panel</h2>
    <ul>
        <li><a href="dashboard" class="active">Dashboard</a></li>
        <li><a href="users">User Management</a></li>
        <li><a href="reports">Reports</a></li>
    </ul>
</nav>

<div class="main">
    <h1>Dashboard Overview</h1>
    
    <c:if test="${not empty error}">
        <div class="alert alert-error">
            <p>${error}</p>
        </div>
    </c:if>

    <div class="stats">
        <div class="stat-card">
            <h3>Total Users</h3>
            <p>
                <c:choose>
                    <c:when test="${not empty userCount}">
                        ${userCount}
                    </c:when>
                    <c:otherwise>
                        <span class="loading">Loading...</span>
                    </c:otherwise>
                </c:choose>
            </p>
        </div>
        <div class="stat-card">
            <h3>Total Listings</h3>
            <p>
                <c:choose>
                    <c:when test="${not empty listingCount}">
                        ${listingCount}
                    </c:when>
                    <c:otherwise>
                        <span class="loading">Loading...</span>
                    </c:otherwise>
                </c:choose>
            </p>
        </div>
        <div class="stat-card">
            <h3>Sales</h3>
            <p>
                <c:choose>
                    <c:when test="${not empty salesCount}">
                        ${salesCount}
                    </c:when>
                    <c:otherwise>
                        <span class="loading">Loading...</span>
                    </c:otherwise>
                </c:choose>
            </p>
        </div>
    </div>

    <div class="log-section">
        <h2>Recent Activity</h2>
        <c:choose>
            <c:when test="${not empty recentActivities}">
                <ul>
                    <c:forEach items="${recentActivities}" var="activity">
                        <li>${activity}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <p class="no-data">No recent activities to display</p>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="refresh-section">
        <button onclick="window.location.reload()" class="refresh-button">
            Refresh Dashboard
        </button>
    </div>
</div>

<script>
    // Auto-refresh dashboard every 5 minutes
    setTimeout(function() {
        window.location.reload();
    }, 300000);
</script>
</body>
</html>
