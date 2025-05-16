package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);
    private static HikariDataSource dataSource;

    static {
        try {
            initializeDataSource();
        } catch (Exception e) {
            logger.error("Failed to initialize database connection pool", e);
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    private static void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/car_sales_db");
        config.setUsername("root");
        config.setPassword("root");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        
        // Connection pool settings
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(300000);
        config.setConnectionTimeout(20000);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
} 