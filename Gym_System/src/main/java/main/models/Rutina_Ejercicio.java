package main.models;

/**
 *
 * @author marco
 */
public class Rutina_Ejercicio {
    
    private int id_rut_eje;
    private int id_Rutina;
    private int id_Ejercicio;
    private int orde;

    public Rutina_Ejercicio() {
    }

    public Rutina_Ejercicio(int id_Rutina, int id_Ejercicio, int orde) {
        this.id_Rutina = id_Rutina;
        this.id_Ejercicio = id_Ejercicio;
        this.orde = orde;
    }

    public int getId_rut_eje() {
        return id_rut_eje;
    }

    public void setId_rut_eje(int id_rut_eje) {
        this.id_rut_eje = id_rut_eje;
    }

    public int getId_Rutina() {
        return id_Rutina;
    }

    public void setId_Rutina(int id_Rutina) {
        this.id_Rutina = id_Rutina;
    }

    public int getId_Ejercicio() {
        return id_Ejercicio;
    }

    public void setId_Ejercicio(int id_Ejercicio) {
        this.id_Ejercicio = id_Ejercicio;
    }

    public int getOrde() {
        return orde;
    }

    public void setOrde(int orde) {
        this.orde = orde;
    }
    
}
