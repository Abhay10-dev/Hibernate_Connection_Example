package org.lazyCoder.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    private String url;
    private String username;
    private String password;

    public PropertyLoader() {
        loadProperties();
    }

    private void loadProperties() {
        Properties properties = new Properties();

        // Read file from the classpath resources folder
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties")) {

            if (inputStream == null) {
                System.err.println("Error: Unable to find db.properties in resources folder.");
                return;
            }

            // Load all the properties
            properties.load(inputStream);

            // Assign properties to the fields
            this.url = properties.getProperty("db.url");
            this.username = properties.getProperty("db.username");
            this.password = properties.getProperty("db.password");

        } catch (IOException e) {
            System.err.println("Exception occurred while reading db.properties: " + e.getMessage());
        }
    }

    // Getters
    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}