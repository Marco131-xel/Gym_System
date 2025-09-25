package main.reportes;

/**
 *
 * @author marco
 */
public class ReporteGasto {
    
    private long dpi;
    private String nombre;
    private String apellido;
    private double gastoTotal;

    public ReporteGasto(long dpi, String nombre, String apellido, double gastoTotal) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.apellido = apellido;
        this.gastoTotal = gastoTotal;
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

    public double getGastoTotal() {
        return gastoTotal;
    }
    
}
