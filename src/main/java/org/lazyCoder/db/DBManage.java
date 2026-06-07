package org.lazyCoder.db;

import org.lazyCoder.config.PropertyLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBManage {

    PropertyLoader propertyLoader = new PropertyLoader();

    private final String url = propertyLoader.getUrl();
    private final String username = propertyLoader.getUsername();
    private final String password = propertyLoader.getPassword();

    public void createDB (String dbName) {

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement stmt = connection.createStatement();
        )
        {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            System.out.println("Database " + dbName + " created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
