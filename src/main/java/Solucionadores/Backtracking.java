package Solucionadores;

import Entidad.Procesador;
import Entidad.Tarea;
import org.example.Servicios;

import java.util.*;

public class Backtracking implements SolucionadorAbstracto {
    private List<Procesador> procesadores;
    private List<Tarea> tareas;

    private ArrayList<Procesador> solucionFinal;
    private HashSet<Procesador> solucionActual;

    private int tiempoActual;
    private int tiempoFinal;
    private boolean esPosibleSolucionar;
    private int countEstados;
    private int countCriticas;

    public Backtracking(Servicios servicios) {
        this.procesadores = servicios.getProcesadores();
        this.tareas = servicios.getTareas();
        this.countCriticas = servicios.countCriticas();

        this.solucionFinal = new ArrayList<>(procesadores.size());
        this.solucionActual = new HashSet<>(procesadores.size());
    }

    //La complejidad es:
    //O(n^m) siendo n la cantidad de procesadores y m la cantidad de tareas

    //La tecnica utilizada es un backtracking clasico, con una condicion de poda
    //Se tiene una lista de solucion final, que es donde va a aparecer el problema resuelto de manera optima
    //Se lleva una solucion actual, que representa cada rama del arbol de soluciones.
    //Al llegar a la hoja de cada rama, se compara la solucion actual con la final y se decide con cual de las dos quedarse.

    //Caso Recursivo
    //El caso recursivo consiste en:
    //Paso 1: Extraer la primer Tarea t de la lista de tareas
    //Verificaciones y poda:
    //Paso 2: Por cada Procesador p, verifico si es posible asignarle t
    //Paso 3: En caso de que sea posible asignar t a p, verifico que la solucion actual no sea peor a la solucion final (Esta seria la poda. Si solucion actual es peor a solucion final, no tiene sentido preocuparme por esos casos. Se que NO hay una solucion optima siguiendo esa rama)
    //Backtracking:
    //Paso 4: Si los chequeos en los pasos 1 y 2 fueron positivos, se hace un llamado recursivo
    //Deshacer cambios:
    //Paso 5: Se remueve t de p
    //Paso 6: Se vuelve a agregar t a la lista de tareas

    //Caso de corte
    //Eventualmente, al remover todas las tareas de la lista de tareas se va a llegar a la condicion de corte.
    //La condicion de corte es, justamente, que la lista de tareas este vacia. Eso significa que se llego a un estado final.
    //Paso 1: Verificar que la lista de tareas este vacia
    //Paso 2: Verificar que la solucion actual sea mejor que la final
    //Paso 3: En caso de que lo sea, la solucion final se vuelve la actual

    //Luego de recorrer todos los estados posibles (a excepcion de los podados, porque es innecesario),
    //la solucion optima va a estar en el ArrayList SolucionFinal.

    public void backtracking(int tiempo) {
        int countTareas = refrezcar(tiempo);

        if (!haySolucion())
            return;

        backtracking();

        this.esPosibleSolucionar = verificarSolucion(countTareas);
    }

    private void backtracking() {
        countEstados++;

        if (tareas.isEmpty()) {
            if (solucionActualEsMejor()) {
                this.deepCopy();
            }
        } else {
            Tarea t = tareas.remove(0);

            for (Procesador p : procesadores) {
                solucionActual.add(p);

                if (p.agregarTarea(t)) {
                    if (solucionActualEsMejor())
                        backtracking();

                    p.quitarTarea();
                }
            }
            tareas.add(0, t);
        }
    }


    private int refrezcar(int tiempo) {
        int countTareas = this.tareas.size();
        this.tiempoFinal = Integer.MAX_VALUE;
        this.countEstados = 0;
        this.tiempoActual = 0;
        Procesador.setTiempo(tiempo);
        return countTareas;
    }

    @Override
    public boolean verificarSolucion(int countTareas) {
        //si se llego a una solucion valida,
        //la cantidad de tareas en solucion final
        //deberia ser igual a la cantidad de tareas con las que se empezo
        int countTareasAsignadas = 0;

        for (Procesador p : solucionFinal)
            countTareasAsignadas += p.getCountTareasAsignadas();

        return countTareasAsignadas == countTareas;
    }


    private boolean solucionActualEsMejor() {
        //obtener tiempo mayor de solucion actual
        int tiempoMayor = 0;

        for (Procesador p : solucionActual) {
            int tiempoProcesador = p.getTiempoEjecucionProcesador();

            if (tiempoProcesador > tiempoMayor)
                tiempoMayor = tiempoProcesador;
        }

        this.tiempoActual = tiempoMayor;
        return tiempoActual < tiempoFinal;
    }

    private void deepCopy() {
        this.solucionFinal.clear();
        this.solucionFinal = new ArrayList<>(solucionActual.size());
        for (Procesador p : solucionActual) {
            this.solucionFinal.add(new Procesador(p));
        }

        this.tiempoFinal = this.tiempoActual;
    }

    @Override
    public boolean haySolucion() {
        return !((this.countCriticas / 2) > procesadores.size());
    }

    public int getTiempoFinal() {
        if (!esPosibleSolucionar)
            return -1;

        return this.tiempoFinal;
    }

    @Override
    public int getMetrica() {
        return this.countEstados;
    }

    @Override
    public String printMetrica() {
        return "Cantidad estados: " + getMetrica();
    }

    @Override
    public String toString() {
        if (!esPosibleSolucionar)
            return "No hay solucion";

        return "Backtracking" +
                solucionFinal +
                "\nTiempo Final = " + tiempoFinal;
    }
}
