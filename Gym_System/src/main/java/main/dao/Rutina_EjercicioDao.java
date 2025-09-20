package main.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.database.DataBase;
import main.models.Rutina_Ejercicio;

/**
 *
 * @author marco
 */
public class Rutina_EjercicioDao {

    // funcion para crear
    public void crear(Rutina_Ejercicio rje) {
        String sql = "INSERT INTO rutina_ejercicio (id_rutina, id_ejercicio, orden) "
                + "VALUES (?,?,?)";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, rje.getId_Rutina());
            stmt.setInt(2, rje.getId_Ejercicio());
            stmt.setInt(3, rje.getOrde());

            stmt.executeUpdate();
            System.out.println("creado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para buscar
    public Rutina_Ejercicio buscar(int id) {
        String sql = "SELECT * FROM rutina_ejercicio WHERE id_rut_eje=?";
        Rutina_Ejercicio rje = null;

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    rje.setId_rut_eje(rs.getInt("id_rut_eje"));
                    rje.setId_Rutina(rs.getInt("id_rutina"));
                    rje.setId_Ejercicio(rs.getInt("id_ejercicio"));
                    rje.setOrde(rs.getInt(rs.getInt("orden")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rje;
    }

    // funcion para listar
    public List<Rutina_Ejercicio> listar() {
        List<Rutina_Ejercicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM rutina_ejercicio";

        try (Connection con = DataBase.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Rutina_Ejercicio rje = new Rutina_Ejercicio();
                rje.setId_rut_eje(rs.getInt("id_rut_eje"));
                rje.setId_Rutina(rs.getInt("id_rutina"));
                rje.setId_Ejercicio(rs.getInt("id_ejercicio"));
                rje.setOrde(rs.getInt(rs.getInt("orden")));
                lista.add(rje);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // funcion para editar
    public void actualizar(Rutina_Ejercicio rje) {
        String sql = "UPDATE rutina_ejercicio SET id_rutina=?, id_ejercicio=?, orden=? WHERE id_rut_eje=?";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, rje.getId_Rutina());
            stmt.setInt(2, rje.getId_Ejercicio());
            stmt.setInt(3, rje.getOrde());
            stmt.setInt(4, rje.getId_rut_eje());

            stmt.executeUpdate();
            System.out.println("actualizado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para eliminar
    public int eliminar(int id) {
        String sql = "DELETE FROM rutina_ejercicio WHERE id_rut_eje=?";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            System.out.println("eliminado");
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
