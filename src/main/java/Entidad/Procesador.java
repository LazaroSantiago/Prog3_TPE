package Entidad;

import java.util.LinkedList;

public class Procesador {
    private String idProcesador;
    private String codigoProcesador;
    private boolean estaRefrigerado;
    private int anioFuncionamiento;
    private LinkedList<Tarea> tareasAsignadas;

    private int countTareasCriticasAsignadas;
    private int tiempoEjecucionProcesador;
    private static int tiempo;

    public Procesador(Procesador p){
        this(p.idProcesador, p.getCodigoProcesador(), p.estaRefrigerado, p.anioFuncionamiento, p.tareasAsignadas, p.countTareasCriticasAsignadas, p.tiempoEjecucionProcesador);
    }

    public Procesador(String idProcesador, String codigoProcesador, boolean estaRefrigerado, int anioFuncionamiento) {
        this.idProcesador = idProcesador;
        this.codigoProcesador = codigoProcesador;
        this.estaRefrigerado = estaRefrigerado;
        this.anioFuncionamiento = anioFuncionamiento;
        this.tareasAsignadas = new LinkedList<>();
    }

    public Procesador(String idProcesador, String codigoProcesador,
                           boolean estaRefrigerado, int anioFuncionamiento,
                           LinkedList<Tarea> tareasAsignadas,
                           int countTareasCriticasAsignadas, int tiempoEjecucionProcesador) {
        this.idProcesador = idProcesador;
        this.codigoProcesador = codigoProcesador;
        this.estaRefrigerado = estaRefrigerado;
        this.anioFuncionamiento = anioFuncionamiento;
        this.tareasAsignadas = new LinkedList<>(tareasAsignadas);
        this.countTareasCriticasAsignadas = countTareasCriticasAsignadas;
        this.tiempoEjecucionProcesador = tiempoEjecucionProcesador;
    }

    public String getIdProcesador() {
        return idProcesador;
    }

    public String getCodigoProcesador() {
        return codigoProcesador;
    }

    public boolean estaRefrigerado() {
        return estaRefrigerado;
    }

    public int getAnioFuncionamiento() {
        return anioFuncionamiento;
    }

    public int getTiempoEjecucionProcesador() {
        return this.tiempoEjecucionProcesador;
    }

    public static void setTiempo(int nuevoTiempo){
        tiempo = nuevoTiempo;
    }

    public boolean agregarTarea(Tarea t){
        if (validarAgregar(t)){
            tareasAsignadas.add(t);
            tiempoEjecucionProcesador += t.getTiempoEjecucion();

            if (t.esCritica())
                countTareasCriticasAsignadas++;

            return true;
        }
        return false;
    }

    public Tarea quitarTarea(){
        Tarea t = this.tareasAsignadas.removeLast();
        tiempoEjecucionProcesador -= t.getTiempoEjecucion();
        return t;
    }

    public Tarea quitarTarea(int i){
        Tarea t = this.tareasAsignadas.remove(i);
        tiempoEjecucionProcesador -= t.getTiempoEjecucion();
        return t;
    }

    private boolean validarAgregar(Tarea t) {
        if (!this.estaRefrigerado())
            if (!validarRefrigerado(t))
                return false;

        if (t.esCritica())
            return validarCritica();

        return true;
    }

    private boolean validarCritica() {
        return countTareasCriticasAsignadas <= 2;
    }

    private boolean validarRefrigerado(Tarea t) {
        //reviso tener tiempo disponible para agregar
        return ((tiempoEjecucionProcesador + t.getTiempoEjecucion()) <= tiempo);
    }

    public int getCountTareasAsignadas(){
        return this.tareasAsignadas.size();
    }

    @Override
    public String toString() {
        return "\nProcesador: " + idProcesador + ". " + "Tareas: " + tareasAsignadas;
    }

}
