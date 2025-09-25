package main.reportes;
/**
 *
 * @author marco
 */
public class ReporteEntrenadorRetencion {

    private long dpi;
    private String nombre;
    private String apellido;
    private int totalAsignaciones;
    private int asignacionesRetenidas;
    private double porcentajeRetencion;

    public ReporteEntrenadorRetencion(long dpi, String nombre, String apellido, int totalAsignaciones, int asignacionesRetenidas, double porcentajeRetencion) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.apellido = apellido;
        this.totalAsignaciones = totalAsignaciones;
        this.asignacionesRetenidas = asignacionesRetenidas;
        this.porcentajeRetencion = porcentajeRetencion;
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

    public int getTotalAsignaciones() {
        return totalAsignaciones;
    }

    public int getAsignacionesRetenidas() {
        return asignacionesRetenidas;
    }

    public double getPorcentajeRetencion() {
        return porcentajeRetencion;
    }

}
