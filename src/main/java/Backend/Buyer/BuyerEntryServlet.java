package Backend.Buyer;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class BuyerEntryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        Buyer buyer = new Buyer(name, email, phone);
        BuyerDataStore.addBuyer(buyer);

        HttpSession session = request.getSession();
        session.setAttribute("currentBuyer", buyer);

        response.sendRedirect("Frontend/carListings.jsp");
    }
}