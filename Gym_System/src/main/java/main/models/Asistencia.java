package main.models;

import java.sql.Date;

/**
 *
 * @author marco
 */
public class Asistencia {
    
    private int id;
    private long dpi_cliente;
    private int id_sucursal;
    private Date fecha_hora;

    public Asistencia() {
    }

    public Asistencia(int id, long dpi_cliente, int id_sucursal, Date fecha_hora) {
        this.id = id;
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

    public Date getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }
    
}
