package main.reportes;

/**
 *
 * @author marco
 */
public class ReporteControlEquiposSucursal {

    private int idSucursal;
    private String sucursal;
    private int idEquipo;
    private String equipo;
    private int disponibles;

    public ReporteControlEquiposSucursal() {
    }

    public ReporteControlEquiposSucursal(int idSucursal, String sucursal, int idEquipo, String equipo, int disponibles) {
        this.idSucursal = idSucursal;
        this.sucursal = sucursal;
        this.idEquipo = idEquipo;
        this.equipo = equipo;
        this.disponibles = disponibles;
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

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public int getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }

}
