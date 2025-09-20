package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.database.DataBase;
import main.models.Adicional;

/**
 *
 * @author marco
 */
public class AdicionalDao {

    // crear pagos adicionales
    public void crear(Adicional adi) {
        String sql = "INSERT INTO adicional (nombre, detalles, precio, dpi_entrenador) VALUES (?, ?, ?, ?)";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, adi.getNombre());
            stmt.setString(2, adi.getDetalles());
            stmt.setDouble(3, adi.getPrecio());

            var dpi = String.valueOf(adi.getDpi());

            if (dpi != null) {
                stmt.setLong(4, adi.getDpi());
            } else {
                stmt.setNull(4, java.sql.Types.BIGINT);
            }

            stmt.executeUpdate();
            System.out.println("adicional creado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // buscar pago adicional
    public Adicional buscar(int id) {
        String sql = "SELECT * FROM adicional WHERE id_adicional=?";
        Adicional adi = null;
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    adi.setId(rs.getInt("id_adicional"));
                    adi.setNombre(rs.getString("nombre"));
                    adi.setDetalles(rs.getString("detalles"));
                    adi.setPrecio(rs.getDouble("precio"));
                    adi.setDpi(rs.getLong("dpi_entrenador"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adi;
    }

    // ver lista de adicionales
    public List<Adicional> listar() {
        List<Adicional> lista = new ArrayList<>();
        String sql = "SELECT * FROM adicional";
        try (Connection con = DataBase.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Adicional adi = new Adicional();
                adi.setId(rs.getInt("id_adicional"));
                adi.setNombre(rs.getString("nombre"));
                adi.setDetalles(rs.getString("detalles"));
                adi.setPrecio(rs.getDouble("precio"));
                adi.setDpi(rs.getLong("dpi_entrenador"));
                lista.add(adi);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // actualizar adicional
    public void actualizar(Adicional adi) {
        String sql = "UPDATE adicional SET nombre=?, detalles=?, precio=?, dpi_entrenador=? WHERE id_adicional=?";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, adi.getNombre());
            stmt.setString(2, adi.getDetalles());
            stmt.setDouble(3, adi.getPrecio());

            var dpi = String.valueOf(adi.getDpi());

            if (dpi != null) {
                stmt.setLong(4, adi.getDpi());
            } else {
                stmt.setNull(4, java.sql.Types.BIGINT);
            }
            
            stmt.setInt(5, adi.getId());
            stmt.executeUpdate();
            System.out.println("Adicional actualizado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // eliminar adicional
    public int eliminar(int id) {
        String sql = "DELETE FROM adicional WHERE id_adicional=?";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            System.out.println("adicional eliminado");
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
