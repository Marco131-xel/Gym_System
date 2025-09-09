package main.gym_system;

import database.DataBase;
import java.sql.Connection;

/**
 *
 * @author marco
 */
public class Gym_System {

    public static void main(String[] args) {
        try (Connection con  = DataBase.getConnection()) {
            System.out.println("Conexion exitosa");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
