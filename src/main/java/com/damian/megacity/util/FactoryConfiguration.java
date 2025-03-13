package com.damian.megacity.util;

import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Log
public class FactoryConfiguration {
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USERNAME");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static FactoryConfiguration factoryConfiguration;
    private Connection connection;

    private FactoryConfiguration() {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            log.info("Error connecting to the database: " + e.getMessage());
        }
    }

    public static synchronized FactoryConfiguration getFactoryConfiguration() {
        if (factoryConfiguration == null) {
            factoryConfiguration = new FactoryConfiguration();
        }
        return factoryConfiguration;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            log.warning("Failed to reopen database connection: " + e.getMessage());
        }
        return connection;
    }
}