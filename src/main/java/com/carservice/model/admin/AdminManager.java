package com.carservice.model.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;

/**
 * Manages admin users - creation, authentication, editing, deletion
 */
public class AdminManager {
    private static final String ADMIN_USERS_FILE = "admin_users.txt";
    private List<Admin> adminUsers;
    private ServletContext servletContext;
    private String dataFilePath;

    // Constructor
    public AdminManager() {
        this(null);
    }

    // Constructor with ServletContext
    public AdminManager(ServletContext servletContext) {
        this.servletContext = servletContext;
        this.adminUsers = new ArrayList<>();
        initializeFilePath();
        loadAdminUsers();
    }

    // Initialize file path
    private void initializeFilePath() {
        if (servletContext != null) {
            // Use WEB-INF/data within the application context
            String webInfDataPath = "/WEB-INF/data";
            dataFilePath = servletContext.getRealPath(webInfDataPath) + File.separator + ADMIN_USERS_FILE;

            // Make sure directory exists
            File dataDir = new File(servletContext.getRealPath(webInfDataPath));
            if (!dataDir.exists()) {
                boolean created = dataDir.mkdirs();
                System.out.println("Created WEB-INF/data directory: " + dataDir.getAbsolutePath() + " - Success: " + created);
            }
        } else {
            // Fallback to simple data directory if not in web context
            String dataPath = "data";
            dataFilePath = dataPath + File.separator + ADMIN_USERS_FILE;

            // Make sure directory exists
            File dataDir = new File(dataPath);
            if (!dataDir.exists()) {
                boolean created = dataDir.mkdirs();
                System.out.println("Created fallback data directory: " + dataPath + " - Success: " + created);
            }
        }

        System.out.println("AdminManager: Using data file path: " + dataFilePath);
    }

