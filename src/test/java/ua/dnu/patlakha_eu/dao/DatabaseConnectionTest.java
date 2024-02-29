package test.java.ua.dnu.patlakha_eu.dao;

import main.java.ua.dnu.patlakha_eu.dao.DatabaseConnection;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class DatabaseConnectionTest {
    @Test
    public void testGetConnection() {
        Connection connection = DatabaseConnection.getConnection();
        assertNotNull(connection, "З'єднання не може бути null");
    }
}
