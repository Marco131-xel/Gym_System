package main.models;

import java.sql.Date;

/**
 *
 * @author marco
 */
public class VistaRutinaEjercicio {

    private int idRutEje;
    private long dpiCliente;
    private String nombreRutina;
    private String tipo;
    private String nombreEjercicio;
    private int orden;
    private Date fechaInicio;

    public int getIdRutEje() {
        return idRutEje;
    }

    public void setIdRutEje(int idRutEje) {
        this.idRutEje = idRutEje;
    }

    public long getDpiCliente() {
        return dpiCliente;
    }

    public void setDpiCliente(long dpiCliente) {
        this.dpiCliente = dpiCliente;
    }

    public String getNombreRutina() {
        return nombreRutina;
    }

    public void setNombreRutina(String nombreRutina) {
        this.nombreRutina = nombreRutina;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreEjercicio() {
        return nombreEjercicio;
    }

    public void setNombreEjercicio(String nombreEjercicio) {
        this.nombreEjercicio = nombreEjercicio;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

}
