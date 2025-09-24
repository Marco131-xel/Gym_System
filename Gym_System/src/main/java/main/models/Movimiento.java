package main.models;

import java.sql.Timestamp;

/**
 *
 * @author marco
 */
public class Movimiento {
    private int id_movimiento;
    private int id_equipo;
    private int cantidad;
    private int origenSuc;
    private int destinoSuc;
    private Timestamp fecha_hora;
    private String motivo;

    public Movimiento() {
    }
    
    // enviar repuesto
    public Movimiento(int id_equipo, int cantidad, int origenSuc, int destinoSuc, Timestamp fecha_hora, String motivo) {
        this.id_equipo = id_equipo;
        this.cantidad = cantidad;
        this.origenSuc = origenSuc;
        this.destinoSuc = destinoSuc;
        this.fecha_hora = fecha_hora;
        this.motivo = motivo;
    }
    
    // para modificar el envio
    public Movimiento(int id_movimiento, int id_equipo, int cantidad, int origenSuc, int destinoSuc, Timestamp fecha_hora, String motivo) {
        this.id_movimiento = id_movimiento;
        this.id_equipo = id_equipo;
        this.cantidad = cantidad;
        this.origenSuc = origenSuc;
        this.destinoSuc = destinoSuc;
        this.fecha_hora = fecha_hora;
        this.motivo = motivo;
    }

    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getOrigenSuc() {
        return origenSuc;
    }

    public void setOrigenSuc(int origenSuc) {
        this.origenSuc = origenSuc;
    }

    public int getDestinoSuc() {
        return destinoSuc;
    }

    public void setDestinoSuc(int destinoSuc) {
        this.destinoSuc = destinoSuc;
    }

    public Timestamp getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Timestamp fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
}
