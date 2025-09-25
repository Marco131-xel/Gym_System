package main.reportes;

/**
 *
 * @author marco
 */
public class ReportePago {

    private int idPago;
    private String cliente;
    private double monto;
    private String fechaInicio;
    private String fechaFin;

    public ReportePago() {
    }

    public ReportePago(int idPago, String cliente, double monto, String fechaInicio, String fechaFin) {
        this.idPago = idPago;
        this.cliente = cliente;
        this.monto = monto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

}
