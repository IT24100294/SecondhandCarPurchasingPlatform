<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
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
        .admin-dashboard-cards {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }
        .admin-card {
            background-color: var(--background-card);
            border-radius: 12px;
            padding: 1.5rem;
            box-shadow: 0 2px 8px var(--shadow-color);
            display: flex;
            align-items: center;
        }
        .admin-card-icon {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            background-color: rgba(25, 118, 210, 0.1);
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 1rem;
        }
        .admin-card-icon i {
            font-size: 1.5rem;
            color: var(--primary-color);
        }
        .admin-card-content h3 {
            font-size: 0.875rem;
            color: var(--text-secondary);
            margin-bottom: 0.25rem;
        }
        .admin-card-content p {
            font-size: 1.75rem;
            font-weight: 600;
            color: var(--text-primary);
            margin: 0;
        }
        .welcome-section {
            background-color: var(--primary-color);
            color: var(--text-light);
            padding: 2rem;
            border-radius: 12px;
            margin-bottom: 2rem;
            box-shadow: 0 4px 6px var(--shadow-color);
        }
        .welcome-section h2 {
            font-size: 1.5rem;
            margin-bottom: 0.5rem;
        }
        .welcome-section p {
            opacity: 0.8;
            margin-bottom: 1rem;
        }
        .admin-section {
            margin-bottom: 2rem;
        }
        .admin-section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1rem;
        }
        .admin-section-header h2 {
            font-size: 1.25rem;
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
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="admin-nav-link active">
                <i class="fas fa-tachometer-alt"></i> Dashboard
            </a>

            <c:if test="${sessionScope.isSuperAdmin}">
                <a href="${pageContext.request.contextPath}/admin/view-admins" class="admin-nav-link">
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

        <!-- Welcome Section -->
        <section class="welcome-section">
            <h2>Welcome, ${sessionScope.adminFullName}!</h2>
            <p>You're logged in as an administrator. From here, you can manage system users and settings.</p>
            <a href="${pageContext.request.contextPath}/admin/edit-admin?adminId=${sessionScope.adminId}" class="btn btn-outline">
                <i class="fas fa-user-edit"></i> Edit Your Profile
            </a>
        </section>

        <!-- Dashboard Stats -->
        <section class="admin-dashboard-cards">
            <c:if test="${sessionScope.isSuperAdmin}">
                <div class="admin-card">
                    <div class="admin-card-icon">
                        <i class="fas fa-users-cog"></i>
                    </div>
                    <div class="admin-card-content">
                        <h3>Total Administrators</h3>
                        <p>${adminCount}</p>
                    </div>
                </div>
            </c:if>

            <!-- You can add more admin cards here if needed -->
        </section>

        <!-- Quick Actions -->
        <section class="admin-section">
            <div class="admin-section-header">
                <h2>Quick Actions</h2>
            </div>
            <div style="display: flex; gap: 1rem;">
                <c:if test="${sessionScope.isSuperAdmin}">
                    <a href="${pageContext.request.contextPath}/admin/add-admin" class="btn btn-primary">
                        <i class="fas fa-user-plus"></i> Add Administrator
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/view-admins" class="btn btn-primary">
                        <i class="fas fa-users-cog"></i> Manage Administrators
                    </a>
                </c:if>
                <a href="${pageContext.request.contextPath}/admin/edit-admin?adminId=${sessionScope.adminId}" class="btn btn-primary">
                    <i class="fas fa-user-edit"></i> Edit Profile
                </a>
            </div>
        </section>
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