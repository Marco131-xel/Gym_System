package main.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.database.DataBase;
import main.models.Rutina;

/**
 *
 * @author marco
 */
public class RutinaDao {

    // funcion para crear
    public void crear(Rutina rut) {
        String sql = "INSERT INTO rutina (nombre, tipo, fecha_inicio, dpi_entrenador, dpi_cliente) "
                + "VALUES (?,?,?,?,?)";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, rut.getNombre());
            stmt.setString(2, rut.getTipo());
            stmt.setDate(3, rut.getFechaInicio());
            stmt.setLong(4, rut.getDpi_entrenador());
            stmt.setLong(5, rut.getDpi_cliente());

            stmt.executeUpdate();
            System.out.println("creado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para buscar
    public Rutina buscar(int id) {
        String sql = "SELECT * FROM rutina WHERE id_rutina=?";
        Rutina rut = null;

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    rut.setId(rs.getInt("id_rutina"));
                    rut.setNombre(rs.getString("nombre"));
                    rut.setTipo(rs.getString("tipo"));
                    rut.setFechaInicio(rs.getDate("fecha_inicio"));
                    rut.setDpi_entrenador(rs.getLong("dpi_entrenador"));
                    rut.setDpi_cliente(rs.getLong("dpi_cliente"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rut;
    }

    // funcion para listar
    public List<Rutina> listar() {
        List<Rutina> lista = new ArrayList<>();
        String sql = "SELECT * FROM rutina";

        try (Connection con = DataBase.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Rutina rut = new Rutina();
                rut.setId(rs.getInt("id_rutina"));
                rut.setNombre(rs.getString("nombre"));
                rut.setTipo(rs.getString("tipo"));
                rut.setFechaInicio(rs.getDate("fecha_inicio"));
                rut.setDpi_entrenador(rs.getLong("dpi_entrenador"));
                rut.setDpi_cliente(rs.getLong("dpi_cliente"));
                lista.add(rut);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // funcion para editar
    public void actualizar(Rutina rut) {
        String sql = "UPDATE rutina SET nombre=?, tipo=?, fecha_inicio=?, "
                + "dpi_entrenador=?, dpi_cliente=? WHERE id_rutina=?";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, rut.getNombre());
            stmt.setString(2, rut.getTipo());
            stmt.setDate(3, rut.getFechaInicio());
            stmt.setLong(4, rut.getDpi_entrenador());
            stmt.setLong(5, rut.getDpi_cliente());
            stmt.setInt(6, rut.getId());
            
            stmt.executeUpdate();
            System.out.println("actualizado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para eliminar
    public int eliminar(int id) {
        String sql = "DELETE FROM rutina WHERE id_rutina=?";
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
