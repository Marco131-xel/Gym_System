package main.reportes;
/**
 *
 * @author marco
 */
public class ReporteEntrenadorClientes {

    private long dpi;
    private String nombre;
    private String apellido;
    private int totalClientes;

    public ReporteEntrenadorClientes(long dpi, String nombre, String apellido, int totalClientes) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.apellido = apellido;
        this.totalClientes = totalClientes;
    }

    public long getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getTotalClientes() {
        return totalClientes;
    }

}
