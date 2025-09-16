package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.database.DataBase;
import main.models.Cliente;

/**
 *
 * @author marco
 */
public class ClienteDao {

    // funcion para crear cliente
    public void crear(Cliente cliente) {
        String sql = "INSERT INTO cliente (dpi, nombre, apellido, password, telefono, direccion) "
                + "VALUES (?,?,?,?,?,?)";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, cliente.getDpi());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellido());
            stmt.setString(4, cliente.getPassword());
            stmt.setString(5, cliente.getTelefono());
            stmt.setString(6, cliente.getDireccion());

            stmt.executeUpdate();
            System.out.println("Cliente creado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para buscar cliente
    public Cliente buscar(long dpi) {
        String sql = "SELECT * FROM cliente WHERE dpi=?";
        Cliente cliente = null;
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, dpi);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("id_cliente"));
                    cliente.setDpi(rs.getLong("dpi"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido(rs.getString("apellido"));
                    cliente.setPassword(rs.getString("password"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setDireccion(rs.getString("direccion"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    // funcion para lista a los clientes
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection con = DataBase.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setDpi(rs.getLong("dpi"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setPassword(rs.getString("password"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setDireccion(rs.getString("direccion"));
                lista.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // funcion para modificar a los clientes
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre=?, apellido=?, password=?, telefono=?, direccion=? WHERE dpi=?";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getPassword());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getDireccion());
            stmt.setLong(6, cliente.getDpi());

            stmt.executeUpdate();
            System.out.println("Cliente actualizado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // funcion para eliminar clientes
    public int eliminar(long dpi) {
        String sql = "DELETE FROM cliente WHERE dpi=?";
        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, dpi);
            System.out.println("Cliente eliminado");
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // funcion para logiar a los clientes
    public Cliente loginCliente(String nombre, String password) {
        String sql = "SELECT * FROM cliente WHERE nombre = ? AND password = ?";
        Cliente cliente = null;

        try (Connection con = DataBase.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("id_cliente"));
                    cliente.setDpi(rs.getLong("dpi"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido(rs.getString("apellido"));
                    cliente.setPassword(rs.getString("password"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setDireccion(rs.getString("direccion"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }
}
