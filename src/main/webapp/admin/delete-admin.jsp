<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>CarCare - Delete Administrator</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <style>
    /* Admin styles copied from dashboard.jsp */
    .admin-header {
      background-color: var(--background-dark);
      color: var(--text-light);
      padding: 1rem 2rem;
      box-shadow: 0 2px 5px var(--shadow-color);
    }
    .admin-container {
      display: flex;
      min-height: calc(100vh - 60px);
    }
    .admin-sidebar {
      width: 250px;
      background-color: #263238;
      color: var(--text-light);
      padding: 2rem 0;
    }
    .admin-content {
      flex: 1;
      padding: 2rem;
      background-color: var(--background-main);
    }
    .admin-nav-link {
      display: flex;
      align-items: center;
      padding: 0.75rem 1.5rem;
      color: rgba(255, 255, 255, 0.7);
      text-decoration: none;
      border-left: 3px solid transparent;
      transition: var(--transition-fast);
    }
    .admin-nav-link:hover, .admin-nav-link.active {
      background-color: rgba(255, 255, 255, 0.1);
      color: var(--text-light);
      border-left-color: var(--primary-color);
    }
    .admin-nav-link i {
      margin-right: 0.75rem;
      width: 20px;
      text-align: center;
    }
    .confirmation-card {
      max-width: 600px;
      margin: 0 auto;
      background-color: var(--background-card);
      border-radius: var(--radius-lg);
      overflow: hidden;
      box-shadow: 0 2px 8px var(--shadow-color);
    }
    .confirmation-header {
      display: flex;
      align-items: center;
      padding: var(--spacing-lg);
      background-color: #fff4f4;
      border-bottom: 1px solid var(--border-color);
    }
    .warning-icon {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      background-color: rgba(244, 67, 54, 0.1);
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: var(--spacing-md);
      flex-shrink: 0;
    }
    .warning-icon i {
      font-size: 1.5rem;
      color: var(--danger-color);
    }
    .confirmation-header h2 {
      font-size: 1.5rem;
      font-weight: 500;
      color: var(--danger-color);
    }
    .confirmation-body {
      padding: var(--spacing-lg);
    }
    .warning-text {
      font-size: 1rem;
      color: var(--danger-color);
      margin-bottom: var(--spacing-lg);
      padding: var(--spacing-md);
      background-color: rgba(244, 67, 54, 0.05);
      border-radius: var(--radius-md);
      border-left: 3px solid var(--danger-color);
    }
    .admin-details {
      margin-bottom: var(--spacing-lg);
    }
    .detail-row {
      display: flex;
      padding: var(--spacing-sm) 0;
      border-bottom: 1px solid var(--border-color);
    }
    .detail-row:last-child {
      border-bottom: none;
    }
    .detail-label {
      width: 150px;
      font-weight: 500;
      color: var(--text-secondary);
    }
    .detail-value {
      flex: 1;
    }
    .confirmation-actions {
      display: flex;
      justify-content: flex-end;
      gap: var(--spacing-md);
      padding: var(--spacing-lg);
      background-color: #f7f9fc;
      border-top: 1px solid var(--border-color);
    }
  </style>
</head>
<body>
<!-- Admin Header -->
<header class="admin-header">
  <div style="display: flex; justify-content: space-between; align-items: center;">
    <div style="display: flex; align-items: center;">
      <i class="fas fa-car" style="font-size: 1.5rem; margin-right: 0.5rem;"></i>
      <span style="font-size: 1.25rem; font-weight: 500;">CarCare Admin</span>
    </div>
    <div>
      <span style="margin-right: 1rem;">Welcome, ${sessionScope.adminFullName}</span>
      <a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-outline btn-sm">
        <i class="fas fa-sign-out-alt"></i> Logout
      </a>
    </div>
  </div>
</header>

<!-- Admin Container -->
<div class="admin-container">
  <!-- Sidebar -->
  <aside class="admin-sidebar">
    <nav>
      <a href="${pageContext.request.contextPath}/admin/dashboard" class="admin-nav-link">
        <i class="fas fa-tachometer-alt"></i> Dashboard
      </a>
      <a href="${pageContext.request.contextPath}/service-history" class="admin-nav-link">
        <i class="fas fa-history"></i> Service History
      </a>
      <a href="${pageContext.request.contextPath}/upcoming-maintenance" class="admin-nav-link">
        <i class="fas fa-calendar-alt"></i> Upcoming Maintenance
      </a>

      <c:if test="${sessionScope.isSuperAdmin}">
        <a href="${pageContext.request.contextPath}/admin/view-admins" class="admin-nav-link active">
          <i class="fas fa-users-cog"></i> Manage Administrators
        </a>
        <a href="${pageContext.request.contextPath}/admin/add-admin" class="admin-nav-link">
          <i class="fas fa-user-plus"></i> Add Administrator
        </a>
      </c:if>

      <a href="${pageContext.request.contextPath}/" class="admin-nav-link">
        <i class="fas fa-external-link-alt"></i> View Public Site
      </a>
    </nav>
  </aside>

  <!-- Main Content -->
  <main class="admin-content">
    <!-- Alert Messages -->
    <c:if test="${not empty errorMessage}">
      <div class="alert alert-danger">
        <i class="fas fa-exclamation-circle"></i> ${errorMessage}
        <button class="close-btn"><i class="fas fa-times"></i></button>
      </div>
    </c:if>

    <!-- Delete Confirmation Card -->
    <div class="confirmation-card">
      <div class="confirmation-header">
        <div class="warning-icon">
          <i class="fas fa-exclamation-triangle"></i>
        </div>
        <h2>Delete Administrator?</h2>
      </div>

      <div class="confirmation-body">
        <p class="warning-text">This action cannot be undone. Are you sure you want to delete this administrator account?</p>

        <div class="admin-details">
          <div class="detail-row">
            <span class="detail-label">Username:</span>
            <span class="detail-value">${admin.username}</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">Full Name:</span>
            <span class="detail-value">${admin.fullName}</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">Email:</span>
            <span class="detail-value">${admin.email}</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">Role:</span>
            <span class="detail-value">
                                <span class="status-badge ${admin.superAdmin ? 'super-admin' : 'regular-admin'}">
                                  ${admin.superAdmin ? 'Super Admin' : 'Admin'}
                                </span>
                            </span>
          </div>
          <div class="detail-row">
            <span class="detail-label">Created Date:</span>
            <span class="detail-value">
                                <fmt:formatDate value="${admin.createdDate}" pattern="MMMM d, yyyy" />
                            </span>
          </div>
        </div>
      </div>

      <div class="confirmation-actions">
        <form action="${pageContext.request.contextPath}/admin/delete-admin" method="post">
          <input type="hidden" name="adminId" value="${admin.adminId}">
          <input type="hidden" name="confirmDelete" value="yes">

          <div style="display: flex; gap: 1rem;">
            <button type="submit" class="btn btn-danger">
              <i class="fas fa-trash"></i> Delete Administrator
            </button>
            <a href="${pageContext.request.contextPath}/admin/view-admins" class="btn btn-outline">
              <i class="fas fa-times"></i> Cancel
            </a>
          </div>
        </form>
      </div>
    </div>
  </main>
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
