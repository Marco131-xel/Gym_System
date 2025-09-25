package main.reportes;

/**
 *
 * @author marco
 */
public class ReporteEquiposPremiumVip {

    private int idEquipo;
    private String equipo;
    private int vecesUtilizado;

    public ReporteEquiposPremiumVip() {
    }

    public ReporteEquiposPremiumVip(int idEquipo, String equipo, int vecesUtilizado) {
        this.idEquipo = idEquipo;
        this.equipo = equipo;
        this.vecesUtilizado = vecesUtilizado;
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

    public int getVecesUtilizado() {
        return vecesUtilizado;
    }

    public void setVecesUtilizado(int vecesUtilizado) {
        this.vecesUtilizado = vecesUtilizado;
    }

}
