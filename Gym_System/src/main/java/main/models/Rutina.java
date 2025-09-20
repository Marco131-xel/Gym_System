package main.models;

import java.sql.Date;

/**
 *
 * @author marco
 */
public class Rutina {
    
    private int id;
    private String nombre;
    private String tipo;
    private Date fechaInicio;
    private long dpi_entrenador;
    private long dpi_cliente;

    public Rutina() {
    }

    public Rutina(String nombre, String tipo, Date fechaInicio, long dpi_entrenador, long dpi_cliente) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.dpi_entrenador = dpi_entrenador;
        this.dpi_cliente = dpi_cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
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
    
    
}
