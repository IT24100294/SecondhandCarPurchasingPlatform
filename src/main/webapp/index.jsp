<!-- Updated Landing Page -->
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Buy & Sell</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Buy&Sell</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Contact Us</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                        Select Type
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" data-bs-toggle="modal" data-bs-target="#buyerModal">Buy</a></li>
                        <li><a class="dropdown-item" href="#">Sell</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="card">
    <img src="./assets/car-selling.jpg" class="card-img-top" alt="...">
    <div class="card-body">
        <h5 class="card-title">Used Cars</h5>
        <p class="card-text">Rent or buy a pre-owned car at your convenience!</p>
        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#buyerModal">Go to Buyer Module</button>
    </div>
</div>

<!-----------------------------------------Buyer Modal------------------------------------------------------->
<div class="modal fade" id="buyerModal" tabindex="-1" aria-labelledby="buyerModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content" action="BuyerEntryServlet" method="post">
            <div class="modal-header">
                <h5 class="modal-title" id="buyerModalLabel">Enter Buyer Details</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label class="form-label">Name</label>
                    <input type="text" class="form-control" name="name" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">email</label>
                    <input type="text" class="form-control" name="email" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Phone</label>
                    <input type="text" class="form-control" name="phone" required>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary">Continue</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
