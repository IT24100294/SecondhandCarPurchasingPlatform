<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="Backend.Buyer.Buyer" %>
<%@ page import="Backend.Buyer.Purchase" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    Buyer buyer = (Buyer) session.getAttribute("currentBuyer");
    List<Purchase> history = (List<Purchase>) session.getAttribute("purchaseHistory");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String updateStatus = request.getParameter("update");
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
        .edit-form {
            margin-top: 20px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f9f9f9;
            display: none; /* Hide form by default */
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-group input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .btn-update {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn-update:hover {
            background-color: #45a049;
        }
        .btn-edit {
            background-color: #2196F3;
            color: white;
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-bottom: 20px;
        }
        .btn-edit:hover {
            background-color: #1976D2;
        }
        .success-message {
            color: #4CAF50;
            margin-bottom: 15px;
            padding: 10px;
            background-color: #e8f5e9;
            border-radius: 4px;
        }
        .buyer-info {
            margin-top: 20px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f9f9f9;
        }
        .info-item {
            margin-bottom: 10px;
            font-size: 1.1em;
        }
        .info-label {
            font-weight: bold;
            color: #2c3e50;
        }
    </style>
</head>
<body>
<div class="container mt-4">
    <h2>Buyer Information</h2>
    
    <% if ("success".equals(updateStatus)) { %>
        <div class="success-message">
            Your information has been updated successfully!
        </div>
    <% } %>

    <div class="buyer-info">
        <div class="info-item">
            <span class="info-label">Name:</span> <%= buyer.getName() %>
        </div>
        <div class="info-item">
            <span class="info-label">Email:</span> <%= buyer.getEmail() %>
        </div>
        <div class="info-item">
            <span class="info-label">Phone:</span> <%= buyer.getPhone() %>
        </div>
        <button class="btn-edit" onclick="toggleEditForm()">Edit Information</button>
    </div>

    <div class="edit-form" id="editForm">
        <h3>Edit Your Information</h3>
        <form action="../UpdateBuyerServlet" method="post">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="<%= buyer.getName() %>" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<%= buyer.getEmail() %>" required>
            </div>
            <div class="form-group">
                <label for="phone">Phone:</label>
                <input type="tel" id="phone" name="phone" value="<%= buyer.getPhone() %>" required>
            </div>
            <button type="submit" class="btn-update">Update Information</button>
        </form>
    </div>

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

<script>
function toggleEditForm() {
    var form = document.getElementById('editForm');
    if (form.style.display === 'none' || form.style.display === '') {
        form.style.display = 'block';
    } else {
        form.style.display = 'none';
    }
}
</script>
</body>
</html>
