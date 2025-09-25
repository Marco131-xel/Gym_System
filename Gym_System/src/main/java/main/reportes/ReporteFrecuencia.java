package main.reportes;

/**
 *
 * @author marco
 */
public class ReporteFrecuencia {
    private long dpi;
    private String nombre;
    private String apellido;
    private int totalAsistencias;

    public ReporteFrecuencia() {
    }

    public ReporteFrecuencia(long dpi, String nombre, String apellido, int totalAsistencias) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.apellido = apellido;
        this.totalAsistencias = totalAsistencias;
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

    public int getTotalAsistencias() {
        return totalAsistencias;
    }
    
}
