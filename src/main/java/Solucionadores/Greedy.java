package Solucionadores;

import Entidad.Procesador;
import Entidad.Tarea;
import org.example.Servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Greedy implements SolucionadorAbstracto {
    List<Procesador> procesadores;
    List<Tarea> tareas;
    private boolean esPosibleSolucionar;
    private int countCandidatos;
    private int countCriticas;
    private int tiempoFinal;

    public Greedy(Servicios servicios) {
        this.procesadores = servicios.getProcesadores();
        this.tareas = servicios.getTareas();
        this.countCriticas = servicios.countCriticas();
        this.tiempoFinal = -1;
    }

    //complejidad O(n*(m log m)) siendo n la cantidad de tareas y m la cantidad de procesadores.
    //Recorro todas las tareas una vez, y tengo que ordenar los procesadores una vez por cada tarea.
    //El ordenamiento internamente usa una variacion timsort, cuya complejidad es O(n log n).

    //La idea es:
    //Paso 1: ordenar las tareas en base a su tiempo de ejecucion (de mayor a menor).
    //Paso 2: por cada una de las tareas, ordeno los procesadores en base a la suma del tiempo de ejecucion de sus tareas asignadas (de menor a mayor)
    //Paso 3: por cada procesador, intento agregar la tarea. si puedo agregarla tarea, la agrego y repito desde el paso 2 con la siguiente tarea en la lista
    //Paso 4: si no pude agregar mi tarea a ningun procesador, es que no hay solucion posible.
    //si pude agregar todas las tareas, la solucion va a estar en la lista de procesadores

    //En varias situaciones alcanza soluciones optimas, pero por ejemplo
    public void greedy(int tiempo) {
        if (!haySolucion())
            return;

        //Paso 1: ordenar tareas
        int countTareas = refrezcar(tiempo);

        while (!tareas.isEmpty()) {
            Tarea t = tareas.remove(0);
            //Pasos 2, 3 y 4
            esPosibleSolucionar = seleccionar(t);

            //si no hay solucion posible, corto la ejecucion
            if (!esPosibleSolucionar) {
                setTiempoFinal();
                return;
            }
        }

        esPosibleSolucionar = verificarSolucion(countTareas);
        setTiempoFinal();
    }

    private int refrezcar(int tiempo) {
        int countTareas = tareas.size();
        this.countCandidatos = 0;
        Procesador.setTiempo(tiempo);
        tareas.sort(Collections.reverseOrder());
        return countTareas;
    }

    public int getTiempoFinal() {
        return tiempoFinal;
    }

    private void setTiempoFinal() {
        if (!esPosibleSolucionar) {
            tiempoFinal = -1;
        } else {
            int tiempoMayor = 0;

            for (Procesador p : procesadores) {
                int tiempoProcesador = p.getTiempoEjecucionProcesador();

                if (tiempoProcesador > tiempoMayor)
                    tiempoMayor = tiempoProcesador;
            }

            tiempoFinal = tiempoMayor;
        }
    }

    //Ordeno procesadores
    //Pruebo de agregar tarea a procesador
    //Retorna verdadero si efectivamente se pudo agregar la tarea a un procesador
    //Retorna falso si no se pudo: Eso significa que no hay solucion posible.
    private boolean seleccionar(Tarea t) {
        countCandidatos++;

        //Paso 2: ordenar procesadores
        Collections.sort(procesadores);
        //Paso 3: intentar agregar
        for (Procesador p : procesadores)
            if (p.agregarTarea(t))
                return true;

        //Paso 4: si no puedo agregar, retornar falso.
        return false;
    }

    @Override
    public int getMetrica() {
        return this.countCandidatos;
    }

    @Override
    public String printMetrica() {
        return "Cantidad candidatos: " + getMetrica();
    }

    @Override
    public boolean haySolucion() {
        return !((this.countCriticas / 2) > procesadores.size());
    }

    public ArrayList<Procesador> getSolucion() {
        return new ArrayList<>(procesadores);
    }

    public boolean verificarSolucion(int countTareas) {
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