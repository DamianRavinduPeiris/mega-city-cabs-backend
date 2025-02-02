package com.damian.megacity.util;

import lombok.Getter;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Getter
@Log
public class FactoryConfiguration {
    private static final String URL = "jdbc:mysql://localhost:3306/megacity";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
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
}