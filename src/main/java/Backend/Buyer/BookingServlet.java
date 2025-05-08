package Backend.Buyer;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BookingServlet")
public class BookingServlet  extends HttpServlet  {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Buyer buyer = (Buyer) session.getAttribute("currentBuyer");
        System.out.println(buyer.getName());

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get form data
        String name = buyer.getName();
        String email = buyer.getEmail();
        String phone = buyer.getPhone();
        String carType = request.getParameter("carModel");;
        String totalPrice = request.getParameter("carPrice");

        System.out.println(carType);

        //create buyer object

        // Here you would typically save to database or process the booking
        // For now, we'll just display a confirmation

        out.println("<html><head><title>Booking Confirmation</title>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../Frontend/css/purchaseConfirm.css\"></head>");
        out.println("<body><div class=\"container\">");
        out.println("<h1>Booking Confirmation</h1>");
        out.println("<p><strong>Name:</strong> " + name + "</p>");
        out.println("<p><strong>Email:</strong> " + email + "</p>");
        out.println("<p><strong>Phone:</strong> " + phone + "</p>");
        out.println("<p><strong>Car Type:</strong> " + carType + "</p>");
        out.println("<p><strong>Total Price:</strong> " + totalPrice + "</p>");
        out.println("<p class=\"success\">Thank you for your booking!</p>");
        out.println("</div></body></html>");
    }
}