package main.models;

/**
 *
 * @author marco
 */
public class Ejercicio {
    
    private int id_ejercicio;
    private String nombre;
    private int series;
    private int repeticiones;
    private int duracionMin;
    private int id_equipo;

    public Ejercicio() {
    }

    public Ejercicio(int id_ejercicio, String nombre, int series, int repeticiones, int duracionMin, int id_equipo) {
        this.id_ejercicio = id_ejercicio;
        this.nombre = nombre;
        this.series = series;
        this.repeticiones = repeticiones;
        this.duracionMin = duracionMin;
        this.id_equipo = id_equipo;
    }

    public int getId_ejercicio() {
        return id_ejercicio;
    }

    public void setId_ejercicio(int id_ejercicio) {
        this.id_ejercicio = id_ejercicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getDuracionMin() {
        return duracionMin;
    }

    public void setDuracionMin(int duracionMin) {
        this.duracionMin = duracionMin;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }
    
    
}
