package main.java.ua.dnu.patlakha_eu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String url = "jdbc:sqlserver://" + ConsDB.DB_HOST + "\\" + ConsDB.DB_INSTANCE + ":" +
            ConsDB.DB_PORT + ";databaseName=" + ConsDB.DB_NAME + ";trustServerCertificate=true";
    private static final String username = ConsDB.DB_USER;
    private static final String password = ConsDB.DB_PASSWORD;

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
