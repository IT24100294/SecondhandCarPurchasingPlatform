<%@ page contentType="text/html; charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Admin Dashboard</title>
    <link rel="stylesheet" href="style.css">
    <style>
        .error-container {
            text-align: center;
            padding: 2rem;
            margin: 2rem auto;
            max-width: 600px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        
        .error-icon {
            font-size: 4rem;
            color: #e74c3c;
            margin-bottom: 1rem;
        }
        
        .error-message {
            color: #2c3e50;
            margin-bottom: 1.5rem;
        }
        
        .back-button {
            display: inline-block;
            padding: 0.75rem 1.5rem;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }
        
        .back-button:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
<nav>
    <h2>Admin Panel</h2>
    <ul>
        <li><a href="dashboard.jsp">Dashboard</a></li>
        <li><a href="users.jsp">User Management</a></li>
        <li><a href="reports.jsp">Reports</a></li>
    </ul>
</nav>

<div class="error-container">
    <div class="error-icon">⚠️</div>
    <h1>Oops! Something went wrong</h1>
    <p class="error-message">
        ${error != null ? error : 'An unexpected error occurred. Please try again later.'}
    </p>
    <a href="dashboard.jsp" class="back-button">Return to Dashboard</a>
</div>
</body>
</html> 