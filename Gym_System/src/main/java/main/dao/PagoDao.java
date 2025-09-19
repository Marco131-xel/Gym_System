package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.database.DataBase;
import main.models.Pago;

/**
 *
 * @author marco
 */
public class PagoDao {

    // funcion para crear un pago
    public void crear(Pago pago) {
        double descuento = (pago.getTipo().equals("servicio")) ? getDescuento(pago.getDpi()) : 0.0;
        double montoFinal = pago.getMonto() - (pago.getMonto() * descuento / 100);

        String sql = "INSERT INTO pago (dpi_cliente, tipo, descripcion, monto, fecha_inicio, fecha_fin, id_adicional) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, pago.getDpi());
            stmt.setString(2, pago.getTipo());
            stmt.setString(3, pago.getDescripcion());
            stmt.setDouble(4, montoFinal);
            stmt.setDate(5, pago.getFechaInicio());

            if (pago.getTipo().equals("adicional") && pago.getId_adicional() != null) {
                stmt.setNull(6, java.sql.Types.DATE);
                stmt.setInt(7, pago.getId_adicional());
            } else {
                stmt.setDate(6, pago.getFechaFin());
                stmt.setNull(7, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();
            System.out.println("Pago creado");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para buscar un pago
    public Pago buscar(int id) {
        String sql = "SELECT * FROM pago WHERE id_pago=?";
        Pago pago = null;
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pago.setId(rs.getInt("id_pago"));
                    pago.setDpi(rs.getLong("dpi_cliente"));
                    pago.setTipo(rs.getString("tipo"));
                    pago.setDescripcion(rs.getString("descripcion"));
                    pago.setMonto(rs.getDouble("monto"));
                    pago.setFechaInicio(rs.getDate("fecha_inicio"));
                    pago.setFechaFin(rs.getDate("fecha_fin"));
                    pago.setId_adicional(rs.getInt("id_adicional"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pago;
    }

    // funcion para listar pagos
    public List<Pago> listar() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM pago";

        try (Connection con = DataBase.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pago pago = new Pago();
                pago.setId(rs.getInt("id_pago"));
                pago.setDpi(rs.getLong("dpi_cliente"));
                pago.setTipo(rs.getString("tipo"));
                pago.setDescripcion(rs.getString("descripcion"));
                pago.setMonto(rs.getDouble("monto"));
                pago.setFechaInicio(rs.getDate("fecha_inicio"));
                pago.setFechaFin(rs.getDate("fecha_fin"));
                pago.setId_adicional(rs.getInt("id_adicional"));
                lista.add(pago);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // actualizar pagos
    public void actualizar(Pago pago) {

    }

    // funcion eliminar pago
    public int eliminar(int id) {
        String sql = "DELETE FROM pago WHERE id_pago=?";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            System.out.println("pago eliminado");
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // obtener descuento segun la membresia asignada
    private double getDescuento(long dpi) {
        String sql = "SELECT mt.descuento FROM membresia m "
                + "JOIN membresia_tipo mt ON m.id_tipo = mt.id_tipo "
                + "WHERE m.dpi_cliente = ? AND CURRENT_DATE BETWEEN m.fecha_inicio AND COALESCE(m.fecha_fin, CURRENT_DATE)";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, dpi);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("descuento");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
