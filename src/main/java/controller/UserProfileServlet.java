package controller;

import dao.UserDAO;
import model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/UserProfile")
public class UserProfileServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO(getServletContext().getRealPath(""));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("userEmail");
        
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/frontEnd/pages/login.jsp");
            return;
        }

        User user = userDAO.getUserByEmail(email);
        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/frontEnd/pages/profile.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/frontEnd/pages/login.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("userEmail");

        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/frontEnd/pages/login.jsp");
            return;
        }

        if ("update".equals(action)) {
            updateProfile(request, response, email);
        } else if ("delete".equals(action)) {
            deleteProfile(request, response, email);
        }
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response, String email)
            throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");

        User existingUser = userDAO.getUserByEmail(email);
        if (existingUser != null) {
            existingUser.setFullName(fullName);
            if (password != null && !password.isEmpty()) {
                existingUser.setPassword(password);
            }
            existingUser.setAddress(address);
            existingUser.setCity(city);
            existingUser.setState(state);
            existingUser.setZip(zip);

            if (userDAO.updateUser(existingUser)) {
                request.setAttribute("successMessage", "Profile updated successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to update profile.");
            }
        }

        request.getRequestDispatcher("/frontEnd/pages/profile.jsp").forward(request, response);
    }

    private void deleteProfile(HttpServletRequest request, HttpServletResponse response, String email)
            throws ServletException, IOException {
        if (userDAO.deleteUser(email)) {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/frontEnd/pages/login.jsp");
        } else {
            request.setAttribute("errorMessage", "Failed to delete profile.");
            request.getRequestDispatcher("/frontEnd/pages/profile.jsp").forward(request, response);
        }
    }
} 