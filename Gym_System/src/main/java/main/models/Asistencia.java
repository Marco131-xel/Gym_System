package main.models;

import java.sql.Timestamp;

/**
 *
 * @author marco
 */
public class Asistencia {
    
    private int id;
    private long dpi_cliente;
    private int id_sucursal;
    private Timestamp fecha_hora;

    public Asistencia() {
    }

    public Asistencia(long dpi_cliente, int id_sucursal, Timestamp fecha_hora) {
        this.dpi_cliente = dpi_cliente;
        this.id_sucursal = id_sucursal;
        this.fecha_hora = fecha_hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDpi_cliente() {
        return dpi_cliente;
    }

    public void setDpi_cliente(long dpi_cliente) {
        this.dpi_cliente = dpi_cliente;
    }

    public int getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(int id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public Timestamp getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Timestamp fecha_hora) {
        this.fecha_hora = fecha_hora;
    }
    
}
