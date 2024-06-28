package org.example;

import Entidad.Procesador;
import Entidad.Tarea;
import Helper.CSVHelper;

import java.util.*;

public class Servicios {
    private List<Procesador> procesadores;

    //indice de tareas por ID utilizando un hashmap para optimizar servicio 1
    private Map<String, Tarea> indexTareas;

    //listas de tareas criticas y no criticas para optimizar servicio 2
    private List<Tarea> tareasCriticas;
    private List<Tarea> tareasNoCriticas;

    //arbol rojo-negro para optimizar servicio 3
    private NavigableMap<Integer, List<Tarea>> tareas;

    /*
     * Complejidad temporal:
     * O(n+m) siendo n la cantidad de tareas y m la cantidad de procesadores
     */
    public Servicios(String pathProcesadores, String pathTareas) {
        CSVHelper helper = new CSVHelper(pathProcesadores, pathTareas);

        this.procesadores = helper.getProcesadores();
        this.tareas = helper.getTareasPorPrioridad();
        this.tareasCriticas = helper.getTareasCriticas();
        this.tareasNoCriticas = helper.getTareasNoCriticas();
        this.indexTareas = helper.getIndexTareas();
    }

    /* Complejidad temporal:
     * O(1)
     * El get de un HashMap es O(1).
     * */
    public Tarea servicio1(String ID) {
        if (ID == null)
            return null;

        return indexTareas.get(ID);
    }

    /*
     * Complejidad temporal:
     * O(1)
     * En CSVHelper al crear la lista de tareas tambien se crean otras dos listas con las tareas criticas y no criticas respectivamente.
     * Sencillamente se devuelven esas listas.
     */
    public List<Tarea> servicio2(boolean esCritica) {
        if (esCritica)
            return this.tareasCriticas;

        return this.tareasNoCriticas;
    }

    /*
     * Complejidad temporal:
     * O(n) siendo n las tareas desde prioridadInferior hasta prioridadSuperior
     * Sigue siendo O(n) pero, en promedio va a ser mejor a recorrer todas las tareas linealmente
     */
    public List<Tarea> servicio3(int prioridadInferior, int
            prioridadSuperior) {

        //chequeo defensivamente que mi numero inferior no sea mayor al superior
        if (prioridadInferior > prioridadSuperior)
            return new LinkedList<>();

        //obtengo las tareas de prioridadInferior hasta prioridad superior.
        //(internamente esta implementado como una lista de listas de tarea)
        Collection<List<Tarea>> values = tareas.subMap(prioridadInferior, prioridadSuperior).values();

        //"aplano" las listas de listas en una sola lista
        List<Tarea> result = values.stream().flatMap(Collection::stream).toList();

        //retorno esa lista
        return result;
    }

    public List<Procesador> getProcesadores() {
        return procesadores;
    }

    public List<Tarea> getTareas() {
        Collection<List<Tarea>> values = tareas.values();
        return new LinkedList<>(values.stream().flatMap(Collection::stream).toList());
    }

    public int countCriticas() {
        return this.tareasCriticas.size();
    }
}