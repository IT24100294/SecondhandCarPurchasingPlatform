
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="Backend.Buyer.Buyer" %>
<%
    Buyer buyer = (Buyer) session.getAttribute("selectedBuyer");
%>
<html>
<head>
    <title>Update Buyer</title>
    <link rel="stylesheet" href="../css/buyer-style.css" />
</head>
<body>
<h2>Update Buyer</h2>
<form action="BuyerServlet" method="post">
    <input type="hidden" name="action" value="update" />
    <input type="hidden" name="id" value="<%= buyer.getId() %>" />
    Name: <input type="text" name="name" value="<%= buyer.getName() %>" required /><br/>
    Email: <input type="email" name="email" value="<%= buyer.getEmail() %>" required /><br/>
    Phone: <input type="text" name="phone" value="<%= buyer.getPhone() %>" required /><br/>
    <input type="submit" value="Update Buyer" />
</form>
<a href="viewBuyers.jsp">Cancel</a>
</body>
</html>
