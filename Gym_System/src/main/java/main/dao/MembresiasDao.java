package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.database.DataBase;
import main.models.Membresia;
import main.models.MembresiaDetalle;

/**
 *
 * @author marco
 */
public class MembresiasDao {

    public void crear(Membresia mem) {
        String sql = "INSERT INTO membresia (dpi_cliente, id_tipo, fecha_inicio, fecha_fin) "
                + "VALUES (?,?,?,?)";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, mem.getDpi());
            stmt.setInt(2, mem.getTipo());
            stmt.setDate(3, mem.getFechaInicio());
            stmt.setDate(4, mem.getFechaFin());

            stmt.executeUpdate();
            System.out.println("Membresia creada");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Membresia buscar(int id) {
        String sql = "SELECT * FROM membresia WHERE id_membresia=?";
        Membresia mem = null;

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    mem = new Membresia();
                    mem.setId(rs.getInt("id_membresia"));
                    mem.setDpi(rs.getLong("dpi_cliente"));
                    mem.setTipo(rs.getInt("id_tipo"));
                    mem.setFechaInicio(rs.getDate("fecha_inicio"));
                    mem.setFechaFin(rs.getDate("fecha_fin"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mem;
    }

    public List<MembresiaDetalle> listarMembresias() {
        List<MembresiaDetalle> lista = new ArrayList<>();
        String sql = "SELECT m.id_membresia, c.dpi, "
                + "c.nombre || ' ' || c.apellido AS cliente, "
                + "mt.nombre AS tipo_membresia, "
                + "m.fecha_inicio, m.fecha_fin "
                + "FROM membresia m "
                + "JOIN cliente c ON m.dpi_cliente = c.dpi "
                + "JOIN membresia_tipo mt ON m.id_tipo = mt.id_tipo";

        try (Connection conn = DataBase.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MembresiaDetalle md = new MembresiaDetalle(
                        rs.getInt("id_membresia"),
                        rs.getLong("dpi"),
                        rs.getString("cliente"),
                        rs.getString("tipo_membresia"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin")
                );
                lista.add(md);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void actualizar(Membresia mem) {
        String sql = "UPDATE membresia SET id_tipo=?, fecha_inicio=?, fecha_fin=? WHERE dpi_cliente=?";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, mem.getTipo());
            stmt.setDate(2, mem.getFechaInicio());
            stmt.setDate(3, mem.getFechaFin());
            stmt.setLong(4, mem.getDpi());

            stmt.executeUpdate();
            System.out.println("Membresia actualizada");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int eliminar(int id) {
        String sql = "DELETE FROM membresia WHERE id=?";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            System.out.println("membresia eliminado");
            return stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
