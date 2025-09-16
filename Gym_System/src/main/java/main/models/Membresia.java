package main.models;

import java.sql.Date;

/**
 *
 * @author marco
 */
public class Membresia {
    
    private int id;
    private long dpi;
    private int tipo;
    private Date fechaInicio;
    private Date fechaFin;

    public Membresia() {
    }

    public Membresia(long dpi, int tipo, Date fechaInicio, Date fechaFin) {
        this.dpi = dpi;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDpi() {
        return dpi;
    }

    public void setDpi(long dpi) {
        this.dpi = dpi;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    
}
