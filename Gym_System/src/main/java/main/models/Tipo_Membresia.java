package main.models;

/**
 *
 * @author marco
 */
public class Tipo_Membresia {
    
    private int id;
    private String nombre;
    private double descuento;

    public Tipo_Membresia() {
    }

    public Tipo_Membresia(String nombre, double descuento) {
        this.nombre = nombre;
        this.descuento = descuento;
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

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    
}
