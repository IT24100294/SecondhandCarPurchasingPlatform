import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.carservice.model.admin.Admin;
import com.carservice.model.admin.AdminManager;

/**
 * Servlet for manually creating an admin account for debugging
 */
@WebServlet("/create-admin")
public class ManualAdminCreator extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests - Create a default admin account
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Create AdminManager
            AdminManager adminManager = new AdminManager(getServletContext());

            // Check existing admins
            out.println("<html><body>");
            out.println("<h1>Admin Account Creator</h1>");

            if (adminManager.hasAdmins()) {
                out.println("<p>Existing admins found. Current admin count: " + adminManager.getAllAdmins().size() + "</p>");
                out.println("<h2>Current Admins:</h2>");
                out.println("<ul>");
                for (Admin existingAdmin : adminManager.getAllAdmins()) {
                    out.println("<li>Username: " + existingAdmin.getUsername() +
                            ", ID: " + existingAdmin.getAdminId() +
                            ", Super Admin: " + existingAdmin.isSuperAdmin() + "</li>");
                }
                out.println("</ul>");
            } else {
                out.println("<p>No existing admins found.</p>");
            }

            // Create admin account
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword("admin123"); // Will be hashed in addAdmin
            admin.setFullName("System Administrator");
            admin.setEmail("admin@example.com");
            admin.setSuperAdmin(true);

            boolean success = adminManager.addAdmin(admin);

            if (success) {
                out.println("<h2>Admin Account Created Successfully</h2>");
                out.println("<p>Username: admin</p>");
                out.println("<p>Password: admin123</p>");
                out.println("<p>Admin ID: " + admin.getAdminId() + "</p>");
            } else {
                out.println("<h2>Failed to Create Admin Account</h2>");
                out.println("<p>An admin account might already exist with the same username.</p>");
            }

            out.println("<p><a href='" + request.getContextPath() + "/admin/login'>Go to Admin Login</a></p>");
            out.println("</body></html>");

        } catch (Exception e) {
            out.println("<html><body>");
            out.println("<h1>Error Creating Admin Account</h1>");
            out.println("<p>Error: " + e.getMessage() + "</p>");
            out.println("<pre>");
            e.printStackTrace(new PrintWriter(out));
            out.println("</pre>");
            out.println("</body></html>");
        }
    }
}
