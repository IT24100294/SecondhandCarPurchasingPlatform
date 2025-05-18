<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Login</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <style>
    body {
      background-color: #f5f7fa;
    }
    .admin-login-container {
      max-width: 400px;
      margin: 100px auto;
      padding: 2rem;
      background-color: #fff;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }
    .admin-login-header {
      text-align: center;
      margin-bottom: 2rem;
    }
    .admin-login-header .logo {
      font-size: 2rem;
      color: var(--primary-color);
      margin-bottom: 1rem;
      display: block;
    }
    .admin-login-header h1 {
      font-size: 1.5rem;
      font-weight: 500;
      color: var(--text-primary);
    }
    .admin-login-form .form-group {
      margin-bottom: 1.5rem;
    }
    .admin-login-form .btn {
      width: 100%;
      padding: 0.75rem;
      font-size: 1rem;
      margin-top: 1rem;
    }
    .back-link {
      display: block;
      text-align: center;
      margin-top: 1.5rem;
      color: var(--text-secondary);
    }
  </style>
</head>
<body>
<div class="admin-login-container">
  <div class="admin-login-header">
    <span class="logo"><i class="fas fa-cogs"></i> Admin Panel</span>
    <h1>Administrator Login</h1>
  </div>

  <c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">
      <i class="fas fa-exclamation-circle"></i> ${errorMessage}
      <button class="close-btn"><i class="fas fa-times"></i></button>
    </div>
  </c:if>

  <form action="${pageContext.request.contextPath}/admin/login" method="post" class="admin-login-form">
    <div class="form-group">
      <label for="username">Username:</label>
      <div class="input-with-icon">
        <i class="fas fa-user"></i>
        <input type="text" id="username" name="username" required>
      </div>
    </div>

    <div class="form-group">
      <label for="password">Password:</label>
      <div class="input-with-icon">
        <i class="fas fa-lock"></i>
        <input type="password" id="password" name="password" required>
      </div>
    </div>

    <button type="submit" class="btn btn-primary">
      <i class="fas fa-sign-in-alt"></i> Login
    </button>
  </form>

  <a href="${pageContext.request.contextPath}/" class="back-link">
    <i class="fas fa-arrow-left"></i> Back to Main Site
  </a>

  <div style="text-align: center; margin-top: 20px; font-size: 0.8em; color: #999;">
    <p>Default admin credentials (if none set): admin / admin123</p>
    <p><a href="${pageContext.request.contextPath}/create-admin" style="color: #999;">Troubleshoot admin access</a></p>
  </div>
</div>

<script>
  // Close alert messages
  document.querySelectorAll('.close-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      btn.parentElement.style.display = 'none';
    });
  });
</script>
</body>
</html>