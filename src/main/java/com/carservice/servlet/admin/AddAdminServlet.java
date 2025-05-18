package com.carservice.servlet.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.carservice.model.admin.Admin;
import com.carservice.model.admin.AdminManager;

/**
 * Servlet for adding a new admin user
 */
@WebServlet("/admin/add-admin")
public class AddAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests - display add admin form
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            // Not logged in, redirect to login
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        // Check if super admin (only super admins can add admins)
        Boolean isSuperAdmin = (Boolean) session.getAttribute("isSuperAdmin");
        if (isSuperAdmin == null || !isSuperAdmin) {
            // Not a super admin, redirect to dashboard
            session.setAttribute("errorMessage", "You do not have permission to add administrators");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        // Forward to add admin page
        request.getRequestDispatcher("/admin/add-admin.jsp").forward(request, response);
    }

    /**
     * Handles POST requests - process add admin form
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            // Not logged in, redirect to login
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        // Check if super admin (only super admins can add admins)
        Boolean isSuperAdmin = (Boolean) session.getAttribute("isSuperAdmin");
        if (isSuperAdmin == null || !isSuperAdmin) {
            // Not a super admin, redirect to dashboard
            session.setAttribute("errorMessage", "You do not have permission to add administrators");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        // Get form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String superAdminStr = request.getParameter("superAdmin");

        // Basic validation
        if (username == null || password == null || confirmPassword == null ||
                fullName == null || email == null ||
                username.trim().isEmpty() || password.trim().isEmpty() ||
                confirmPassword.trim().isEmpty() || fullName.trim().isEmpty() ||
                email.trim().isEmpty()) {

            request.setAttribute("errorMessage", "All fields are required");
            request.getRequestDispatcher("/admin/add-admin.jsp").forward(request, response);
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match");
            request.getRequestDispatcher("/admin/add-admin.jsp").forward(request, response);
            return;
        }

        try {
            // Create AdminManager
            AdminManager adminManager = new AdminManager(getServletContext());

            // Check if username already exists
            if (adminManager.getAdminByUsername(username) != null) {
                request.setAttribute("errorMessage", "Username already exists");
                request.getRequestDispatcher("/admin/add-admin.jsp").forward(request, response);
                return;
            }

            // Create new admin
            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password); // Will be hashed during addAdmin
            admin.setFullName(fullName);
            admin.setEmail(email);
            admin.setSuperAdmin("on".equals(superAdminStr) || "true".equals(superAdminStr));

            // Add admin
            boolean success = adminManager.addAdmin(admin);

            if (success) {
                // Redirect to view admins with success message
                session.setAttribute("successMessage", "Admin added successfully");
                response.sendRedirect(request.getContextPath() + "/admin/view-admins");
            } else {
                // Set error message and go back to form
                request.setAttribute("errorMessage", "Failed to add admin. Please try again.");
                request.getRequestDispatcher("/admin/add-admin.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Handle errors
            System.err.println("Error in AddAdminServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/admin/add-admin.jsp").forward(request, response);
        }
    }
}
