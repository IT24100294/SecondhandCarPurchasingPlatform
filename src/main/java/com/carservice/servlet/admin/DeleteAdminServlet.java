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
 * Servlet for deleting an admin user
 */
@WebServlet("/admin/delete-admin")
public class DeleteAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests - display delete confirmation
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

        // Check if super admin (only super admins can delete admins)
        Boolean isSuperAdmin = (Boolean) session.getAttribute("isSuperAdmin");
        if (isSuperAdmin == null || !isSuperAdmin) {
            // Not a super admin, redirect to dashboard
            session.setAttribute("errorMessage", "You do not have permission to delete administrators");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        // Get admin ID from request parameter
        String adminId = request.getParameter("adminId");
        if (adminId == null || adminId.trim().isEmpty()) {
            // No admin ID provided
            response.sendRedirect(request.getContextPath() + "/admin/view-admins");
            return;
        }

        // Prevent self-deletion
        String currentAdminId = (String) session.getAttribute("adminId");
        if (adminId.equals(currentAdminId)) {
            session.setAttribute("errorMessage", "You cannot delete your own account");
            response.sendRedirect(request.getContextPath() + "/admin/view-admins");
            return;
        }

        try {
            // Create AdminManager and get admin
            AdminManager adminManager = new AdminManager(getServletContext());
            Admin admin = adminManager.getAdminById(adminId);

            if (admin == null) {
                // Admin not found
                session.setAttribute("errorMessage", "Administrator not found");
                response.sendRedirect(request.getContextPath() + "/admin/view-admins");
                return;
            }

            // Add admin to request
            request.setAttribute("admin", admin);

            // Forward to delete confirmation page
            request.getRequestDispatcher("/admin/delete-admin.jsp").forward(request, response);
        } catch (Exception e) {
            // Handle errors
            System.err.println("Error in DeleteAdminServlet (GET): " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/view-admins");
        }
    }

    /**
     * Handles POST requests - process delete operation
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

        // Check if super admin (only super admins can delete admins)
        Boolean isSuperAdmin = (Boolean) session.getAttribute("isSuperAdmin");
        if (isSuperAdmin == null || !isSuperAdmin) {
            // Not a super admin, redirect to dashboard
            session.setAttribute("errorMessage", "You do not have permission to delete administrators");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        // Get admin ID and confirmation
        String adminId = request.getParameter("adminId");
        String confirmDelete = request.getParameter("confirmDelete");

        if (adminId == null || adminId.trim().isEmpty()) {
            // No admin ID provided
            response.sendRedirect(request.getContextPath() + "/admin/view-admins");
            return;
        }

        // Check confirmation
        if (!"yes".equals(confirmDelete)) {
            // Not confirmed, redirect back to view
            response.sendRedirect(request.getContextPath() + "/admin/view-admins");
            return;
        }

        // Prevent self-deletion
        String currentAdminId = (String) session.getAttribute("adminId");
        if (adminId.equals(currentAdminId)) {
            session.setAttribute("errorMessage", "You cannot delete your own account");
            response.sendRedirect(request.getContextPath() + "/admin/view-admins");
            return;
        }

        try {
            // Create AdminManager and delete admin
            AdminManager adminManager = new AdminManager(getServletContext());
            boolean deleted = adminManager.deleteAdmin(adminId);

            if (deleted) {
                // Redirect to admin list with success message
                session.setAttribute("successMessage", "Administrator deleted successfully");
            } else {
                // Redirect with error message
                session.setAttribute("errorMessage", "Failed to delete administrator. They may be the last super admin.");
            }

            response.sendRedirect(request.getContextPath() + "/admin/view-admins");
        } catch (Exception e) {
            // Handle errors
            System.err.println("Error in DeleteAdminServlet (POST): " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/view-admins");
        }
    }
}
