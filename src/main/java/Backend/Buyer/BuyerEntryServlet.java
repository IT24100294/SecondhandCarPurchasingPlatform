package Backend.Buyer;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BuyerEntryServlet extends HttpServlet {
    private static final String BUYER_INFO_FILE = "D:\\UNI\\Y1S2\\OOP\\SecondhandCarPurchasingPlatform\\src\\main\\data\\buyerInfo.txt";
    private static final String COUNTER_FILE = "D:\\UNI\\Y1S2\\OOP\\SecondhandCarPurchasingPlatform\\src\\main\\data\\buyerCounter.txt";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        Buyer buyer = new Buyer(name, email, phone);
        BuyerDataStore.addBuyer(buyer);

        // Read and update buyer counter
        int buyerCount = 1; // Default to 1 if file doesn't exist
        try (BufferedReader reader = new BufferedReader(new FileReader(COUNTER_FILE))) {
            String countStr = reader.readLine();
            if (countStr != null) {
                buyerCount = Integer.parseInt(countStr.trim()) + 1;
            }
        } catch (IOException | NumberFormatException e) {
            // If file doesn't exist or can't read, start with 1
        }

        // Update counter file
        try (PrintWriter counterWriter = new PrintWriter(new FileWriter(COUNTER_FILE))) {
            counterWriter.println(buyerCount);
        }

        // Save buyer information to file
        try (FileWriter fw = new FileWriter(BUYER_INFO_FILE, true);
             PrintWriter writer = new PrintWriter(fw)) {

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = now.format(formatter);

            writer.println("=== Buyer Entry #" + buyerCount + " ===");
            writer.println("Timestamp: " + timestamp);
            writer.println("Name: " + name);
            writer.println("Email: " + email);
            writer.println("Phone: " + phone);
            writer.println("=================");
            writer.println();

            System.out.println("New buyer added. Total buyers: " + buyerCount);
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Phone: " + phone);
        }

        HttpSession session = request.getSession();
        session.setAttribute("currentBuyer", buyer);

        response.sendRedirect("Frontend/carListings.jsp");
    }
}