<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="Backend.Buyer.Buyer" %>
<%@ page import="Backend.Buyer.Purchase" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    Buyer buyer = (Buyer) session.getAttribute("currentBuyer");
    List<Purchase> history = (List<Purchase>) session.getAttribute("purchaseHistory");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<html>
<head>
    <title>Buyer Info</title>
    <link href="../css/buyerInfoPage.css" rel="stylesheet">
    <style>
        .purchase-history {
            margin-top: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            background-color: #f9f9f9;
        }
        .purchase-item {
            border-bottom: 1px solid #eee;
            padding: 10px 0;
        }
        .purchase-item:last-child {
            border-bottom: none;
        }
        .purchase-date {
            color: #666;
            font-size: 0.9em;
        }
        .car-details {
            font-weight: bold;
            color: #2c3e50;
        }
        .car-price {
            color: #27ae60;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container mt-4">
    <h2>Buyer Information</h2>
    <p><strong>Name:</strong> <%= buyer.getName() %></p>
    <p><strong>Email:</strong> <%= buyer.getEmail() %></p>
    <p><strong>Phone:</strong> <%= buyer.getPhone() %></p>

    <div class="purchase-history">
        <h4>Purchase History</h4>
        <% if (history != null && !history.isEmpty()) { %>
        <% for (Purchase purchase : history) { %>
        <div class="purchase-item">
            <div class="car-details">
                <%= purchase.getCarType() %>
                <% if (purchase.getCarPrice() != null && !purchase.getCarPrice().isEmpty()) { %>
                <span class="car-price">- $<%= purchase.getCarPrice() %></span>
                <% } %>
            </div>
            <div class="purchase-date">Purchased on: <%= dateFormat.format(purchase.getPurchaseDate()) %></div>
        </div>
        <% } %>
        <% } else { %>
        <p>No previous purchases.</p>
        <% } %>
    </div>
</div>
</body>
</html>
