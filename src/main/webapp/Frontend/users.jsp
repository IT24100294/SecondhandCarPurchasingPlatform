<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Management</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<nav>
    <h2>Admin Panel</h2>
    <ul>
        <li><a href="dashboard.jsp">Dashboard</a></li>
        <li><a href="users.jsp" class="active">User Management</a></li>
        <li><a href="reports.jsp">Reports</a></li>
    </ul>
</nav>
<div class="main">
    <h1>User Management</h1>
    
    <c:if test="${not empty error}">
        <div class="alert alert-error">
            <p>${error}</p>
        </div>
    </c:if>

    <form action="users" method="post">
        <input type="hidden" name="action" value="add">
        <h3>Add User</h3>
        <input type="text" name="username" placeholder="Username" required>
        <input type="email" name="email" placeholder="Email" required>
        <select name="role">
            <option>Admin</option>
            <option>Seller</option>
            <option>Buyer</option>
        </select>
        <button type="submit">Add User</button>
    </form>

    <table>
        <tr>
            <th>ID</th><th>Username</th><th>Email</th><th>Role</th><th>Actions</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td>
                    <form action="users" method="post" style="display: inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="userId" value="${user.id}">
                        <button type="submit" onclick="return confirm('Are you sure you want to delete this user?')">Delete</button>
                    </form>
                    <button onclick="editUser('${user.id}', '${user.username}', '${user.email}', '${user.role}')">Edit</button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<script>
function editUser(id, username, email, role) {
    // Create and show edit form
    const form = document.createElement('form');
    form.action = 'users';
    form.method = 'post';
    form.innerHTML = `
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="userId" value="${id}">
        <input type="text" name="username" value="${username}" required>
        <input type="email" name="email" value="${email}" required>
        <select name="role">
            <option ${role === 'Admin' ? 'selected' : ''}>Admin</option>
            <option ${role === 'Seller' ? 'selected' : ''}>Seller</option>
            <option ${role === 'Buyer' ? 'selected' : ''}>Buyer</option>
        </select>
        <button type="submit">Update</button>
        <button type="button" onclick="this.parentElement.remove()">Cancel</button>
    `;
    
    const row = document.querySelector(`tr:has(input[value="${id}"])`);
    row.appendChild(form);
}
</script>
</body>
</html>

