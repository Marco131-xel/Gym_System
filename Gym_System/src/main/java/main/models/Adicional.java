package main.models;

/**
 *
 * @author marco
 */
public class Adicional {
    
    private int id;
    private String nombre;
    private String detalles;
    private double precio;
    private long dpi;

    public Adicional() {
    }
    
    // constructor sin entrenador
    public Adicional(String nombre, String detalles, double precio) {
        this.nombre = nombre;
        this.detalles = detalles;
        this.precio = precio;
    }
    
    // contructor con entrenador
    public Adicional(String nombre, String detalles, double precio, long dpi) {
        this.nombre = nombre;
        this.detalles = detalles;
        this.precio = precio;
        this.dpi = dpi;
    }
    
    // modificar un constructor
    public Adicional(int id, String nombre, String detalles, double precio, long dpi) {
        this.id = id;
        this.nombre = nombre;
        this.detalles = detalles;
        this.precio = precio;
        this.dpi = dpi;
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

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public long getDpi() {
        return dpi;
    }

    public void setDpi(long dpi) {
        this.dpi = dpi;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
