package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import main.database.DataBase;
import main.models.*;
import main.reportes.*;

/**
 *
 * @author marco
 */
public class ReportesDao {

    // top clientes mas frecuentes
    public List<ReporteFrecuencia> topClientesFrecuentes() {
        List<ReporteFrecuencia> lista = new ArrayList<>();
        String sql = """
            SELECT c.dpi, c.nombre, c.apellido,
                   COUNT(a.id_asistencia) AS total_asistencias
            FROM cliente c
            JOIN asistencia a ON c.dpi = a.dpi_cliente
            GROUP BY c.dpi, c.nombre, c.apellido
            ORDER BY total_asistencias DESC
            LIMIT 10;
        """;

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new ReporteFrecuencia(
                        rs.getLong("dpi"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("total_asistencias")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 🔹top clientes con mayor gasto
    public List<ReporteGasto> topClientesGasto() {
        List<ReporteGasto> lista = new ArrayList<>();
        String sql = """
            SELECT c.dpi, c.nombre, c.apellido,
                   SUM(p.monto) AS gasto_total
            FROM cliente c
            JOIN pago p ON c.dpi = p.dpi_cliente
            GROUP BY c.dpi, c.nombre, c.apellido
            ORDER BY gasto_total DESC
            LIMIT 10;
        """;

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new ReporteGasto(
                        rs.getLong("dpi"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getDouble("gasto_total")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 🔹clientes con mayor asistencia cruzada
    public List<ReporteAsistenciaCruzada> topAsistenciaCruzada() {
        List<ReporteAsistenciaCruzada> lista = new ArrayList<>();
        String sql = """
            SELECT c.dpi, c.nombre, c.apellido,
                   COUNT(a.id_asistencia) AS total_asistencias,
                   COUNT(DISTINCT a.id_sucursal) AS sucursales_distintas,
                   ROUND(COUNT(DISTINCT a.id_sucursal)::numeric / COUNT(a.id_asistencia), 3) AS ratio_diversidad
            FROM cliente c
            JOIN asistencia a ON c.dpi = a.dpi_cliente
            GROUP BY c.dpi, c.nombre, c.apellido
            HAVING COUNT(a.id_asistencia) > 1
            ORDER BY ratio_diversidad DESC, total_asistencias DESC
            LIMIT 10;
        """;

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new ReporteAsistenciaCruzada(
                        rs.getLong("dpi"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("total_asistencias"),
                        rs.getInt("sucursales_distintas"),
                        rs.getDouble("ratio_diversidad")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /* ENTRENADOR */
    // top entrenador con mas clientes
    public List<ReporteEntrenador> topEntrenadoresMasClientes() {
        String sql = """
            SELECT e.dpi, e.nombre, e.apellido, COUNT(ec.dpi_cliente) AS total_clientes
            FROM empleado e
            JOIN entrenador_cliente ec ON e.dpi = ec.dpi_entrenador
            WHERE e.id_rol = 2
            GROUP BY e.dpi, e.nombre, e.apellido
            ORDER BY total_clientes DESC
            LIMIT 5
        """;

        List<ReporteEntrenador> lista = new ArrayList<>();
        try (Connection con = DataBase.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReporteEntrenador r = new ReporteEntrenador();
                r.setDpi(rs.getLong("dpi"));
                r.setNombre(rs.getString("nombre"));
                r.setApellido(rs.getString("apellido"));
                r.setValor(rs.getInt("total_clientes"));
                lista.add(r);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    // top 5 entrenadores con mayor rutinas
    public List<ReporteEntrenador> topEntrenadoresMasRutinas() {
        String sql = """
            SELECT e.dpi, e.nombre, e.apellido, COUNT(r.id_rutina) AS total_rutinas
            FROM empleado e
            JOIN rutina r ON e.dpi = r.dpi_entrenador
            WHERE e.id_rol = 2
            GROUP BY e.dpi, e.nombre, e.apellido
            ORDER BY total_rutinas DESC
            LIMIT 5
        """;

        List<ReporteEntrenador> lista = new ArrayList<>();
        try (Connection con = DataBase.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReporteEntrenador r = new ReporteEntrenador();
                r.setDpi(rs.getLong("dpi"));
                r.setNombre(rs.getString("nombre"));
                r.setApellido(rs.getString("apellido"));
                r.setValor(rs.getInt("total_rutinas"));
                lista.add(r);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    // ranking de entrenadores con mayor retenciion de clientes
    public List<ReporteEntrenador> rankingEntrenadoresRetencion() {
        String sql = """
            SELECT e.dpi, e.nombre, e.apellido,
                   ROUND(100.0 * SUM(
                       CASE 
                         WHEN ec.fecha_fin IS NULL 
                           AND ec.fecha_asignacion <= NOW() - INTERVAL '1 year'
                         THEN 1 ELSE 0 END
                   ) / COUNT(ec.dpi_cliente), 2) AS retencion
            FROM empleado e
            JOIN entrenador_cliente ec ON e.dpi = ec.dpi_entrenador
            WHERE e.id_rol = 2
            GROUP BY e.dpi, e.nombre, e.apellido
            ORDER BY retencion DESC
        """;

        List<ReporteEntrenador> lista = new ArrayList<>();
        try (Connection con = DataBase.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReporteEntrenador r = new ReporteEntrenador();
                r.setDpi(rs.getLong("dpi"));
                r.setNombre(rs.getString("nombre"));
                r.setApellido(rs.getString("apellido"));
                r.setValor(rs.getDouble("retencion")); // porcentaje
                lista.add(r);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    // rntrenadores con mayor servicios adicionales
    public List<ReporteEntrenador> entrenadoresServiciosAdicionales() {
        String sql = """
            SELECT e.dpi, e.nombre, e.apellido, COUNT(DISTINCT p.dpi_cliente) AS clientes_servicios
            FROM empleado e
            JOIN adicional a ON e.dpi = a.dpi_entrenador
            JOIN pago p ON a.id_adicional = p.id_adicional
            WHERE e.id_rol = 2 AND p.tipo = 'adicional'
            GROUP BY e.dpi, e.nombre, e.apellido
            ORDER BY clientes_servicios DESC
        """;

        List<ReporteEntrenador> lista = new ArrayList<>();
        try (Connection con = DataBase.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReporteEntrenador r = new ReporteEntrenador();
                r.setDpi(rs.getLong("dpi"));
                r.setNombre(rs.getString("nombre"));
                r.setApellido(rs.getString("apellido"));
                r.setValor(rs.getInt("clientes_servicios"));
                lista.add(r);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    /* SUCURSALES */
    // Ranking de sucursales con mas ingresos
    public List<ReporteSucursal> rankingSucursalesIngresos() {
        String sql = """
        SELECT s.id_sucursal, s.nombre,
               SUM(p.monto) AS ingresos_totales
        FROM sucursal s
        JOIN empleado e ON s.id_sucursal = e.id_sucursal
        JOIN adicional a ON e.dpi = a.dpi_entrenador
        JOIN pago p ON a.id_adicional = p.id_adicional
        GROUP BY s.id_sucursal, s.nombre
        ORDER BY ingresos_totales DESC
    """;

        List<ReporteSucursal> lista = new ArrayList<>();
        try (Connection con = DataBase.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReporteSucursal r = new ReporteSucursal();
                r.setIdSucursal(rs.getInt("id_sucursal"));
                r.setNombre(rs.getString("nombre"));
                r.setValor(rs.getDouble("ingresos_totales"));
                lista.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    //  ranking de sucursales por asistencias
    public List<ReporteSucursal> rankingSucursalesAsistencias() {
        String sql = """
        SELECT s.id_sucursal, s.nombre,
               COUNT(a.id_asistencia) AS total_asistencias
        FROM sucursal s
        JOIN asistencia a ON s.id_sucursal = a.id_sucursal
        GROUP BY s.id_sucursal, s.nombre
        ORDER BY total_asistencias DESC
    """;

        List<ReporteSucursal> lista = new ArrayList<>();
        try (Connection con = DataBase.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReporteSucursal r = new ReporteSucursal();
                r.setIdSucursal(rs.getInt("id_sucursal"));
                r.setNombre(rs.getString("nombre"));
                r.setValor(rs.getInt("total_asistencias"));
                lista.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    /* MEMBRESIAS Y PAGOS */
    public List<ReporteMembresia> tiposMembresiasMasVendidas() {
        String sql = """
        SELECT mt.id_tipo, mt.nombre,
               COUNT(m.id_membresia) AS total_vendidas
        FROM membresia_tipo mt
        LEFT JOIN membresia m ON mt.id_tipo = m.id_tipo
        GROUP BY mt.id_tipo, mt.nombre
        ORDER BY total_vendidas DESC
    """;

        List<ReporteMembresia> lista = new ArrayList<>();
        try (Connection con = DataBase.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReporteMembresia r = new ReporteMembresia();
                r.setIdTipo(rs.getInt("id_tipo"));
                r.setNombre(rs.getString("nombre"));
                r.setValor(rs.getInt("total_vendidas"));
                lista.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<ReporteMembresia> ingresosPorTipoMembresia() {
        String sql = """
        SELECT mt.id_tipo, mt.nombre,
               SUM(p.monto) AS ingresos_totales
        FROM membresia_tipo mt
        JOIN membresia m ON mt.id_tipo = m.id_tipo
        JOIN pago p ON m.dpi_cliente = p.dpi_cliente AND p.tipo = 'servicio'
        GROUP BY mt.id_tipo, mt.nombre
        ORDER BY ingresos_totales DESC
    """;

        List<ReporteMembresia> lista = new ArrayList<>();
        try (Connection con = DataBase.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReporteMembresia r = new ReporteMembresia();
                r.setIdTipo(rs.getInt("id_tipo"));
                r.setNombre(rs.getString("nombre"));
                r.setValor(rs.getDouble("ingresos_totales"));
                lista.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<ReportePago> pagosPorFechas(LocalDate inicio, LocalDate fin) {
        String sql = """
        SELECT p.id_pago, c.nombre, c.apellido, p.monto, p.fecha_inicio, p.fecha_fin
        FROM pago p
        JOIN cliente c ON p.dpi_cliente = c.dpi
        WHERE p.fecha_inicio BETWEEN ? AND ?
        ORDER BY p.fecha_inicio
    """;

        List<ReportePago> lista = new ArrayList<>();
        try (Connection con = DataBase.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(inicio));
            ps.setDate(2, java.sql.Date.valueOf(fin));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReportePago r = new ReportePago();
                    r.setIdPago(rs.getInt("id_pago"));
                    r.setCliente(rs.getString("nombre") + " " + rs.getString("apellido"));
                    r.setMonto(rs.getDouble("monto"));
                    r.setFechaInicio(rs.getDate("fecha_inicio").toString());
                    r.setFechaFin(rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toString() : "-");
                    lista.add(r);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<ReporteDistribucion> distribucionMembresiasPorSucursal() {
        String sql = """
        SELECT s.id_sucursal, s.nombre AS sucursal,
               mt.nombre AS tipo_membresia,
               COUNT(m.id_membresia) AS total,
               ROUND(100.0 * COUNT(m.id_membresia) / SUM(COUNT(m.id_membresia)) OVER (PARTITION BY s.id_sucursal), 2) AS porcentaje
        FROM sucursal s
        JOIN empleado e ON s.id_sucursal = e.id_sucursal
        JOIN cliente c ON e.id_sucursal = s.id_sucursal
        JOIN membresia m ON c.dpi = m.dpi_cliente
        JOIN membresia_tipo mt ON m.id_tipo = mt.id_tipo
        GROUP BY s.id_sucursal, s.nombre, mt.nombre
        ORDER BY s.id_sucursal, porcentaje DESC
    """;

        List<ReporteDistribucion> lista = new ArrayList<>();
        try (Connection con = DataBase.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReporteDistribucion r = new ReporteDistribucion();
                r.setIdSucursal(rs.getInt("id_sucursal"));
                r.setSucursal(rs.getString("sucursal"));
                r.setTipoMembresia(rs.getString("tipo_membresia"));
                r.setCantidad(rs.getInt("total"));
                r.setPorcentaje(rs.getDouble("porcentaje"));
                lista.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    /* INVENTARIO */
    // equipos más utilizados
    public List<ReporteEquiposMasUtilizados> equiposMasUtilizados() {
        String sql = """
        SELECT e.id_equipo,
               e.nombre AS equipo,
               COUNT(re.id_rut_eje) AS veces_utilizado
        FROM equipo e
        JOIN ejercicio ex ON e.id_equipo = ex.id_equipo
        JOIN rutina_ejercicio re ON ex.id_ejercicio = re.id_ejercicio
        GROUP BY e.id_equipo, e.nombre
        ORDER BY veces_utilizado DESC
    """;
        List<ReporteEquiposMasUtilizados> lista = new ArrayList<>();

        try (Connection con = DataBase.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReporteEquiposMasUtilizados r = new ReporteEquiposMasUtilizados();
                r.setIdEquipo(rs.getInt("id_equipo"));
                r.setEquipo(rs.getString("equipo"));
                r.setVecesUtilizado(rs.getInt("veces_utilizado"));
                lista.add(r);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    // equipos usados por Premium/VIP
    public List<ReporteEquiposPremiumVip> equiposPremiumVip() {
        String sql = """
        SELECT e.id_equipo,
               e.nombre AS equipo,
               COUNT(re.id_rut_eje) AS veces_utilizado
        FROM membresia m
        JOIN membresia_tipo mt ON m.id_tipo = mt.id_tipo
        JOIN rutina r ON r.dpi_cliente = m.dpi_cliente
        JOIN rutina_ejercicio re ON r.id_rutina = re.id_rutina
        JOIN ejercicio ex ON re.id_ejercicio = ex.id_ejercicio
        JOIN equipo e ON ex.id_equipo = e.id_equipo
        WHERE mt.nombre IN ('Premium','VIP')
        GROUP BY e.id_equipo, e.nombre
        ORDER BY veces_utilizado DESC
    """;
        List<ReporteEquiposPremiumVip> lista = new ArrayList<>();

        try (Connection con = DataBase.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReporteEquiposPremiumVip r = new ReporteEquiposPremiumVip();
                r.setIdEquipo(rs.getInt("id_equipo"));
                r.setEquipo(rs.getString("equipo"));
                r.setVecesUtilizado(rs.getInt("veces_utilizado"));
                lista.add(r);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    // Control de equipos por sucursal
    public List<ReporteControlEquiposSucursal> controlEquiposSucursal() {
        String sql = """
        SELECT s.id_sucursal,
               s.nombre AS sucursal,
               e.id_equipo,
               e.nombre AS equipo,
               i.cantidad AS disponibles
        FROM inventario i
        JOIN sucursal s ON i.id_sucursal = s.id_sucursal
        JOIN equipo e ON i.id_equipo = e.id_equipo
        ORDER BY s.id_sucursal, e.nombre
    """;
        List<ReporteControlEquiposSucursal> lista = new ArrayList<>();

        try (Connection con = DataBase.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReporteControlEquiposSucursal r = new ReporteControlEquiposSucursal();
                r.setIdSucursal(rs.getInt("id_sucursal"));
                r.setSucursal(rs.getString("sucursal"));
                r.setIdEquipo(rs.getInt("id_equipo"));
                r.setEquipo(rs.getString("equipo"));
                r.setDisponibles(rs.getInt("disponibles"));
                lista.add(r);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }
}
