package main.reportes;

/**
 *
 * @author marco
 */
public class ReporteAsistenciaCruzada {
    
    private long dpi;
    private String nombre;
    private String apellido;
    private int totalAsistencias;
    private int sucursalesDistintas;
    private double ratioDiversidad;

    public ReporteAsistenciaCruzada(long dpi, String nombre, String apellido, int totalAsistencias, int sucursalesDistintas, double ratioDiversidad) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.apellido = apellido;
        this.totalAsistencias = totalAsistencias;
        this.sucursalesDistintas = sucursalesDistintas;
        this.ratioDiversidad = ratioDiversidad;
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

    public int getSucursalesDistintas() {
        return sucursalesDistintas;
    }

    public double getRatioDiversidad() {
        return ratioDiversidad;
    }
    
    
    
}
