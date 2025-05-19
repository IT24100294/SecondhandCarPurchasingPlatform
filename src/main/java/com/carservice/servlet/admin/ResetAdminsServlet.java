package com.carservice.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.carservice.model.admin.Admin;
import com.carservice.model.admin.AdminManager;

import java.io.File;
import java.io.FileWriter;

/**
 * Servlet for resetting admin users (only for troubleshooting)
 */
@WebServlet("/reset-admins")
public class ResetAdminsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests - Reset all admin users
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Get the data file path
            String webInfDataPath = "/WEB-INF/data";
            String dataFilePath = getServletContext().getRealPath(webInfDataPath) + File.separator + "admin_users.txt";

            // Empty the file
            try (FileWriter writer = new FileWriter(dataFilePath)) {
                writer.write("");
            }

            // Create AdminManager
            AdminManager adminManager = new AdminManager(getServletContext());

            // Create new admin account
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword("admin123"); // Will be hashed in addAdmin
            admin.setFullName("System Administrator");
            admin.setEmail("admin@example.com");
            admin.setSuperAdmin(true);

            boolean success = adminManager.addAdmin(admin);

            out.println("<html><body>");
            out.println("<h1>Admin Accounts Reset</h1>");

            if (success) {
                out.println("<p>Successfully cleared all admin accounts and created a new default admin.</p>");
                out.println("<p>Username: admin</p>");
                out.println("<p>Password: admin123</p>");
            } else {
                out.println("<p>Failed to create a new admin account after reset.</p>");
            }

            out.println("<p><a href='" + request.getContextPath() + "/admin/login'>Go to Admin Login</a></p>");
            out.println("</body></html>");

        } catch (Exception e) {
            out.println("<html><body>");
            out.println("<h1>Error Resetting Admin Accounts</h1>");
            out.println("<p>Error: " + e.getMessage() + "</p>");
            out.println("<pre>");
            e.printStackTrace(new PrintWriter(out));
            out.println("</pre>");
            out.println("</body></html>");
        }
    }
}