package main.reportes;

/**
 *
 * @author marco
 */
public class ReporteEntrenadorAdicionales {

    private long dpi;
    private String nombre;
    private String apellido;
    private int clientesConsumidores;

    public ReporteEntrenadorAdicionales(long dpi, String nombre, String apellido, int clientesConsumidores) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.apellido = apellido;
        this.clientesConsumidores = clientesConsumidores;
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

    public int getClientesConsumidores() {
        return clientesConsumidores;
    }

}
