package org.example;

import Entidad.Procesador;
import Entidad.Tarea;
import Factory.ProcesadorFactory;
import Factory.TareaFactory;
import Helper.CSVHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Servicios {
    private List<Procesador> procesadores;
    private List<Tarea> tareas;
    private int countTareasCriticas;

    /*
     * Complejidad temporal:
     * O(n)
     */
    public Servicios(String pathProcesadores, String pathTareas) {
        CSVHelper<Procesador> helperProcesador = new CSVHelper<Procesador>(ProcesadorFactory.getInstance());
        CSVHelper<Tarea> helperTarea = new CSVHelper<Tarea>(TareaFactory.getInstance());

        this.procesadores = helperProcesador.crearLista(pathProcesadores);
        this.tareas = helperTarea.crearLista(pathTareas);
        this.countTareasCriticas = TareaFactory.getCountCriticas();
    }

    /* Complejidad temporal:
     * O(n)
     * aunque se puede cambiar la estructura a un hashset para que sea O(1) */
    public Tarea servicio1(String ID) {
        for (Tarea t : tareas)
            if (t.getIdTarea().equals(ID))
                return t;

        return null;
    }

    /*
     * Complejidad temporal:
     * O(n)
     */
    public List<Tarea> servicio2(boolean esCritica) {
        List<Tarea> result = new LinkedList<>();

        for (Tarea t : tareas)
            if (t.esCritica() == esCritica)
                result.add(t);

        return result;
    }

    /*
     * Complejidad temporal del constructor:
     * O(n)
     */
    public List<Tarea> servicio3(int prioridadInferior, int
            prioridadSuperior) {
        List<Tarea> result = new ArrayList<>();

        for (Tarea t : tareas)
            if ((t.getNivelPrioridad() >= prioridadInferior) &&
                    (t.getNivelPrioridad() <= prioridadSuperior))
                result.add(t);

        return result;
    }

    public List<Procesador> getProcesadores() {
        return procesadores;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }
}