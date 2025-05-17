<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="Backend.Buyer.Buyer" %>
<%@ page import="Backend.Buyer.Purchase" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%
    Buyer buyer = (Buyer) session.getAttribute("currentBuyer");
    
    // Get form data
    String carModel = request.getParameter("carModel");
    String carPrice = request.getParameter("carPrice");
    String paymentMethod = request.getParameter("paymentMethod");
    
    // Create new purchase
    Purchase purchase = new Purchase(
        buyer.getId(),
        buyer.getName(),
        buyer.getPhone(),
        buyer.getEmail(),
        carModel,
        carPrice,
        new java.util.Date()
    );
    
    // Get existing purchase history or create new list
    List<Purchase> purchaseHistory = (List<Purchase>) session.getAttribute("purchaseHistory");
    if (purchaseHistory == null) {
        purchaseHistory = new ArrayList<>();
    }
    
    // Add new purchase to history
    purchaseHistory.add(purchase);
    session.setAttribute("purchaseHistory", purchaseHistory);
    
    // Get the latest purchase for display
    Purchase latestPurchase = purchase;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Purchase Confirmation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .success-message {
            color: #27ae60;
            font-size: 24px;
            margin-bottom: 20px;
        }
        .details {
            margin: 20px 0;
            padding: 15px;
            background: #f9f9f9;
            border-radius: 5px;
        }
        .details p {
            margin: 10px 0;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            background: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-top: 20px;
        }
        .button:hover {
            background: #2980b9;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="success-message">
            ✓ Purchase Successful!
        </div>
        
        <div class="details">
            <h3>Purchase Details:</h3>
            <p><strong>Car Model:</strong> <%= latestPurchase.getCarType() %></p>
            <p><strong>Price:</strong> <%= latestPurchase.getCarPrice() %></p>
            <p><strong>Purchase Date:</strong> <%= latestPurchase.getPurchaseDate() %></p>
            <p><strong>Payment Method:</strong> <%= paymentMethod %></p>
            <p><strong>Buyer Name:</strong> <%= latestPurchase.getBuyerName() %></p>
            <p><strong>Email:</strong> <%= latestPurchase.getBuyerEmail() %></p>
            <p><strong>Phone:</strong> <%= latestPurchase.getBuyerPhone() %></p>
        </div>

        <a href="buyerInfo.jsp" class="button">View Purchase History</a>
        <a href="purchase.jsp" class="button" style="margin-left: 10px;">Make Another Purchase</a>
    </div>
</body>
</html>

