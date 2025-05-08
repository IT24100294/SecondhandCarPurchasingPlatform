
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="Backend.Buyer.Buyer" %>
<%@ page import="Backend.Buyer.BuyerDataStore" %>
<%
    List<Buyer> buyers = BuyerDataStore.getAllBuyers();
%>
<html>
<head>
    <title>View Buyers</title>
    <link rel="stylesheet" href="../css/buyer-style.css">
</head>
<body>
<h2>All Buyers</h2>
<a href="addBuyer.jsp">Add New Buyer</a><br/><br/>
<table border="1">
    <tr>
        <th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Actions</th>
    </tr>
    <% for (Buyer b : buyers) { %>
    <tr>
        <td><%= b.getId() %></td>
        <td><%= b.getName() %></td>
        <td><%= b.getEmail() %></td>
        <td><%= b.getPhone() %></td>
        <td>
            <form action="../BuyerServlet" method="post" style="display:inline;">
                <input type="hidden" name="action" value="edit" />
                <input type="hidden" name="id" value="<%= b.getId() %>" />
                <input type="submit" value="Edit" />
            </form>
            <form action="../BuyerServlet" method="post" style="display:inline;">
                <input type="hidden" name="action" value="delete" />
                <input type="hidden" name="id" value="<%= b.getId() %>" />
                <input type="submit" value="Delete" />
            </form>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>

