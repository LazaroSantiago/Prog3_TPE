package Entidad;

public class Tarea implements Comparable<Tarea> {
    private String idTarea;
    private String nombreTarea;
    private int tiempoEjecucion;
    private boolean esCritica;
    private int nivelPrioridad;

    public Tarea(String idTarea, String nombreTarea, int tiempoEjecucion, boolean esCritica, int nivelPrioridad) {
        this.idTarea = idTarea;
        this.nombreTarea = nombreTarea;
        this.tiempoEjecucion = tiempoEjecucion;
        this.esCritica = esCritica;
        this.setNivelPrioridad(nivelPrioridad);
    }

    public String getIdTarea() {
        return idTarea;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public int getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public boolean esCritica() {
        return esCritica;
    }

    public int getNivelPrioridad() {
        return nivelPrioridad;
    }

    public void setNivelPrioridad(int nivelPrioridad) {
        if (nivelPrioridad >= 100)
            this.nivelPrioridad = 100;
        else if (nivelPrioridad <= 1)
            this.nivelPrioridad = 1;
        else
            this.nivelPrioridad = nivelPrioridad;
    }

    @Override
    public String toString() {
        return idTarea + ", tiempo: " + tiempoEjecucion + ", critica: " + esCritica;
    }

    @Override
    public int compareTo(Tarea t) {
        return Integer.compare(this.tiempoEjecucion, t.tiempoEjecucion);
    }
}
