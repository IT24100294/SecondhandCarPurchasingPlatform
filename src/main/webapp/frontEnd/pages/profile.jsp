<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            padding: 40px;
        }
        .profile-container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            max-width: 800px;
            margin: auto;
        }
        .profile-container h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #0d6efd;
        }
        .alert {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="profile-container">
    <h2>User Profile</h2>

    <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="alert alert-danger">
            <%= request.getAttribute("errorMessage") %>
        </div>
    <% } %>

    <% if (request.getAttribute("successMessage") != null) { %>
        <div class="alert alert-success">
            <%= request.getAttribute("successMessage") %>
        </div>
    <% } %>

    <% User user = (User) request.getAttribute("user"); %>
    <% if (user != null) { %>
        <form action="<%=request.getContextPath()%>/UserProfile" method="post">
            <input type="hidden" name="action" value="update">
            
            <div class="mb-3">
                <label for="fullName" class="form-label">Full Name</label>
                <input type="text" class="form-control" id="fullName" name="fullName" value="<%= user.getFullName() %>" required>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" value="<%= user.getEmail() %>" readonly>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">New Password (leave blank to keep current)</label>
                <input type="password" class="form-control" id="password" name="password">
            </div>

            <div class="mb-3">
                <label for="address" class="form-label">Address</label>
                <input type="text" class="form-control" id="address" name="address" value="<%= user.getAddress() != null ? user.getAddress() : "" %>">
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="city" class="form-label">City</label>
                    <input type="text" class="form-control" id="city" name="city" value="<%= user.getCity() != null ? user.getCity() : "" %>">
                </div>

                <div class="col-md-3 mb-3">
                    <label for="state" class="form-label">State</label>
                    <input type="text" class="form-control" id="state" name="state" value="<%= user.getState() != null ? user.getState() : "" %>">
                </div>

                <div class="col-md-3 mb-3">
                    <label for="zip" class="form-label">Zip</label>
                    <input type="text" class="form-control" id="zip" name="zip" value="<%= user.getZip() != null ? user.getZip() : "" %>">
                </div>
            </div>

            <div class="d-grid gap-2">
                <button type="submit" class="btn btn-primary">Update Profile</button>
            </div>
        </form>

        <hr class="my-4">

        <form action="<%=request.getContextPath()%>/UserProfile" method="post" onsubmit="return confirm('Are you sure you want to delete your account? This action cannot be undone.');">
            <input type="hidden" name="action" value="delete">
            <div class="d-grid">
                <button type="submit" class="btn btn-danger">Delete Account</button>
            </div>
        </form>
    <% } else { %>
        <div class="alert alert-warning">
            User information not found. Please <a href="<%=request.getContextPath()%>/frontEnd/pages/login.jsp">login</a> again.
        </div>
    <% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 