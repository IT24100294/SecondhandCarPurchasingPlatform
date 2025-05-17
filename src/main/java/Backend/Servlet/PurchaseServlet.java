package Backend.Servlet;

import Backend.Buyer.Buyer;
import Backend.Buyer.Purchase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Buyer buyer = (Buyer) session.getAttribute("currentBuyer");
        
        if (buyer == null) {
            response.sendRedirect("login.jsp");
            return;
        }

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
            new Date()
        );

        // Get existing purchase history or create new list
        List<Purchase> purchaseHistory = (List<Purchase>) session.getAttribute("purchaseHistory");
        if (purchaseHistory == null) {
            purchaseHistory = new ArrayList<>();
        }

        // Add new purchase to history
        purchaseHistory.add(purchase);
        session.setAttribute("purchaseHistory", purchaseHistory);

        // Redirect to confirmation page
        response.sendRedirect("purchaseConfirm.jsp");
    }
} 