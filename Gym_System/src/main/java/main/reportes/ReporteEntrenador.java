package main.reportes;

/**
 *
 * @author marco
 */
public class ReporteEntrenador {

    private long dpi;
    private String nombre;
    private String apellido;
    private double valor;

    public ReporteEntrenador() {
    }

    public ReporteEntrenador(long dpi, String nombre, String apellido, double valor) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.apellido = apellido;
        this.valor = valor;
    }

    public long getDpi() {
        return dpi;
    }

    public void setDpi(long dpi) {
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
