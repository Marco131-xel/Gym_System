package main.models;

import java.sql.Date;

/**
 *
 * @author marco
 */
public class Pago {
    
    private int id;
    private long dpi;
    private String tipo;
    private String descripcion;
    private double monto;
    private Date fechaInicio;
    private Date fechaFin;
    private int id_adicional;

    public Pago() {
    }
    
    // constructor de pago de servicio
    public Pago(long dpi, String tipo, String descripcion, double monto, Date fechaInicio, Date fechaFin) {
        this.dpi = dpi;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.monto = monto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    // constructor de pago adicional
    public Pago(long dpi, String tipo, String descripcion, double monto, Date fechaInicio, Date fechaFin, int id_adicional) {
        this.dpi = dpi;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.monto = monto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.id_adicional = id_adicional;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
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

    public int getId_adicional() {
        return id_adicional;
    }

    public void setId_adicional(int id_adicional) {
        this.id_adicional = id_adicional;
    }
}
