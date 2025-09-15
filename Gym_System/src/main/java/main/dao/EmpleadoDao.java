package main.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.database.DataBase;
import main.models.Empleado;

/**
 *
 * @author marco
 */
public class EmpleadoDao {

    // funcion para crear empleados
    public void crear(Empleado emp) {
        String sql = "INSERT INTO empleado (dpi, nombre, apellido, password, telefono, direccion, id_sucursal, id_rol) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, emp.getDpi());
            stmt.setString(2, emp.getNombre());
            stmt.setString(3, emp.getApellido());
            stmt.setString(4, emp.getPassword());
            stmt.setString(5, emp.getTelefono());
            stmt.setString(6, emp.getDireccion());
            stmt.setInt(7, emp.getIdSucursal());
            stmt.setInt(8, emp.getIdRol());

            stmt.executeUpdate();
            System.out.println("Empleado insertado correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para buscar empleado por DPI
    public Empleado buscarPorDPI(long dpi) {
        String sql = "SELECT * FROM empleado WHERE dpi=?";
        Empleado emp = null;

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, dpi);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    emp = new Empleado();
                    emp.setId(rs.getInt("id_empleado"));
                    emp.setDpi(rs.getLong("dpi"));
                    emp.setNombre(rs.getString("nombre"));
                    emp.setApellido(rs.getString("apellido"));
                    emp.setPassword(rs.getString("password"));
                    emp.setTelefono(rs.getString("telefono"));
                    emp.setDireccion(rs.getString("direccion"));
                    emp.setIdSucursal(rs.getInt("id_sucursal"));
                    emp.setIdRol(rs.getInt("id_rol"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emp;
    }

    // funcion para listar a los empleados
    public List<Empleado> listar() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleado";

        try (Connection con = DataBase.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setId(rs.getInt("id_empleado"));
                emp.setDpi(rs.getLong("dpi"));
                emp.setNombre(rs.getString("nombre"));
                emp.setApellido(rs.getString("apellido"));
                emp.setPassword(rs.getString("password"));
                emp.setTelefono(rs.getString("telefono"));
                emp.setDireccion(rs.getString("direccion"));
                emp.setIdSucursal(rs.getInt("id_sucursal"));
                emp.setIdRol(rs.getInt("id_rol"));
                lista.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // funcion para editar empleados
    public void actualizar(Empleado emp) {
        String sql = "UPDATE empleado SET nombre=?, apellido=?, password=?, "
                + "telefono=?, direccion=?, id_sucursal=?, id_rol=? WHERE dpi=?";

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, emp.getNombre());
            stmt.setString(2, emp.getApellido());
            stmt.setString(3, emp.getPassword());
            stmt.setString(4, emp.getTelefono());
            stmt.setString(5, emp.getDireccion());
            stmt.setInt(6, emp.getIdSucursal());
            stmt.setInt(7, emp.getIdRol());
            stmt.setLong(8, emp.getDpi());

            stmt.executeUpdate();
            System.out.println("Empleado actualizado correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para eliminar empleados
    public void eliminar(int idEmpleado) {
        String sql = "DELETE FROM empleado WHERE id_empleado=?";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            stmt.executeUpdate();
            System.out.println("Empleado eliminado correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para eliminar por dpi
    public int eliminarDPI(long dpi) {
        String sql = "DELETE FROM empleado WHERE dpi=?";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, dpi);
            System.out.println("Empleado eliminado correctamente");
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
