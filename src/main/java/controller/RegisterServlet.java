package controller;

import dao.UserDAO;
import model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO(getServletContext().getRealPath(""));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");

        if (fullName == null || email == null || password == null ||
                fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("/frontEnd/pages/Register.jsp").forward(request, response);
            return;
        }

        // Check if user already exists
        if (userDAO.getUserByEmail(email) != null) {
            request.setAttribute("errorMessage", "Email already registered.");
            request.getRequestDispatcher("/frontEnd/pages/Register.jsp").forward(request, response);
            return;
        }

        // Create new user
        User user = new User(fullName, email, password, address, city, state, zip);
        
        if (userDAO.createUser(user)) {
            // Remove session attributes since we want user to login first
            request.setAttribute("successMessage", "Registration successful! Please login to continue.");
            response.sendRedirect(request.getContextPath() + "/frontEnd/pages/login.jsp");
        } else {
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            request.getRequestDispatcher("/frontEnd/pages/Register.jsp").forward(request, response);
        }
    }
}
