<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Management</title>
    <link rel="stylesheet" href="/PG197/css/style.css">
</head>
<body>
<nav>
    <h2>Admin Panel</h2>
    <ul>
        <li><a href="/PG197/Frontend/dashboard.jsp">Dashboard</a></li>
        <li><a href="/PG197/Frontend/users.jsp" class="active">User Management</a></li>
        <li><a href="/PG197/Frontend/reports.jsp">Reports</a></li>
    </ul>
</nav>
<div class="main">
    <h1>User Management</h1>
    <form action="UserServlet" method="post">
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
        <%-- Example static rows; replace with dynamic rows as needed --%>
        <tr>
            <td>1</td><td>admin</td><td>admin@example.com</td><td>Admin</td>
            <td>
                <button>Edit</button>
                <button>Delete</button>
            </td>
        </tr>
        <tr>
            <td>2</td><td>johndoe</td><td>john@example.com</td><td>Buyer</td>
            <td>
                <button>Edit</button>
                <button>Delete</button>
            </td>
        </tr>
        <tr>
            <td>3</td><td>janesmith</td><td>jane@example.com</td><td>Seller</td>
            <td>
                <button>Edit</button>
                <button>Delete</button>
            </td>
        </tr>
        <%-- For dynamic rows, use JSTL or scriptlets to iterate over a user list --%>
    </table>
</div>
</body>
</html>

