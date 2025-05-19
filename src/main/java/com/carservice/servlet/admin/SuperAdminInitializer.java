package com.carservice.servlet.admin;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.carservice.model.admin.AdminManager;

/**
 * Context listener that initializes the super admin account when the application starts
 */
@WebListener
public class SuperAdminInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing application - checking for super admin account");
        AdminManager adminManager = new AdminManager(sce.getServletContext());

        if (adminManager.createDefaultSuperAdmin()) {
            System.out.println("Created default super admin account - username: admin, password: admin123");
        } else {
            System.out.println("Super admin account already exists, no default account created");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup code if needed
    }
}