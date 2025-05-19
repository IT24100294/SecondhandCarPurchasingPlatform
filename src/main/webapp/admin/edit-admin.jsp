<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CarCare - Edit Administrator</title>
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
        .admin-form-card {
            background-color: var(--background-card);
            border-radius: 12px;
            padding: 2rem;
            box-shadow: 0 2px 8px var(--shadow-color);
            max-width: 800px;
            margin: 0 auto;
        }
        .page-title {
            margin-bottom: 1.5rem;
        }
        .page-title h1 {
            font-size: 1.75rem;
            font-weight: 500;
            display: flex;
            align-items: center;
        }
        .page-title h1 i {
            margin-right: 0.5rem;
        }
        .form-section {
            margin-bottom: 2rem;
        }
        .form-section h2 {
            font-size: 1.25rem;
            font-weight: 500;
            margin-bottom: 1rem;
            padding-bottom: 0.5rem;
            border-bottom: 1px solid var(--border-color);
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
                <a href="${pageContext.request.contextPath}/admin/view-admins" class="admin-nav-link ${!editingSelf ? 'active' : ''}">
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

        <!-- Page Title -->
        <div class="page-title">
            <h1>
                <i class="fas fa-user-edit"></i>
                <c:choose>
                    <c:when test="${editingSelf}">Edit Your Profile</c:when>
                    <c:otherwise>Edit Administrator</c:otherwise>
                </c:choose>
            </h1>
        </div>

        <!-- Edit Admin Form -->
        <div class="admin-form-card">
            <form action="${pageContext.request.contextPath}/admin/edit-admin" method="post">
                <input type="hidden" name="adminId" value="${admin.adminId}">

                <div class="form-section">
                    <h2>Account Information</h2>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="text" id="username" name="username" value="${admin.username}" required>
                            <small>Username must be unique and will be used for login</small>
                        </div>
                        <div class="form-group">
                            <label for="email">Email Address:</label>
                            <input type="email" id="email" name="email" value="${admin.email}" required>
                        </div>
                    </div>
                </div>

                <div class="form-section">
                    <h2>Personal Information</h2>
                    <div class="form-group">
                        <label for="fullName">Full Name:</label>
                        <input type="text" id="fullName" name="fullName" value="${admin.fullName}" required>
                    </div>
                </div>

                <div class="form-section">
                    <h2>Security</h2>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="password">New Password:</label>
                            <input type="password" id="password" name="password">
                            <small>Leave blank to keep current password</small>
                        </div>
                        <div class="form-group">
                            <label for="confirmPassword">Confirm New Password:</label>
                            <input type="password" id="confirmPassword" name="confirmPassword">
                        </div>
                    </div>
                </div>

                <c:if test="${sessionScope.isSuperAdmin && !editingSelf}">
                    <div class="form-section">
                        <h2>Permissions</h2>
                        <div class="form-group checkbox-group">
                            <label class="checkbox-container">
                                <input type="checkbox" id="superAdmin" name="superAdmin" ${admin.superAdmin ? 'checked' : ''}>
                                <span class="checkmark"></span>
                                Super Administrator
                            </label>
                            <small>Super administrators can manage other admin accounts and have full access to all features</small>
                        </div>
                    </div>
                </c:if>

                <div class="form-actions" style="display: flex; gap: 1rem;">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i> Save Changes
                    </button>
                    <c:choose>
                        <c:when test="${editingSelf}">
                            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-outline">
                                <i class="fas fa-times"></i> Cancel
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/admin/view-admins" class="btn btn-outline">
                                <i class="fas fa-times"></i> Cancel
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </form>
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

    // Password match validation
    document.querySelector('form').addEventListener('submit', function(e) {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        if (password && password !== confirmPassword) {
            e.preventDefault();
            alert('Passwords do not match!');
        }
    });
</script>
</body>
</html>
