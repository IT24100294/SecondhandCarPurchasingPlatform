<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Second-Hand Car Purchase</title>
    <link rel="stylesheet" href="../css/purchase.css">
</head>
<body>
<div class="container">
    <h1>Buy a Second-Hand Car</h1>
<%--    <form id="purchaseForm" action="../BookingServlet" method="POST">--%>
    <form id="purchaseForm" action="purchaseConfirm.jsp" method="POST">
<%--        <div class="form-group">--%>
<%--            <label for="name">Full Name:</label>--%>
<%--            <input type="text" id="name" name="name" required>--%>
<%--        </div>--%>

<%--        <div class="form-group">--%>
<%--            <label for="email">Email:</label>--%>
<%--            <input type="email" id="email" name="email" required>--%>
<%--        </div>--%>

<%--        <div class="form-group">--%>
<%--            <label for="phone">Phone Number:</label>--%>
<%--            <input type="tel" id="phone" name="phone" required>--%>
<%--        </div>--%>

        <div class="form-group">
            <label for="carModel">Car Model:</label>
            <select id="carModel" name="carModel" required>
                <option value="">Select a car</option>
                <option value="honda_civic_2018">Honda Civic 2018</option>
                <option value="toyota_corolla_2017">Toyota Corolla 2017</option>
                <option value="ford_focus_2016">Ford Focus 2016</option>
                <option value="hyundai_elantra_2019">Hyundai Elantra 2019</option>
                <option value="maruti_swift_2020">Maruti Swift 2020</option>
                <!-- Add more cars as needed -->
            </select>
        </div>

        <div class="form-group">
            <label for="carPrice">Car Price (LKR):</label>
            <input type="text" id="carPrice" name="carPrice">
        </div>

        <div class="form-group">
            <label for="paymentMethod">Payment Method:</label>
            <select id="paymentMethod" name="paymentMethod" required>
                <option value="">Select payment method</option>
                <option value="cash">Cash</option>
                <option value="bank_transfer">Bank Transfer</option>
                <option value="loan">Loan/Finance</option>
            </select>
        </div>

        <button type="submit" class="submit-btn">Purchase Now</button>
    </form>
</div>

<script>
    // Example car prices (you can fetch these from your backend/database)
    const carPrices = {
        'honda_civic_2018': 12000,
        'toyota_corolla_2017': 11000,
        'ford_focus_2016': 9000,
        'hyundai_elantra_2019': 14000,
        'maruti_swift_2020': 8000
    };

    document.addEventListener('DOMContentLoaded', function() {
        const carModelSelect = document.getElementById('carModel');
        const carPriceInput = document.getElementById('carPrice');

        carModelSelect.addEventListener('change', function() {
            const selectedModel = carModelSelect.value;
            if (carPrices[selectedModel]) {
                carPriceInput.value = `$${carPrices[selectedModel]}`;
            } else {
                carPriceInput.value = '';
            }
        });
    });
</script>
</body>
</html>
