package com.carservice.model.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Admin class representing an administrator user
 */
public class Admin {
    private String adminId;
    private String username;
    private String password; // Will be stored as hash in real application
    private String fullName;
    private String email;
    private boolean superAdmin;
    private Date createdDate;
    private Date lastLoginDate;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Default constructor
    public Admin() {
        this.adminId = java.util.UUID.randomUUID().toString();
        this.createdDate = new Date();
    }

    // Constructor with all fields
    public Admin(String adminId, String username, String password, String fullName,
                 String email, boolean superAdmin, Date createdDate, Date lastLoginDate) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.superAdmin = superAdmin;
        this.createdDate = createdDate;
        this.lastLoginDate = lastLoginDate;
    }

    // Getters and Setters
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    // For file storage
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(adminId).append(",");
        sb.append(username).append(",");
        sb.append(password).append(",");
        sb.append(fullName.replace(",", "&#44;")).append(",");
        sb.append(email).append(",");
        sb.append(superAdmin).append(",");
        sb.append(createdDate != null ? DATE_FORMAT.format(createdDate) : "").append(",");
        sb.append(lastLoginDate != null ? DATE_FORMAT.format(lastLoginDate) : "");
        return sb.toString();
    }

    // From file storage
    public static Admin fromFileString(String fileString) {
        try {
            System.out.println("Parsing admin from string: " + fileString);
            String[] parts = fileString.split(",");

            // Debugging
            System.out.println("Number of parts: " + parts.length);
            for (int i = 0; i < parts.length; i++) {
                System.out.println("Part " + i + ": " + parts[i]);
            }

            if (parts.length < 7) {
                System.err.println("Not enough parts in admin string (need at least 7): " + parts.length);
                return null;
            }

            Admin admin = new Admin();
            admin.setAdminId(parts[0]);
            admin.setUsername(parts[1]);
            admin.setPassword(parts[2]);
            admin.setFullName(parts[3].replace("&#44;", ","));
            admin.setEmail(parts[4]);
            admin.setSuperAdmin(Boolean.parseBoolean(parts[5]));

            try {
                if (parts.length > 6 && parts[6] != null && !parts[6].isEmpty()) {
                    admin.setCreatedDate(DATE_FORMAT.parse(parts[6]));
                } else {
                    admin.setCreatedDate(new Date()); // Default to current date
                }

                if (parts.length > 7 && parts[7] != null && !parts[7].isEmpty()) {
                    admin.setLastLoginDate(DATE_FORMAT.parse(parts[7]));
                }
            } catch (ParseException e) {
                System.err.println("Error parsing dates for admin: " + e.getMessage());
                // Continue with the admin object even if dates can't be parsed
                admin.setCreatedDate(new Date()); // Default to current date
            }

            System.out.println("Successfully parsed admin: " + admin.getUsername());
            return admin;
        } catch (Exception e) {
            System.err.println("Error parsing admin record: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Simple password hashing method (for demo - use more secure methods in production)
    public static String hashPassword(String plainPassword) {
        // In a real application, use a proper password hashing library
        // This is a very simple hash for demonstration purposes only
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(plainPassword.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            System.err.println("Error hashing password: " + e.getMessage());
            return plainPassword; // Fallback to plain password if hashing fails
        }
    }

    // Validate password
    public boolean validatePassword(String inputPassword) {
        String hashedInput = hashPassword(inputPassword);
        System.out.println("Validating password:");
        System.out.println("Input password: " + inputPassword);
        System.out.println("Hashed input: " + hashedInput);
        System.out.println("Stored hash: " + this.password);
        return this.password.equals(hashedInput);
    }
}