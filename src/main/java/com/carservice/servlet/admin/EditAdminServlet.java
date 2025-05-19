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
 * Servlet for editing an admin user
 */
@WebServlet("/admin/edit-admin")
public class EditAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests - display edit admin form
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

        // Check if super admin (only super admins can edit other admins)
        Boolean isSuperAdmin = (Boolean) session.getAttribute("isSuperAdmin");
        String currentAdminId = (String) session.getAttribute("adminId");
        String adminIdToEdit = request.getParameter("adminId");

        // Regular admins can edit their own profile
        boolean editingSelf = currentAdminId != null && currentAdminId.equals(adminIdToEdit);

        if (!editingSelf && (isSuperAdmin == null || !isSuperAdmin)) {
            // Not a super admin and not editing self, redirect to dashboard
            session.setAttribute("errorMessage", "You do not have permission to edit other administrators");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        // Get the admin to edit
        if (adminIdToEdit == null || adminIdToEdit.trim().isEmpty()) {
            // No admin ID provided
            response.sendRedirect(request.getContextPath() + "/admin/view-admins");
            return;
        }

        try {
            // Create AdminManager and get admin
            AdminManager adminManager = new AdminManager(getServletContext());
            Admin admin = adminManager.getAdminById(adminIdToEdit);

            if (admin == null) {
                // Admin not found
                session.setAttribute("errorMessage", "Administrator not found");
                response.sendRedirect(request.getContextPath() + "/admin/view-admins");
                return;
            }

            // Add admin to request
            request.setAttribute("admin", admin);
            request.setAttribute("editingSelf", editingSelf);

            // Forward to edit admin page
            request.getRequestDispatcher("/admin/edit-admin.jsp").forward(request, response);
        } catch (Exception e) {
            // Handle errors
            System.err.println("Error in EditAdminServlet (GET): " + e.getMessage());
            e.printStackTrace();
            session.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/view-admins");
        }
    }

    /**
     * Handles POST requests - process edit admin form
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

        // Check if super admin or editing own profile
        Boolean isSuperAdmin = (Boolean) session.getAttribute("isSuperAdmin");
        String currentAdminId = (String) session.getAttribute("adminId");
        String adminIdToEdit = request.getParameter("adminId");

        boolean editingSelf = currentAdminId != null && currentAdminId.equals(adminIdToEdit);

        if (!editingSelf && (isSuperAdmin == null || !isSuperAdmin)) {
            // Not a super admin and not editing self, redirect to dashboard
            session.setAttribute("errorMessage", "You do not have permission to edit other administrators");
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
        if (adminIdToEdit == null || username == null || fullName == null || email == null ||
                adminIdToEdit.trim().isEmpty() || username.trim().isEmpty() ||
                fullName.trim().isEmpty() || email.trim().isEmpty()) {

            request.setAttribute("errorMessage", "Required fields cannot be empty");
            request.getRequestDispatcher("/admin/edit-admin.jsp").forward(request, response);
            return;
        }

        // Check if passwords match if provided
        if (password != null && !password.trim().isEmpty() &&
                !password.equals(confirmPassword)) {

            request.setAttribute("errorMessage", "Passwords do not match");
            request.getRequestDispatcher("/admin/edit-admin.jsp").forward(request, response);
            return;
        }

        try {
            // Create AdminManager
            AdminManager adminManager = new AdminManager(getServletContext());

            // Get existing admin
            Admin existingAdmin = adminManager.getAdminById(adminIdToEdit);
            if (existingAdmin == null) {
                session.setAttribute("errorMessage", "Administrator not found");
                response.sendRedirect(request.getContextPath() + "/admin/view-admins");
                return;
            }

            // Check if username changed and already exists
            if (!existingAdmin.getUsername().equals(username)) {
                Admin adminWithSameUsername = adminManager.getAdminByUsername(username);
                if (adminWithSameUsername != null) {
                    request.setAttribute("errorMessage", "Username already exists");
                    request.setAttribute("admin", existingAdmin);
                    request.setAttribute("editingSelf", editingSelf);
                    request.getRequestDispatcher("/admin/edit-admin.jsp").forward(request, response);
                    return;
                }
            }

            // Update admin
            Admin updatedAdmin = new Admin();
            updatedAdmin.setAdminId(adminIdToEdit);
            updatedAdmin.setUsername(username);
            updatedAdmin.setPassword(password); // Will be hashed if not empty
            updatedAdmin.setFullName(fullName);
            updatedAdmin.setEmail(email);

            // Only super admins can change super admin status, and not for themselves
            if (isSuperAdmin && !editingSelf) {
                updatedAdmin.setSuperAdmin("on".equals(superAdminStr) || "true".equals(superAdminStr));
            } else {
                updatedAdmin.setSuperAdmin(existingAdmin.isSuperAdmin());
            }

            // Preserve other fields
            updatedAdmin.setCreatedDate(existingAdmin.getCreatedDate());
            updatedAdmin.setLastLoginDate(existingAdmin.getLastLoginDate());

            // Update admin
            boolean success = adminManager.updateAdmin(updatedAdmin);

            if (success) {
                // Update session data if editing self
                if (editingSelf) {
                    session.setAttribute("adminUsername", updatedAdmin.getUsername());
                    session.setAttribute("adminFullName", updatedAdmin.getFullName());
                }

                // Redirect with success message
                if (editingSelf) {
                    session.setAttribute("successMessage", "Your profile has been updated successfully");
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                } else {
                    session.setAttribute("successMessage", "Administrator updated successfully");
                    response.sendRedirect(request.getContextPath() + "/admin/view-admins");
                }
            } else {
                // Set error message and go back to form
                request.setAttribute("errorMessage", "Failed to update administrator. Please try again.");
                request.setAttribute("admin", existingAdmin);
                request.setAttribute("editingSelf", editingSelf);
                request.getRequestDispatcher("/admin/edit-admin.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Handle errors
            System.err.println("Error in EditAdminServlet (POST): " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/admin/edit-admin.jsp").forward(request, response);
        }
    }
}
