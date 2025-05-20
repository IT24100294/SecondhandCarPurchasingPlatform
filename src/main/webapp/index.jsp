<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Buy & Sell</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        .carousel-item img {
            height: 700px;
            object-fit: cover;
        }
        .img-fixed {
            height: 200px; /* Adjust as needed */
            object-fit: cover;
        }
        .card-text {
            display: -webkit-box;
            -webkit-line-clamp: 2; /* limits to 2 lines */
            -webkit-box-orient: vertical;
            overflow: hidden;
            text-overflow: ellipsis;
            font-size: 0.9rem; /* optional: adjust size */
            max-height: 3em; /* optional: control height */
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold fst-itali" href="#" style="color: #e78400;">Buy & Sell</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#" id="concat_us">Contact Us</a>
                    <script>
                        document.getElementById("concat_us").addEventListener("click", function(event) {
                            event.preventDefault(); // prevent the default anchor behavior
                            window.location.href = "./frontEnd/pages/contact_us.jsp";
                        });
                    </script>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Select Type
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#">Buy</a></li>
                        <li><a class="dropdown-item" href="#" id="sell-link">Sell</a></li>

                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#">Something else here</a></li>
                    </ul>
                    <script>
                        document.getElementById("sell-link").addEventListener("click", function(event) {
                            event.preventDefault(); // prevent the default anchor behavior
                            window.location.href = "./frontEnd/pages/SellingPage.jsp";
                        });
                    </script>

                </li>
            </ul>
            <form class="d-flex" role="search">
                <% if (session.getAttribute("userEmail") != null) { %>
                    <span class="me-3">Welcome, <%= session.getAttribute("userName") %></span>
                    <a href="<%=request.getContextPath()%>/UserProfile" class="btn btn-outline-primary me-2">Profile</a>
                    <a href="<%=request.getContextPath()%>/Logout" class="btn btn-outline-danger">Logout</a>
                <% } else { %>
                    <button class="btn btn-outline-danger me-2" type="submit" id="login">Login</button>
                    <script>
                        document.getElementById("login").addEventListener("click", function(event) {
                            event.preventDefault();
                            window.location.href = "./frontEnd/pages/login.jsp";
                        });
                    </script>
                    <button class="btn btn-outline-danger" type="button" id="signup">Signup</button>
                    <script>
                        document.getElementById("signup").addEventListener("click", function(event) {
                            event.preventDefault();
                            window.location.href = "./frontEnd/pages/Register.jsp";
                        });
                    </script>
                <% } %>
            </form>
        </div>
    </div>
</nav>
<div id="mainCarousel" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-indicators">
        <button type="button" data-bs-target="#mainCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#mainCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#mainCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
    </div>

    <div class="carousel-inner">
        <div class="carousel-item active">
            <img src="./assets/carousel_1.jpg" class="d-block w-100" alt="First slide">
            <div class="carousel-caption d-none d-md-block">
                <h5 style="font-size: 3rem;">Sell your car FAST for FREE!</h5>
                <p style="font-size: 1rem;">Reach thousands of buyers in just a few clicks.</p>
            </div>
        </div>
        <div class="carousel-item">
            <img src="./assets/carousel_2.jpg" class="d-block w-100" alt="Second slide">
            <div class="carousel-caption d-none d-md-block">
                <h5 style="font-size: 3rem;">Wide Range of Cars</h5>
                <p style="font-size: 1rem;">Explore luxury and budget-friendly options.</p>
            </div>
        </div>
        <div class="carousel-item">
            <img src="./assets/carousel_3.jpg" class="d-block w-100" alt="Third slide">
            <div class="carousel-caption d-none d-md-block">
                <h5 style="font-size: 3rem;">Trustworthy Sellers</h5>
                <p style="font-size: 1rem;">Verified listings from real people.</p>
            </div>
        </div>
    </div>

    <button class="carousel-control-prev" type="button" data-bs-target="#mainCarousel" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#mainCarousel" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>

<%--Cards--%>
<%--<div class="card">--%>
<%--    <img src="./assets/car-selling.jpg" class="card-img-top" alt="...">--%>

<%--    <div class="card-body">--%>
<%--        <h5 class="card-title">Card title</h5>--%>
<%--        <p class="card-text">Rent a Pre-Owned Car Today!</p>--%>
<%--        <a href="#" class="btn btn-primary">Go somewhere</a>--%>
<%--    </div>--%>
<%--</div>--%>


<div class="container mt-4 ">
    <h5 class="fw-bold mb-3 text-center" style="font-size: 2.5rem;">Featured Ads</h5>
    <h5 class="fw-bold mb-3 text-center" style="font-size: 1rem;"> Be amazed by what is on offer-choose from a pool of vehicles that are brand new, reconditioned, or DPMC certified</h5>

    <div class="row">
        <div class="col-md-3 d-flex">
            <div class="card h-100 d-flex flex-column">
                <img src="./assets/McLaren%20720S.jpg" class="card-img-top img-fixed" alt="...">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">McLaren 20720S</h5>
                    <p class="card-text">Unleash elite performance with the stunning McLaren 720S &mdash; pure supercar thrill.</p>
                    <a href="#" class="btn btn-primary">View</a>
                </div>
            </div>
        </div>

        <div class="col-md-3 d-flex">
            <div class="card h-100 d-flex flex-column">
                <img src="./assets/nissan-gt-r.jpg" class="card-img-top img-fixed" alt="...">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">Nissan GT-R</h5>
                    <p class="card-text">Legendary power meets precision. Own the iconic Nissan GT-R today.</p>
                    <a href="#" class="btn btn-primary">View</a>
                </div>
            </div>
        </div>

        <div class="col-md-3 d-flex">
            <div class="card h-100 d-flex flex-column">
                <img src="./assets/Toyota%20Premio.jpg" class="card-img-top img-fixed" alt="...">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">Toyota Premio</h5>
                    <p class="card-text">Comfort and reliability in one sleek package  &mdash; drive the trusted Toyota Premio.</p>
                    <a href="#" class="btn btn-primary">View</a>
                </div>
            </div>
        </div>

        <div class="col-md-3 d-flex">
            <div class="card h-100 d-flex flex-column">
                <img src="./assets/BMW%203%20Series%20Gran%20Turismo.jpg" class="card-img-top img-fixed" alt="...">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">BMW 3 Series Gran Turismo</h5>
                    <p class="card-text">Luxury, space, and performance&mdash;BMW&rsquo;s 3 Series GT redefines your journey.</p>
                    <a href="#" class="btn btn-primary">View</a>
                </div>
            </div>
        </div>
    </div>

    <div class="row mt-3">
        <div class="col-md-3 d-flex">
            <div class="card h-100 d-flex flex-column">
                <img src="./assets/honda-fit.jpg" class="card-img-top img-fixed" alt="...">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">Honda Fit</h5>
                    <p class="card-text">Compact yet spacious. The Honda Fit is perfect for city life and weekend trips.</p>
                    <a href="#" class="btn btn-primary">View</a>
                </div>
            </div>
        </div>

        <div class="col-md-3 d-flex">
            <div class="card h-100 d-flex flex-column">
                <img src="./assets/Maruti%20Suzuki%20Swift.jpg" class="card-img-top img-fixed" alt="...">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">Maruti Suzuki Swift</h5>
                    <p class="card-text">Sporty, efficient, and fun to drive  &mdash; experience the Maruti Suzuki Swift.</p>
                    <a href="#" class="btn btn-primary">View</a>
                </div>
            </div>
        </div>

        <div class="col-md-3 d-flex">
            <div class="card h-100 d-flex flex-column">
                <img src="./assets/porsche-911.jpg" class="card-img-top img-fixed" alt="...">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">Porsche 911</h5>
                    <p class="card-text">A timeless icon of speed and style  &mdash; discover the Porsche 911 experience.</p>
                    <a href="#" class="btn btn-primary">View</a>
                </div>
            </div>
        </div>

        <div class="col-md-3 d-flex">
            <div class="card h-100 d-flex flex-column">
                <img src="./assets/mazda.jpg" class="card-img-top img-fixed" alt="...">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">Mazda</h5>
                    <p class="card-text">Smooth, stylish, and reliable  &mdash; the Mazda is built for everyday excitement.</p>
                    <a href="#" class="btn btn-primary">View</a>
                </div>
            </div>
        </div>
    </div>
</div>

<footer class="bg-light py-4 mt-5">
    <div class="container text-center">
        <p>&copy; 2025 Buy &amp; Sell. All rights reserved.</p>
        <p class="mb-1">Buy&Sell offers an unparalleled experience in the automobile trading industry. With its unmatched transparency, quality, value for money - Buy&Sell is the place to buy, sell or exchange your vehicle.</p>
        <p class="mb-0">Contact us: <a href="mailto:support@buyandsell.com">support@buyandsell.com</a></p>
    </div>
</footer>

<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>