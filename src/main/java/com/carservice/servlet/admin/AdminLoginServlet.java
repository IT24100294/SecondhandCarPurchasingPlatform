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
 * Servlet for handling admin login
 */
@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests - display login form
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("adminId") != null) {
            // Already logged in, redirect to dashboard
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        // Check if default admin needs to be created
        AdminManager adminManager = new AdminManager(getServletContext());
        if (!adminManager.hasAdmins()) {
            System.out.println("No admins found during login page access - creating default admin");
            adminManager.createDefaultSuperAdmin();
        }

        // Forward to login page
        request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
    }

    /**
     * Handles POST requests - process login form
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("Login attempt with username: " + username);

        // Validate input
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Username and password are required");
            request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
            return;
        }

        try {
            // Create AdminManager and authenticate
            AdminManager adminManager = new AdminManager(getServletContext());

            // First check if we need to create a default admin
            if (!adminManager.hasAdmins()) {
                System.out.println("No admins found during login attempt - creating default admin");
                adminManager.createDefaultSuperAdmin();
            }

            System.out.println("Attempting authentication for: " + username);
            Admin admin = adminManager.authenticate(username, password);

            if (admin != null) {
                System.out.println("Authentication successful for: " + username);
                // Create session and add admin info
                HttpSession session = request.getSession(true);
                session.setAttribute("adminId", admin.getAdminId());
                session.setAttribute("adminUsername", admin.getUsername());
                session.setAttribute("adminFullName", admin.getFullName());
                session.setAttribute("isSuperAdmin", admin.isSuperAdmin());

                // Redirect to dashboard
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                System.out.println("Authentication failed for: " + username);
                // Invalid credentials
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Handle errors
            System.err.println("Error in AdminLoginServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred during login: " + e.getMessage());
            request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
        }
    }
}