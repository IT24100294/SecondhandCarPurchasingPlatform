package Backend.Buyer;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseRequestServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Buyer buyer = (Buyer) session.getAttribute("currentBuyer");

            if (buyer == null) {
                response.sendRedirect("login.jsp?error=sessionExpired");
                return;
            }

            final String fullCarInfo = request.getParameter("car");
            if (fullCarInfo == null || fullCarInfo.isEmpty()) {
                response.sendRedirect("carListings.jsp?error=invalidCar");
                return;
            }

            // Extract car price from the carType string (format: "Car X - $YYYY")
            String carPrice = "";
            final String carType;
            if (fullCarInfo.contains(" - $")) {
                String[] parts = fullCarInfo.split(" - \\$");
                carType = parts[0];
                carPrice = parts[1];
            } else {
                carType = fullCarInfo;
            }

            // Create a new purchase request
            Purchase purchase = new Purchase(
                    buyer.getId(),
                    buyer.getName(),
                    buyer.getPhone(),
                    buyer.getEmail(),
                    carType,
                    carPrice,
                    new Date()
            );

            // Save to datastore
            PurchaseDataStore.addPurchase(purchase);

            // Save to session history with duplicate check
            List<Purchase> history = (List<Purchase>) session.getAttribute("purchaseHistory");
            if (history == null) {
                history = new ArrayList<>();
            }

            // Check if this car has already been requested
            boolean alreadyRequested = history.stream()
                    .anyMatch(p -> p.getCarType().equals(carType));

            if (!alreadyRequested) {
                history.add(purchase);
            }

            session.setAttribute("purchaseHistory", history);

            // Success message
            response.sendRedirect("Frontend/carListings.jsp?success=true");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Frontend/carListings.jsp?error=exception");
        }
    }
}
