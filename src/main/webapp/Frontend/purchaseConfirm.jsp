<%@ page import="Backend.Buyer.Buyer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Buyer buyer = (Buyer) session.getAttribute("currentBuyer");
    String carModel = (String) request.getAttribute("carModel");
    String carPrice = (String) request.getAttribute("carPrice");
    String carType = request.getParameter("carModel");;
    String totalPrice = request.getParameter("carPrice");
%>

<html>
<head>
    <title>Booking Confirmation</title>
    <link rel="stylesheet" type="text/css" href="../css/purchaseConfirm.css">
</head>
<body>
<div class="container">
    <h1>Purchase Confirmation</h1>
    <p><strong>Name:</strong> <%= buyer.getName() %></p>
    <p><strong>Email:</strong> <%= buyer.getEmail() %></p>
    <p><strong>Phone:</strong> <%= buyer.getPhone() %></p>
    <p><strong>Car Model:</strong> <%= carType %></p>
    <p><strong>Price:</strong> <%= totalPrice%></p>
    <p class="success">Thank you for your purchasing!</p>
</div>
</body>
</html>

