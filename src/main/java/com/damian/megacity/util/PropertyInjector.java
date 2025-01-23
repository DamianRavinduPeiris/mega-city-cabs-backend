package com.damian.megacity.util;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.Properties;

@Log
public class PropertyInjector {
    public static Properties injectProperties() {
        var properties = new Properties();
        var resource = PropertyInjector.class.getResource("/hibernate.properties");
        try {
            if (resource != null) {
                properties.load(resource.openStream());
                return properties;
            }
        } catch (IOException e) {
            log.info("Error loading properties file: " + e.getMessage());

        }
        throw new RuntimeException("Properties file not found.");
    }
}