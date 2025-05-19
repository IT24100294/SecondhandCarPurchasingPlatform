package com.carservice.servlet.admin;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.carservice.model.admin.Admin;
import com.carservice.model.admin.AdminManager;

/**
 * Servlet for viewing all admin users
 */
@WebServlet("/admin/view-admins")
public class ViewAdminsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests - display all admin users
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

        // Check if super admin (only super admins can view all admins)
        Boolean isSuperAdmin = (Boolean) session.getAttribute("isSuperAdmin");
        if (isSuperAdmin == null || !isSuperAdmin) {
            // Not a super admin, redirect to dashboard
            session.setAttribute("errorMessage", "You do not have permission to view all administrators");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        try {
            // Get all admin users
            AdminManager adminManager = new AdminManager(getServletContext());
            List<Admin> admins = adminManager.getAllAdmins();

            System.out.println("ViewAdminsServlet - Retrieved admins: " + admins.size());
            for (Admin admin : admins) {
                System.out.println("- Admin: " + admin.getUsername() + ", ID: " + admin.getAdminId());
            }

            // Add admins to request
            request.setAttribute("admins", admins);

            // Forward to view admins page
            request.getRequestDispatcher("/admin/view-admins.jsp").forward(request, response);
        } catch (Exception e) {
            // Handle errors
            System.err.println("Error in ViewAdminsServlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/admin/view-admins.jsp").forward(request, response);
        }
    }
}