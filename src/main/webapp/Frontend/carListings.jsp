<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="Backend.Buyer.Buyer" %>
<%
    Buyer buyer = (Buyer) session.getAttribute("currentBuyer");
    String message = (String) request.getAttribute("message");
%>
<html>
<head>
    <title>Car Listings</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .buyerinfo {
            position: absolute;
            top: 20px;
            right: 40px;
        }
        .buyerinfoIMG {
            width: 50px;
            height: 50px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<%--//popup mesage here--%>
<% if (message != null) { %>
<div id="popup" class="popup">
    <link rel="stylesheet" href="../css/popup.css"/>
    <span class="close" onclick="document.getElementById('popup').style.display='none'">&times;</span>
    <%= message %>
</div>
<script>
    setTimeout(() => {
        document.getElementById('popup').style.display = 'none';
    }, 3000);
</script>
<% } %>

<div class="container mt-5">
    <h2 class="mb-4">Available Cars</h2>

    <!-- Buyer Info Icon -->
    <div class="buyerinfo">
        <a href="buyerInfo.jsp">
            <img src="../assets/buyerProfileIMG.png" class="buyerinfoIMG" alt="Buyer Info">
        </a>
    </div>

    <div class="row">
        <% for (int i = 1; i <= 6; i++) { %>
        <div class="col-md-4 d-flex justify-content-center">
            <div class="card mb-4" style="width: 18rem;">
                <img src="../assets/car<%= i %>.jpg" class="card-img-top" alt="Car <%= i %>">
                <div class="card-body">
                    <h5 class="card-title">Car <%= i %></h5>
                    <p class="card-text">Great condition. Price: $<%= 5000 + (i * 1000) %></p>

                    <!-- Purchase Request Form -->
                    <form action="purchase.jsp" method="get">

                        <!---------------------------------------------------------->
                        <input type="hidden" name="buyerId" value="<%= buyer.getId() %>">
                        <input type="hidden" name="car" value="Car <%= i %> - $<%= 5000 + (i * 1000) %>">
                        <button type="submit" class="btn btn-success mt-2">Purchase Request</button>
                    </form>
                </div>
            </div>
        </div>
        <% } %>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
