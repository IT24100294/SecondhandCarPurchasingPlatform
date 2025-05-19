package com.carservice.filter.model;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filter for admin authentication
 */
@WebFilter("/admin/*")
public class AdminAuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get the requested URI path6
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        System.out.println("AdminAuthFilter: Checking access to " + path);

        // Allow access to login page and CSS without authentication
        if (path.equals("/admin/login") || path.contains("/css/") || path.contains("/js/")) {
            System.out.println("AdminAuthFilter: Allowing access to public resource");
            chain.doFilter(request, response);
            return;
        }

        // Get session without creating a new one
        HttpSession session = httpRequest.getSession(false);

        // Check if user is logged in as admin
        if (session == null || session.getAttribute("adminId") == null) {
            System.out.println("AdminAuthFilter: No valid session found, redirecting to login");
            // Not logged in, redirect to admin login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/login");
            return;
        }

        // Log session details
        System.out.println("AdminAuthFilter: Session found - Admin ID: " + session.getAttribute("adminId"));
        System.out.println("AdminAuthFilter: Session found - Username: " + session.getAttribute("adminUsername"));
        System.out.println("AdminAuthFilter: Session found - Super Admin: " + session.getAttribute("isSuperAdmin"));

        // Check if requested page requires super admin privileges
        boolean requiresSuperAdmin = path.contains("/add-admin") ||
                path.contains("/view-admins") ||
                path.contains("/delete-admin");

        // For edit-admin, only check if editing other admins
        if (path.contains("/edit-admin")) {
            String adminIdToEdit = httpRequest.getParameter("adminId");
            String currentAdminId = (String) session.getAttribute("adminId");

            if (adminIdToEdit != null && !adminIdToEdit.equals(currentAdminId)) {
                requiresSuperAdmin = true;
            }
        }

        // If super admin is required, check if user is a super admin
        if (requiresSuperAdmin) {
            Boolean isSuperAdmin = (Boolean) session.getAttribute("isSuperAdmin");

            if (isSuperAdmin == null || !isSuperAdmin) {
                // Not a super admin, redirect to dashboard
                System.out.println("AdminAuthFilter: Not a super admin, access denied to " + path);
                session.setAttribute("errorMessage", "You do not have permission to access that page");
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/dashboard");
                return;
            }
        }

        // User is authenticated, continue with the request
        System.out.println("AdminAuthFilter: Access granted to " + path);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // No cleanup needed
    }
}