package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.database.DataBase;
import main.models.Equipo;

/**
 *
 * @author marco
 */
public class EquipoDao {

    // funcion para crear equipos
    public void crear(Equipo equi) {
        String sql = "INSERT INTO equipo (nombre, descripcion, tipo) "
                + "VALUES (?,?,?)";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, equi.getNombre());
            stmt.setString(2, equi.getDescripcion());
            stmt.setString(3, equi.getTipo());

            stmt.executeUpdate();
            System.out.println("Equipo creado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para buscar equipo por id
    public Equipo buscar(int id) {
        String sql = "SELECT * FROM equipo WHERE id_equipo=?";
        Equipo equi = null;

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    equi = new Equipo();
                    equi.setId(rs.getInt("id_equipo"));
                    equi.setNombre(rs.getString("nombre"));
                    equi.setDescripcion(rs.getString("descripcion"));
                    equi.setTipo(rs.getString("tipo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return equi;
    }

    // funcion para lista los equipos
    public List<Equipo> listar() {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipo";

        try (Connection con = DataBase.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Equipo equi = new Equipo();
                equi.setId(rs.getInt("id_equipo"));
                equi.setNombre(rs.getString("nombre"));
                equi.setDescripcion(rs.getString("descripcion"));
                equi.setTipo(rs.getString("tipo"));
                lista.add(equi);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // funcion para editar equipos
    public void actualizar(Equipo equi) {
        String sql = "UPDATE equipo SET nombre=?, descripcion=?, tipo=? WHERE id_equipo=?";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, equi.getNombre());
            stmt.setString(2, equi.getDescripcion());
            stmt.setString(3, equi.getTipo());
            stmt.setInt(4, equi.getId());

            stmt.executeUpdate();
            System.out.println("Equipo actualizado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para eliminar equipos
    public int eliminar(int id) {
        String sql = "DELETE FROM equipo WHERE id_equipo=?";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            System.out.println("Equipo eliminado");
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