    // Load admin users from file
    private void loadAdminUsers() {
        // Clear existing admin users first
        adminUsers.clear();

        File file = new File(dataFilePath);

        // If file doesn't exist, create it
        if (!file.exists()) {
            try {
                // Ensure parent directory exists
                file.getParentFile().mkdirs();
                boolean created = file.createNewFile();
                System.out.println("Created admin users file: " + dataFilePath + " - Success: " + created);
            } catch (IOException e) {
                System.err.println("Error creating admin users file: " + e.getMessage());
                e.printStackTrace();
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue;
                }

                System.out.println("Reading admin line " + lineNumber + ": " + line);
                Admin admin = Admin.fromFileString(line);
                if (admin != null) {
                    adminUsers.add(admin);
                    System.out.println("Loaded admin user: " + admin.getUsername() + " - ID: " + admin.getAdminId());
                } else {
                    System.err.println("Failed to parse admin user from line " + lineNumber + ": " + line);
                }
            }
            System.out.println("Total admin users loaded: " + adminUsers.size());
        } catch (IOException e) {
            System.err.println("Error loading admin users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Save admin users to file
    private boolean saveAdminUsers() {
        try {
            // Ensure directory exists
            File file = new File(dataFilePath);
            if (!file.getParentFile().exists()) {
                boolean created = file.getParentFile().mkdirs();
                System.out.println("Created directory: " + file.getParentFile().getAbsolutePath() + " - Success: " + created);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilePath))) {
                for (Admin admin : adminUsers) {
                    String line = admin.toFileString();
                    writer.write(line);
                    writer.newLine();
                    System.out.println("Saved admin user: " + admin.getUsername() + " - Line: " + line);
                }
            }
            System.out.println("Admin users saved successfully to: " + dataFilePath);

            // Reload to verify
            loadAdminUsers();
            return true;
        } catch (IOException e) {
            System.err.println("Error saving admin users: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Add a new admin user
    public boolean addAdmin(Admin admin) {
        try {
            System.out.println("Adding new admin: " + admin.getUsername());

            // Check if username already exists
            if (getAdminByUsername(admin.getUsername()) != null) {
                System.out.println("Username already exists: " + admin.getUsername());
                return false; // Username already taken
            }

            // Generate ID if not provided
            if (admin.getAdminId() == null || admin.getAdminId().isEmpty()) {
                admin.setAdminId(UUID.randomUUID().toString());
                System.out.println("Generated new admin ID: " + admin.getAdminId());
            }

            // Set creation date if not provided
            if (admin.getCreatedDate() == null) {
                admin.setCreatedDate(new Date());
                System.out.println("Set creation date: " + admin.getCreatedDate());
            }

            // Hash password if it's not already hashed (simple check based on length)
            if (admin.getPassword() != null && admin.getPassword().length() < 40) {
                String originalPassword = admin.getPassword();
                String hashedPassword = Admin.hashPassword(originalPassword);
                admin.setPassword(hashedPassword);
                System.out.println("Hashed password for " + admin.getUsername() + ": " + hashedPassword);
            }

            adminUsers.add(admin);
            boolean saved = saveAdminUsers();
            System.out.println("Admin saved successfully: " + saved);
            return saved;
        } catch (Exception e) {
            System.err.println("Exception occurred when adding admin:");
            e.printStackTrace();
            return false;
        }
    }

    // Get all admin users
    public List<Admin> getAllAdmins() {
        if (adminUsers.isEmpty()) {
            System.out.println("Admin list is empty, reloading from file");
            loadAdminUsers();
        }

        System.out.println("Getting all admins - count: " + adminUsers.size());
        for (Admin admin : adminUsers) {
            System.out.println("  Admin: " + admin.getUsername() + " - ID: " + admin.getAdminId());
        }

        return new ArrayList<>(adminUsers);
    }

    // Get admin by ID
    public Admin getAdminById(String adminId) {
        System.out.println("Looking for admin with ID: " + adminId);
        for (Admin admin : adminUsers) {
            if (admin.getAdminId().equals(adminId)) {
                System.out.println("Found admin: " + admin.getUsername());
                return admin;
            }
        }
        System.out.println("Admin not found with ID: " + adminId);
        return null;
    }

    // Get admin by username
    public Admin getAdminByUsername(String username) {
        System.out.println("Looking for admin with username: " + username);
        for (Admin admin : adminUsers) {
            System.out.println("Checking admin: " + admin.getUsername() + " - Matches? " + admin.getUsername().equals(username));
            if (admin.getUsername().equals(username)) {
                System.out.println("Found admin: " + admin.getUsername() + " - ID: " + admin.getAdminId());
                return admin;
            }
        }
        System.out.println("Admin not found with username: " + username);
        return null;
    }

    // Update admin
    public boolean updateAdmin(Admin updatedAdmin) {
        System.out.println("Updating admin: " + updatedAdmin.getUsername() + " - ID: " + updatedAdmin.getAdminId());

        for (int i = 0; i < adminUsers.size(); i++) {
            Admin admin = adminUsers.get(i);
            if (admin.getAdminId().equals(updatedAdmin.getAdminId())) {
                // Don't change the password if it's empty (meaning it wasn't changed)
                if (updatedAdmin.getPassword() == null || updatedAdmin.getPassword().isEmpty()) {
                    updatedAdmin.setPassword(admin.getPassword());
                } else if (updatedAdmin.getPassword().length() < 40) {
                    // Hash password if it's not already hashed
                    String hashedPassword = Admin.hashPassword(updatedAdmin.getPassword());
                    updatedAdmin.setPassword(hashedPassword);
                    System.out.println("Hashed updated password: " + hashedPassword);
                }

                // Preserve creation date and other fields that shouldn't change
                if (updatedAdmin.getCreatedDate() == null) {
                    updatedAdmin.setCreatedDate(admin.getCreatedDate());
                }

                adminUsers.set(i, updatedAdmin);
                System.out.println("Admin updated in memory. Saving to file...");
                return saveAdminUsers();
            }
        }

        System.out.println("Admin not found for update: " + updatedAdmin.getAdminId());
        return false;
    }

    // Delete admin
    public boolean deleteAdmin(String adminId) {
        System.out.println("Deleting admin with ID: " + adminId);

        Admin admin = getAdminById(adminId);
        if (admin != null) {
            // Prevent deletion of the last super admin
            if (admin.isSuperAdmin() && countSuperAdmins() <= 1) {
                System.out.println("Cannot delete the last super admin");
                return false; // Can't delete the last super admin
            }

            adminUsers.remove(admin);
            System.out.println("Admin removed from memory. Saving to file...");
            return saveAdminUsers();
        }

        System.out.println("Admin not found for deletion: " + adminId);
        return false;
    }

    // Count super admins
    private int countSuperAdmins() {
        int count = 0;
        for (Admin admin : adminUsers) {
            if (admin.isSuperAdmin()) {
                count++;
            }
        }
        System.out.println("Number of super admins: " + count);
        return count;
    }

    // Authenticate admin login
    public Admin authenticate(String username, String password) {
        System.out.println("Authenticating user: " + username);
        System.out.println("Current admin users: " + adminUsers.size());

        for (Admin admin : adminUsers) {
            System.out.println("Admin in system: " + admin.getUsername() + " - isMatch: " + admin.getUsername().equals(username));
        }

        Admin admin = getAdminByUsername(username);
        if (admin != null) {
            System.out.println("Found admin during authentication: " + admin.getUsername());
            System.out.println("Stored password hash: " + admin.getPassword());

            if (admin.validatePassword(password)) {
                System.out.println("Password validated successfully for: " + admin.getUsername());
                // Update last login date
                admin.setLastLoginDate(new Date());
                updateAdmin(admin);
                return admin;
            } else {
                System.out.println("Password validation failed for: " + admin.getUsername());
            }
        } else {
            System.out.println("Admin not found during authentication: " + username);
        }
        return null;
    }

    // Check if any admins exist
    public boolean hasAdmins() {
        if (adminUsers.isEmpty()) {
            // Try to reload from file in case they exist but weren't loaded
            loadAdminUsers();
        }

        boolean hasAdmins = !adminUsers.isEmpty();
        System.out.println("Checking if admins exist: " + hasAdmins + " (count: " + adminUsers.size() + ")");
        return hasAdmins;
    }

    // Create default super admin if none exist
    public boolean createDefaultSuperAdmin() {
        if (!hasAdmins()) {
            System.out.println("No admin users found, creating default super admin");
            Admin superAdmin = new Admin();
            superAdmin.setUsername("admin");
            superAdmin.setPassword("admin123"); // Will be hashed during addAdmin
            superAdmin.setFullName("System Administrator");
            superAdmin.setEmail("admin@example.com");
            superAdmin.setSuperAdmin(true);

            boolean result = addAdmin(superAdmin);
            System.out.println("Default super admin created: " + result);
            return result;
        }
        System.out.println("Admin users already exist, not creating default super admin");
        return false; // Only create if no admins exist
    }
}
