package main.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.database.DataBase;
import main.models.Ejercicio;

/**
 *
 * @author marco
 */
public class EjercicioDao {

    // funcion para crear
    public void crear(Ejercicio eje) {
        String sql = "INSERT INTO ejercicio (nombre, series, repeticiones, duracion_min, id_equipo) "
                + "VALUES (?,?,?,?,?)";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, eje.getNombre());
            stmt.setInt(2, eje.getSeries());
            stmt.setInt(3, eje.getRepeticiones());
            stmt.setInt(4, eje.getDuracionMin());
            stmt.setInt(5, eje.getId_equipo());

            stmt.executeUpdate();
            System.out.println("creado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para buscar
    public Ejercicio buscar(int id) {
        String sql = "SELECT * FROM ejercicio WHERE id_ejercicio=?";
        Ejercicio eje = null;

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    eje.setId_ejercicio(rs.getInt("id_ejercicio"));
                    eje.setNombre(rs.getString("nombre"));
                    eje.setSeries(rs.getInt("series"));
                    eje.setRepeticiones(rs.getInt("repeticiones"));
                    eje.setDuracionMin(rs.getInt("duracion_min"));
                    eje.setId_equipo(rs.getInt("id_equipo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eje;
    }

    // funcion para listar
    public List<Ejercicio> listar() {
        List<Ejercicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM ejercicio";

        try (Connection con = DataBase.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Ejercicio eje = new Ejercicio();
                eje.setId_ejercicio(rs.getInt("id_ejercicio"));
                eje.setNombre(rs.getString("nombre"));
                eje.setSeries(rs.getInt("series"));
                eje.setRepeticiones(rs.getInt("repeticiones"));
                eje.setDuracionMin(rs.getInt("duracion_min"));
                eje.setId_equipo(rs.getInt("id_equipo"));
                lista.add(eje);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // funcion para editar
    public void actualizar(Ejercicio eje) {
        String sql = "UPDATE ejercicio SET nombre=?, series=?, repeticiones=?, "
                + "duracion_min=?, id_equipo=? WHERE id_ejercicio=?";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, eje.getNombre());
            stmt.setInt(2, eje.getSeries());
            stmt.setInt(3, eje.getRepeticiones());
            stmt.setInt(4, eje.getDuracionMin());
            stmt.setInt(5, eje.getId_equipo());
            stmt.setInt(6, eje.getId_ejercicio());

            stmt.executeUpdate();
            System.out.println("actualizado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para eliminar
    public int eliminar(int id) {
        String sql = "DELETE FROM ejercicio WHERE id_ejercicio=?";
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
