package main.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author marco
 */
public class DataBase {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/gym_system";
    private static String USER = "postgres";
    private static String PASSWORD = "1234";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
