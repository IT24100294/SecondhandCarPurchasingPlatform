<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="Backend.Buyer.Buyer" %>
<%@ page import="java.util.List" %>
<%
    Buyer buyer = (Buyer) session.getAttribute("currentBuyer");
    List<String> history = (List<String>) session.getAttribute("purchaseHistory");
%>
<html>
<head>
    <title>Buyer Info</title>
    <link href="../css/buyerInfoPage.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Buyer Information</h2>
    <p><strong>Name:</strong> <%= buyer.getName() %></p>
    <p><strong>Email:</strong> <%= buyer.getEmail() %></p>
    <p><strong>Phone:</strong> <%= buyer.getPhone() %></p>

    <h4>Purchase History</h4>
    <% if (history != null && !history.isEmpty()) { %>
    <ul>
        <% for (String record : history) { %>
        <li><%= record %></li>
        <% } %>
    </ul>
    <% } else { %>
    <p>No previous purchases.</p>
    <% } %>
</div>
</body>
</html>
