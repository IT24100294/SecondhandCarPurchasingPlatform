package Backend.Buyer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/UpdateBuyerServlet")
public class UpdateBuyerServlet extends HttpServlet {
    private static final String BUYER_INFO_FILE = "D:\\UNI\\Y1S2\\OOP\\SecondhandCarPurchasingPlatform\\src\\main\\data\\buyerInfo.txt";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Buyer buyer = (Buyer) session.getAttribute("currentBuyer");
        
        if (buyer == null) {
            response.sendRedirect("Frontend/login.jsp");
            return;
        }

        // Get updated information
        String newName = request.getParameter("name");
        String newEmail = request.getParameter("email");
        String newPhone = request.getParameter("phone");

        // Get the original email to find the entry in the file
        String originalEmail = buyer.getEmail();

        // Update buyer object
        buyer.updateBuyer(newName, newEmail, newPhone);
        session.setAttribute("currentBuyer", buyer);

        // Update the buyerInfo.txt file
        updateBuyerInfoFile(buyer, originalEmail);

        // Redirect back to buyer info page with correct path
        response.sendRedirect("Frontend/buyerInfo.jsp?update=success");
    }

    private void updateBuyerInfoFile(Buyer buyer, String originalEmail) throws IOException {
        List<String> lines = new ArrayList<>();
        boolean foundBuyer = false;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);

        // Read all lines from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(BUYER_INFO_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        // Find and remove the buyer's old entry
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("=== Buyer Entry #")) {
                // Check the next few lines for matching original email
                if (i + 3 < lines.size() && lines.get(i + 3).contains("Email: " + originalEmail)) {
                    // Found the buyer's entry, remove it
                    int endIndex = i;
                    while (endIndex < lines.size() && !lines.get(endIndex).equals("=================")) {
                        endIndex++;
                    }
                    if (endIndex < lines.size()) {
                        endIndex++; // Include the separator line
                    }
                    // Remove the entry
                    for (int j = 0; j < endIndex - i; j++) {
                        lines.remove(i);
                    }
                    foundBuyer = true;
                    break;
                }
            }
        }

//        // Add the new entry at the end
//        int entryNumber = 1;
//        for (String line : lines) {
//            if (line.startsWith("=== Buyer Entry #")) {
//                entryNumber++;
//            }
//        }

        lines.add("=== Buyer Entry #  ===");
        lines.add("Timestamp: " + timestamp);
        lines.add("Name: " + buyer.getName());
        lines.add("Email: " + buyer.getEmail());
        lines.add("Phone: " + buyer.getPhone());
        lines.add("=================");
        lines.add("");

        // Write back to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(BUYER_INFO_FILE))) {
            for (String line : lines) {
                writer.println(line);
            }
        }
    }
} 