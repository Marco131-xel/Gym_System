package main.models;

/**
 *
 * @author marco
 */
public class Inventario {
    
    private int id;
    private int idSucursal;
    private int idEquipo;
    private int cantidad;

    public Inventario() {
    }

    public Inventario(int idSucursal, int idEquipo, int cantidad) {
        this.idSucursal = idSucursal;
        this.idEquipo = idEquipo;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
