<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Manage Administrators</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <style>
    /* Admin styles */
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
    .admin-table {
      width: 100%;
      border-collapse: collapse;
      background-color: var(--background-card);
      border-radius: var(--radius-lg);
      overflow: hidden;
      box-shadow: 0 2px 8px var(--shadow-color);
      margin-top: 1.5rem;
    }
    .admin-table th, .admin-table td {
      padding: var(--spacing-md);
      text-align: left;
    }
    .admin-table th {
      background-color: #f7f9fc;
      font-weight: 500;
    }
    .admin-table tbody tr {
      border-top: 1px solid var(--border-color);
    }
    .admin-table tbody tr:hover {
      background-color: rgba(0, 0, 0, 0.02);
    }
    .admin-table .status-badge {
      display: inline-flex;
      align-items: center;
      padding: 0.25rem 0.75rem;
      border-radius: 50px;
      font-size: 0.75rem;
      font-weight: 500;
    }
    .admin-table .status-badge.super-admin {
      background-color: rgba(76, 175, 80, 0.1);
      color: var(--success-color);
    }
    .admin-table .status-badge.regular-admin {
      background-color: rgba(33, 150, 243, 0.1);
      color: var(--info-color);
    }
    .admin-actions {
      display: flex;
      gap: 0.5rem;
    }
    .page-title {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 1.5rem;
    }
    .page-title h1 {
      font-size: 1.75rem;
      font-weight: 500;
    }
  </style>
</head>
<body>
<!-- Admin Header -->
<header class="admin-header">
  <div style="display: flex; justify-content: space-between; align-items: center;">
    <div style="display: flex; align-items: center;">
      <i class="fas fa-cogs" style="font-size: 1.5rem; margin-right: 0.5rem;"></i>
      <span style="font-size: 1.25rem; font-weight: 500;">Admin Panel</span>
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

      <c:if test="${sessionScope.isSuperAdmin}">
        <a href="${pageContext.request.contextPath}/admin/view-admins" class="admin-nav-link active">
          <i class="fas fa-users-cog"></i> Manage Administrators
        </a>
        <a href="${pageContext.request.contextPath}/admin/add-admin" class="admin-nav-link">
          <i class="fas fa-user-plus"></i> Add Administrator
        </a>
      </c:if>

      <a href="${pageContext.request.contextPath}/" class="admin-nav-link">
        <i class="fas fa-external-link-alt"></i> View Main Site
      </a>
    </nav>
  </aside>

  <!-- Main Content -->
  <main class="admin-content">
    <!-- Alert Messages -->
    <c:if test="${not empty sessionScope.successMessage}">
      <div class="alert alert-success">
        <i class="fas fa-check-circle"></i> ${sessionScope.successMessage}
        <button class="close-btn"><i class="fas fa-times"></i></button>
      </div>
      <c:remove var="successMessage" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.errorMessage}">
      <div class="alert alert-danger">
        <i class="fas fa-exclamation-circle"></i> ${sessionScope.errorMessage}
        <button class="close-btn"><i class="fas fa-times"></i></button>
      </div>
      <c:remove var="errorMessage" scope="session" />
    </c:if>

    <c:if test="${not empty errorMessage}">
      <div class="alert alert-danger">
        <i class="fas fa-exclamation-circle"></i> ${errorMessage}
        <button class="close-btn"><i class="fas fa-times"></i></button>
      </div>
    </c:if>

    <!-- Page Title -->
    <div class="page-title">
      <h1><i class="fas fa-users-cog"></i> Manage Administrators</h1>
      <a href="${pageContext.request.contextPath}/admin/add-admin" class="btn btn-primary">
        <i class="fas fa-user-plus"></i> Add New Administrator
      </a>
    </div>

    <!-- Admin Table -->
    <div class="table-responsive">
      <c:choose>
        <c:when test="${empty admins || admins.size() == 0}">
          <div style="text-align: center; padding: 2rem; background: #fff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
            <i class="fas fa-users-slash" style="font-size: 3rem; color: #ccc; margin-bottom: 1rem;"></i>
            <h3>No Administrators Found</h3>
            <p style="margin-bottom: 1.5rem;">There are no administrators in the system yet or they couldn't be loaded.</p>
            <a href="${pageContext.request.contextPath}/admin/add-admin" class="btn btn-primary">
              <i class="fas fa-user-plus"></i> Add Administrator
            </a>
          </div>
        </c:when>
        <c:otherwise>
          <table class="admin-table">
            <thead>
            <tr>
              <th>Username</th>
              <th>Full Name</th>
              <th>Email</th>
              <th>Role</th>
              <th>Created Date</th>
              <th>Last Login</th>
              <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${admins}" var="admin">
              <tr>
                <td>${admin.username}</td>
                <td>${admin.fullName}</td>
                <td>${admin.email}</td>
                <td>
                                            <span class="status-badge ${admin.superAdmin ? 'super-admin' : 'regular-admin'}">
                                                ${admin.superAdmin ? 'Super Admin' : 'Admin'}
                                            </span>
                </td>
                <td>
                  <fmt:formatDate value="${admin.createdDate}" pattern="MMM dd, yyyy" />
                </td>
                <td>
                  <c:choose>
                    <c:when test="${empty admin.lastLoginDate}">
                      Never
                    </c:when>
                    <c:otherwise>
                      <fmt:formatDate value="${admin.lastLoginDate}" pattern="MMM dd, yyyy HH:mm" />
                    </c:otherwise>
                  </c:choose>
                </td>
                <td>
                  <div class="admin-actions">
                    <a href="${pageContext.request.contextPath}/admin/edit-admin?adminId=${admin.adminId}"
                       class="btn btn-sm btn-primary" title="Edit">
                      <i class="fas fa-edit"></i>
                    </a>
                    <c:if test="${admin.adminId != sessionScope.adminId}">
                      <a href="${pageContext.request.contextPath}/admin/delete-admin?adminId=${admin.adminId}"
                         class="btn btn-sm btn-danger" title="Delete">
                        <i class="fas fa-trash"></i>
                      </a>
                    </c:if>
                  </div>
                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </c:otherwise>
      </c:choose>
    </div>

    <!-- Debug Info (only visible during development) -->
    <div style="margin-top: 2rem; padding: 1rem; background: #f8f9fa; border-radius: 8px; border: 1px solid #ddd;">
      <h3>Debug Information</h3>
      <p>Total admins: ${admins.size()}</p>
      <p>Admin data:</p>
      <ul>
        <c:forEach items="${admins}" var="admin">
          <li>
              ${admin.username} (ID: ${admin.adminId}) -
            SuperAdmin: ${admin.superAdmin} -
            Created: <fmt:formatDate value="${admin.createdDate}" pattern="yyyy-MM-dd HH:mm:ss" /> -
            Last Login: <fmt:formatDate value="${admin.lastLoginDate}" pattern="yyyy-MM-dd HH:mm:ss" />
          </li>
        </c:forEach>
      </ul>
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