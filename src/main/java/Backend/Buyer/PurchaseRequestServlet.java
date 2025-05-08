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

            String carType = request.getParameter("car");
            if (carType == null || carType.isEmpty()) {
                response.sendRedirect("carListings.jsp?error=invalidCar");
                return;
            }

            // Create a new purchase request
            Purchase purchase = new Purchase(
                    buyer.getId(),
                    buyer.getName(),
                    buyer.getPhone(),
                    buyer.getEmail(),
                    carType,
                    new Date()
            );

            // Save to datastore
            PurchaseDataStore.addPurchase(purchase);

            // Save to session history with duplicate check
            List<String> history = (List<String>) session.getAttribute("purchaseHistory");
            if (history == null) {
                history = new ArrayList<>();
            }

            // ✅ Check if this car has already been requested
            // Extract car name (e.g., "Car 1") from full carType string
            String carName = carType.split(" - ")[0]; // "Car 1"

            boolean alreadyRequested = history.stream().anyMatch(s -> s.contains(carName + " - $"));
            if (!alreadyRequested) {
                history.add("Requested: " + carType + " on " + purchase.getPurchaseDate());
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
