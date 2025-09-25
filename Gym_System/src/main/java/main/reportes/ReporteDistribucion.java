package main.reportes;

/**
 *
 * @author marco
 */
public class ReporteDistribucion {

    private int idSucursal;
    private String sucursal;
    private String tipoMembresia;
    private int cantidad;
    private double porcentaje;

    public ReporteDistribucion() {
    }

    public ReporteDistribucion(int idSucursal, String sucursal, String tipoMembresia, int cantidad, double porcentaje) {
        this.idSucursal = idSucursal;
        this.sucursal = sucursal;
        this.tipoMembresia = tipoMembresia;
        this.cantidad = cantidad;
        this.porcentaje = porcentaje;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getTipoMembresia() {
        return tipoMembresia;
    }

    public void setTipoMembresia(String tipoMembresia) {
        this.tipoMembresia = tipoMembresia;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

}
