package org.lazyCoder.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBManage {

    private final String url = "jdbc:mysql://localhost:3306/";
    private final String username = "root";
    private final String password = "Abhay#12345";

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
