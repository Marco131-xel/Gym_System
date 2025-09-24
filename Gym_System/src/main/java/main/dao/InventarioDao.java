package main.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.database.DataBase;
import main.models.*;

/**
 *
 * @author marco
 */
public class InventarioDao {
    
        // funcion para crear
    public void crear(Inventario inv) {
        String sql = "INSERT INTO inventario (id_sucursal, id_equipo, cantidad) "
                + "VALUES (?,?,?)";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, inv.getIdSucursal());
            stmt.setInt(2, inv.getIdEquipo());
            stmt.setInt(3, inv.getCantidad());

            stmt.executeUpdate();
            System.out.println("creado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para buscar
    public Inventario buscar(int id) {
        String sql = "SELECT * FROM inventario WHERE id_inventario=?";
        Inventario inv = null;

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    inv = new Inventario();
                    inv.setId(rs.getInt("id_inventario"));
                    inv.setIdSucursal(rs.getInt("id_sucursal"));
                    inv.setIdEquipo(rs.getInt("id_equipo"));
                    inv.setCantidad(rs.getInt("cantidad"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inv;
    }

    // funcion para listar
    public List<Inventario> listar() {
        List<Inventario> lista = new ArrayList<>();
        String sql = "SELECT * FROM inventario";

        try (Connection con = DataBase.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Inventario inv = new Inventario();
                inv.setId(rs.getInt("id_inventario"));
                inv.setIdSucursal(rs.getInt("id_sucursal"));
                inv.setIdEquipo(rs.getInt("id_equipo"));
                inv.setCantidad(rs.getInt("cantidad"));
                lista.add(inv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // funcion para editar
    public void actualizar(Inventario inv) {
        String sql = "UPDATE inventario SET id_sucursal=?, id_equipo=?, cantidad=? "
                + "WHERE id_inventario=?";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, inv.getIdSucursal());
            stmt.setInt(2, inv.getIdEquipo());
            stmt.setInt(3, inv.getCantidad());
            stmt.setInt(4, inv.getId());

            stmt.executeUpdate();
            System.out.println("actualizado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para eliminar
    public int eliminar(int id) {
        String sql = "DELETE FROM inventario WHERE id_inventario=?";
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
