package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import main.database.DataBase;
import main.models.Movimiento;

/**
 *
 * @author marco
 */
public class MovimientoDao {

    // funcion para crear movimientos
    public void crear(Movimiento mov) {
        String sql = "INSERT INTO movimiento_inventario (id_equipo, cantidad, origen_sucursal, "
                + "destino_sucursal, fecha, motivo) VALUES (?,?,?,?,?,?)";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, mov.getId_equipo());
            stmt.setInt(2, mov.getCantidad());
            stmt.setInt(3, mov.getOrigenSuc());
            stmt.setInt(4, mov.getDestinoSuc());
            if (mov.getFecha_hora() != null) {
                stmt.setTimestamp(5, mov.getFecha_hora());
            } else {
                stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            }
            stmt.setString(6, mov.getMotivo());
            
            stmt.executeUpdate();
            System.out.println("Movimiento creado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para buscar movimiento por id
    public Movimiento buscar(int id) {
        String sql = "SELECT * FROM movimiento_inventario WHERE id_movimiento=?";
        Movimiento mov = null;

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    mov = new Movimiento();
                    mov.setId_movimiento(rs.getInt("id_movimiento"));
                    mov.setId_equipo(rs.getInt("id_equipo"));
                    mov.setCantidad(rs.getInt("cantidad"));
                    mov.setOrigenSuc(rs.getInt("origen_sucursal"));
                    mov.setDestinoSuc(rs.getInt("destino_sucursal"));
                    mov.setFecha_hora(rs.getTimestamp("fecha_hora"));
                    mov.setMotivo(rs.getString("motivo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mov;
    }

    // funcion para lista los movimientos
    public List<Movimiento> listar() {
        List<Movimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimiento_inventario";

        try (Connection con = DataBase.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Movimiento mov = new Movimiento();
                mov.setId_movimiento(rs.getInt("id_movimiento"));
                mov.setId_equipo(rs.getInt("id_equipo"));
                mov.setCantidad(rs.getInt("cantidad"));
                mov.setOrigenSuc(rs.getInt("origen_sucursal"));
                mov.setDestinoSuc(rs.getInt("destino_sucursal"));
                mov.setFecha_hora(rs.getTimestamp("fecha_hora"));
                mov.setMotivo(rs.getString("motivo"));
                lista.add(mov);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // funcion para editar equipos
    public void actualizar(Movimiento mov) {
        String sql = "UPDATE movimiento_inventario SET id_equipo=?, cantidad=?, origen_sucursal=?, "
                + "destino_sucursal=?, fecha=?, motivo=? WHERE id_movimiento=?";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, mov.getId_equipo());
            stmt.setInt(2, mov.getCantidad());
            stmt.setInt(3, mov.getOrigenSuc());
            stmt.setInt(4, mov.getDestinoSuc());
            stmt.setTimestamp(5, mov.getFecha_hora());
            stmt.setString(6, mov.getMotivo());
            stmt.setInt(7, mov.getId_movimiento());

            stmt.executeUpdate();
            System.out.println("Movimiento actualizado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para eliminar movimientos
    public int eliminar(int id) {
        String sql = "DELETE FROM movimiento_inventario WHERE id_movimiento=?";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            System.out.println("Movimiento eliminado");
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
