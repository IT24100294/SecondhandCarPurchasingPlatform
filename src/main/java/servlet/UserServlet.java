package servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            request.setAttribute("users", getAllUsers());
            request.getRequestDispatcher("/Frontend/users.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error loading users", e);
            request.setAttribute("error", "Error loading users");
            request.getRequestDispatcher("/Frontend/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            switch (action) {
                case "add":
                    addUser(request);
                    break;
                case "delete":
                    deleteUser(request);
                    break;
                case "update":
                    updateUser(request);
                    break;
            }
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (Exception e) {
            logger.error("Error processing user action", e);
            request.setAttribute("error", "Error processing request");
            request.getRequestDispatcher("/Frontend/error.jsp").forward(request, response);
        }
    }

    private void addUser(HttpServletRequest request) throws SQLException {
        String sql = "INSERT INTO users (username, email, role) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, request.getParameter("username"));
            stmt.setString(2, request.getParameter("email"));
            stmt.setString(3, request.getParameter("role"));
            stmt.executeUpdate();
        }
    }

    private List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, email, role FROM users";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        }
        return users;
    }

    private void deleteUser(HttpServletRequest request) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(request.getParameter("userId")));
            stmt.executeUpdate();
        }
    }

    private void updateUser(HttpServletRequest request) throws SQLException {
        String sql = "UPDATE users SET username = ?, email = ?, role = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, request.getParameter("username"));
            stmt.setString(2, request.getParameter("email"));
            stmt.setString(3, request.getParameter("role"));
            stmt.setInt(4, Integer.parseInt(request.getParameter("userId")));
            stmt.executeUpdate();
        }
    }

    @Override
    public void destroy() {
        DatabaseUtil.closeDataSource();
    }
}

class User {
    private int id;
    private String username;
    private String email;
    private String role;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
} 