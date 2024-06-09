package Solucionadores;

import Entidad.Procesador;
import Entidad.Tarea;

import java.util.ArrayList;
import java.util.List;

public class Greedy implements SolucionadorAbstracto {
    List<Procesador> procesadores;
    List<Tarea> tareas;
    private boolean esPosibleSolucionar;
    private int countCandidatos;

    public Greedy(List<Procesador> procesadores, List<Tarea> tareas) {
        this.procesadores = procesadores;
        this.tareas = tareas;
    }

    //complejidad O(n*m) siendo n la cantidad de tareas y m la cantidad de procesadores

    //La solucion consiste en:
    //por cada tarea t
        //obtener el procesador con menor tiempo de ejecucion
            //si se puede agregar t a ese procesador
                //se la agrega
            //caso contrario
                //se revisan los demas procesadores para ver si se puede en algun otro
                    //si no se puede en ninguno
                        //es que no hay solucion
    public void greedy(int tiempo) {
        if (!haySolucion())
            return;

        int countTareas = refrezcar(tiempo);

        while (!tareas.isEmpty()) {
            Tarea t = tareas.removeFirst();

            int indiceProcesador = getIndiceProcesadorMenosCargado();
            Procesador p = procesadores.get(indiceProcesador);

            if (!agregarTarea(t, p)){
                esPosibleSolucionar = seleccionar(indiceProcesador, t);

                if (!esPosibleSolucionar)
                    return;
            }
        }
        esPosibleSolucionar = verificarSolucion(countTareas);
    }

    private int refrezcar(int tiempo) {
        int countTareas = tareas.size();
        this.countCandidatos = 0;
        Procesador.setTiempo(tiempo);
        return countTareas;
    }

    private boolean agregarTarea(Tarea t, Procesador p) {
        if (p.agregarTarea(t)){
            countCandidatos++;
            return true;
        }

        return false;
    }

    private int getIndiceProcesadorMenosCargado() {
        int tiempoMenor = Integer.MAX_VALUE;
        int indiceProcesador = 0;

        for (int i = 0; i < procesadores.size(); i++) {
            if (procesadores.get(i).getTiempoEjecucionProcesador() < tiempoMenor) {
                tiempoMenor = procesadores.get(i).getTiempoEjecucionProcesador();
                indiceProcesador = i;
            }
        }

        return indiceProcesador;
    }

    private int getTiempoFinal() {
        int tiempoMayor = 0;

        for (Procesador p : procesadores) {
            int tiempoProcesador = p.getTiempoEjecucionProcesador();

            if (tiempoProcesador > tiempoMayor)
                tiempoMayor = tiempoProcesador;
        }

        return tiempoMayor;
    }

    private boolean seleccionar(int indiceProcesador, Tarea t) {
        //iterar por el resto de los procesadores
            //si en alguna instancia se pudo agregar,
                //retornar true
            //si no se pudo
                //retornar false

        int size = procesadores.size();
        for (int i = 0; i < size; i++) {
            int currentIndex = (indiceProcesador + i) % size;
            Procesador procesadorActual = procesadores.get(currentIndex);

            if (agregarTarea(t, procesadorActual)){
                return true;
            }
        }

        return false;
    }

    @Override
    public int getMetrica(){
        return this.countCandidatos;
    }

    @Override
    public String printMetrica(){
        return "Cantidad candidatos: " + getMetrica();
    }

    @Override
    public boolean haySolucion() {
        return !((Tarea.getCountCriticas() / 2) > procesadores.size());
    }

    public ArrayList<Procesador> getSolucion() {
        return new ArrayList<>(procesadores);
    }

    public boolean verificarSolucion(int countTareas){
        //si se llego a una solucion valida,
        //la cantidad de tareas en solucion final
        //deberia ser igual a la cantidad de tareas con las que se empezo
        int countTareasAsignadas = 0;

        for (Procesador p : procesadores)
            countTareasAsignadas += p.getCountTareasAsignadas();

        return countTareasAsignadas == countTareas;
    }

    @Override
    public String toString() {
        if (!esPosibleSolucionar)
            return "No hay solucion";

        return "Greedy" +
                procesadores +
                "\nTiempo Final = " + getTiempoFinal();
    }
}