package main.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.database.DataBase;
import main.models.Asistencia;

/**
 *
 * @author marco
 */
public class AsistenciaDao {

    // funcion para crear
    public void crear(Asistencia asi) {
        String sql = "INSERT INTO asistencia (dpi_cliente, id_sucursal, fecha_hora) "
                + "VALUES (?,?,?)";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, asi.getDpi_cliente());
            stmt.setInt(2, asi.getId_sucursal());
            stmt.setDate(3, asi.getFecha_hora());

            stmt.executeUpdate();
            System.out.println("creado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para buscar
    public Asistencia buscar(int id) {
        String sql = "SELECT * FROM asistencia WHERE id_asistencia=?";
        Asistencia asi = null;

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    asi.setId(rs.getInt("id_asistencia"));
                    asi.setDpi_cliente(rs.getLong("dpi_cliente"));
                    asi.setId_sucursal(rs.getInt("id_sucursal"));
                    asi.setFecha_hora(rs.getDate("fecha_hora"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return asi;
    }

    // funcion para listar
    public List<Asistencia> listar() {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM asistencia";

        try (Connection con = DataBase.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Asistencia asi = new Asistencia();
                asi.setId(rs.getInt("id_asistencia"));
                asi.setDpi_cliente(rs.getLong("dpi_cliente"));
                asi.setId_sucursal(rs.getInt("id_sucursal"));
                asi.setFecha_hora(rs.getDate("fecha_hora"));
                lista.add(asi);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // funcion para editar
    public void actualizar(Asistencia asi) {
        String sql = "UPDATE asistencia SET dpi_cliente=?, id_sucursal=?, "
                + "fecha_hora=? WHERE id_asistencia=?";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, asi.getDpi_cliente());
            stmt.setInt(2, asi.getId_sucursal());
            stmt.setDate(3, asi.getFecha_hora());
            stmt.setInt(4, asi.getId());

            stmt.executeUpdate();
            System.out.println("actualizado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para eliminar
    public int eliminar(int id) {
        String sql = "DELETE FROM # WHERE id_asistencia=?";
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
