package main.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.database.DataBase;
import main.models.Entrenador_Cliente;

/**
 *
 * @author marco
 */
public class Entrenador_ClienteDao {

    // funcion para crear
    public void crear(Entrenador_Cliente ec) {
        String sql = "INSERT INTO entrenador_cliente (dpi_entrenador, dpi_cliente, fecha_asignacion, fecha_fin)"
                + "VALUES (?,?,?,?);";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, ec.getDpi_entrenador());
            stmt.setLong(2, ec.getDpi_cliente());
            stmt.setDate(3, ec.getFechaAsignacion());
            stmt.setDate(4, ec.getFechaFin());

            stmt.executeUpdate();
            System.out.println("creado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para buscar
    public Entrenador_Cliente buscar(int asignacion) {
        String sql = "SELECT * FROM entrenador_cliente WHERE id_asignacion=?";
        Entrenador_Cliente ec = null;

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, asignacion);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ec.setAsignacion(rs.getInt("id_asignacino"));
                    ec.setDpi_entrenador(rs.getLong("dpi_entrenador"));
                    ec.setDpi_cliente(rs.getLong("dpi_cliente"));
                    ec.setFechaAsignacion(rs.getDate("fecha_asignacion"));
                    ec.setFechaFin(rs.getDate("fecha_fin"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ec;
    }

    // funcion para listar
    public List<Entrenador_Cliente> listar() {
        List<Entrenador_Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrenador_cliente";

        try (Connection con = DataBase.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Entrenador_Cliente ec = new Entrenador_Cliente();
                ec.setAsignacion(rs.getInt("id_asignacion"));
                ec.setDpi_entrenador(rs.getLong("dpi_entrenador"));
                ec.setDpi_cliente(rs.getLong("dpi_cliente"));
                ec.setFechaAsignacion(rs.getDate("fecha_asignacion"));
                ec.setFechaFin(rs.getDate("fecha_fin"));
                lista.add(ec);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // funcion para editar
    public void actualizar(Entrenador_Cliente ec) {
        String sql = "UPDATE entrenador_cliente SET dpi_entrenador=?, dpi_cliente=?, "
                + "fecha_asignacion=?, fecha_fin=? WHERE id_asignacion=?";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, ec.getDpi_entrenador());
            stmt.setLong(2, ec.getDpi_cliente());
            stmt.setDate(3, ec.getFechaAsignacion());
            stmt.setDate(4, ec.getFechaFin());
            stmt.setInt(5, ec.getAsignacion());
            
            stmt.executeUpdate();
            System.out.println("actualizado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para eliminar
    public int eliminar(int asignacion) {
        String sql = "DELETE FROM entrenador_cliente WHERE id_asignacion=?";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, asignacion);
            System.out.println("eliminado");
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
