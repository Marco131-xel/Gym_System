package main.reportes;

/**
 *
 * @author marco
 */
public class ReporteEntrenadorRutinas {

    private long dpi;
    private String nombre;
    private String apellido;
    private int totalRutinas;

    public ReporteEntrenadorRutinas(long dpi, String nombre, String apellido, int totalRutinas) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.apellido = apellido;
        this.totalRutinas = totalRutinas;
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

    public int getTotalRutinas() {
        return totalRutinas;
    }

}
