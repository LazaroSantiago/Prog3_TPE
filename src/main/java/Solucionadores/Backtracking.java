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

    public Backtracking (Servicios servicios){
        this.procesadores = servicios.getProcesadores();
        this.tareas = servicios.getTareas();
        this.countCriticas = servicios.countCriticas();

        this.solucionFinal = new ArrayList<>(procesadores.size());
        this.solucionActual = new HashSet<>(procesadores.size());
    }

    //La complejidad es:
    // O(n^m) siendo n la cantidad de procesadores y m la cantidad de tareas

    //la solucion es un backtracking clasico:

    //el caso recursivo consiste en obtener la primer tarea de la lista de tareas
        //luego, por cada procesador, revisar si se la puede agregar a ese procesador
            //en caso de que se pueda, se hace backtracking, y al final del backtracking se la remueve del procesador
    //finalmente, se la vuelve a agregar al principio de la lista

    //el caso de corte es que la lista de tareas este vacia
        //si lo esta, se revisa si la solucion actual es mejor que la final
            //en caso de que lo sea, la solucion actual se vuelve la final

    //la solucion va a aparecer en el ArrayList solucionFinal

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
    public boolean verificarSolucion(int countTareas){
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

    public int getTiempoFinal(){
        if (!esPosibleSolucionar)
            return -1;

        return this.tiempoFinal;
    }

    @Override
    public int getMetrica(){
        return this.countEstados;
    }

    @Override
    public String printMetrica(){
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