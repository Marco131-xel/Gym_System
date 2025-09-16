package main.models;

import java.sql.Date;

/**
 *
 * @author marco
 */
public class MembresiaDetalle {
    
    private int idMembresia;
    private long dpi;
    private String cliente;
    private String tipoMembresia;
    private Date fechaInicio;
    private Date fechaFin;

    public MembresiaDetalle() {
    }

    public MembresiaDetalle(int idMembresia, long dpi, String cliente, String tipoMembresia, Date fechaInicio, Date fechaFin) {
        this.idMembresia = idMembresia;
        this.dpi = dpi;
        this.cliente = cliente;
        this.tipoMembresia = tipoMembresia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(int idMembresia) {
        this.idMembresia = idMembresia;
    }

    public long getDpi() {
        return dpi;
    }

    public void setDpi(long dpi) {
        this.dpi = dpi;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTipoMembresia() {
        return tipoMembresia;
    }

    public void setTipoMembresia(String tipoMembresia) {
        this.tipoMembresia = tipoMembresia;
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
