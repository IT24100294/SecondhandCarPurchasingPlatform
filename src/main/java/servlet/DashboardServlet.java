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

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Set dashboard statistics
            request.setAttribute("userCount", getUserCount());
            request.setAttribute("listingCount", getListingCount());
            request.setAttribute("salesCount", getSalesCount());
            
            // Set recent activities
            request.setAttribute("recentActivities", getRecentActivities());
            
            // Forward to the dashboard JSP
            request.getRequestDispatcher("/Frontend/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error processing dashboard request", e);
            request.setAttribute("error", "An error occurred while loading the dashboard. Please try again later.");
            request.getRequestDispatcher("/Frontend/error.jsp").forward(request, response);
        }
    }
    
    private int getUserCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
    
    private int getListingCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM car_listings";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
    
    private int getSalesCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM sales";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
    
    private List<String> getRecentActivities() throws SQLException {
        List<String> activities = new ArrayList<>();
        String sql = "SELECT activity_type, user_name, details, created_at " +
                    "FROM activity_log " +
                    "ORDER BY created_at DESC LIMIT 5";
                    
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String activityType = rs.getString("activity_type");
                String userName = rs.getString("user_name");
                String details = rs.getString("details");
                String timestamp = rs.getTimestamp("created_at").toString();
                
                String activity = String.format("%s by <b>%s</b> (%s)", 
                    activityType, userName, timestamp);
                if (details != null && !details.isEmpty()) {
                    activity += " - " + details;
                }
                
                activities.add(activity);
            }
        }
        
        return activities;
    }
    
    @Override
    public void destroy() {
        DatabaseUtil.closeDataSource();
    }
} 