package main.models;

import java.sql.Date;

/**
 *
 * @author marco
 */
public class Entrenador_Cliente {
    
    private int asignacion;
    private long dpi_entrenador;
    private long dpi_cliente;
    private Date fechaAsignacion;
    private Date fechaFin;

    public Entrenador_Cliente() {
    }

    public Entrenador_Cliente(long dpi_entrenador, long dpi_cliente, Date fechaAsignacion, Date fechaFin) {
        this.dpi_entrenador = dpi_entrenador;
        this.dpi_cliente = dpi_cliente;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaFin = fechaFin;
    }

    public int getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(int asignacion) {
        this.asignacion = asignacion;
    }

    public long getDpi_entrenador() {
        return dpi_entrenador;
    }

    public void setDpi_entrenador(long dpi_entrenador) {
        this.dpi_entrenador = dpi_entrenador;
    }

    public long getDpi_cliente() {
        return dpi_cliente;
    }

    public void setDpi_cliente(long dpi_cliente) {
        this.dpi_cliente = dpi_cliente;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
}
