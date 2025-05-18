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
 * Servlet for the admin dashboard
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests - display the admin dashboard
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

        try {
            // Get admin info
            String adminId = (String) session.getAttribute("adminId");
            AdminManager adminManager = new AdminManager(getServletContext());
            Admin admin = adminManager.getAdminById(adminId);

            if (admin == null) {
                // Admin not found in database, invalidate session and redirect to login
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/admin/login");
                return;
            }

            // Get admin count
            int adminCount = adminManager.getAllAdmins().size();

            // Add dashboard data to request
            request.setAttribute("admin", admin);
            request.setAttribute("adminCount", adminCount);

            // Forward to dashboard page
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            // Handle errors
            System.err.println("Error in AdminDashboardServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        }
    }
}
