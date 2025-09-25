package main.reportes;

/**
 *
 * @author marco
 */
public class ReporteMembresia {

    private int idTipo;
    private String nombre;
    private double valor;

    public ReporteMembresia() {
    }

    public ReporteMembresia(int idTipo, String nombre, double valor) {
        this.idTipo = idTipo;
        this.nombre = nombre;
        this.valor = valor;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
