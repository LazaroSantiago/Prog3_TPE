package org.example;

import Entidad.Procesador;
import Entidad.Tarea;
import Solucionadores.Backtracking;
import Solucionadores.Greedy;
import Solucionadores.SolucionadorAbstracto;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        testearBacktracking(0);
        testearGreedy(0);

        testearBacktracking(100);
        testearGreedy(100);

    }

    public static void printSolucion(SolucionadorAbstracto s){
        System.out.println(s);
        System.out.println(s.printMetrica() + "\n");
    }

    public static void testearGreedy(int tiempo){
        Servicios servicios = new Servicios("./datasets/Procesadores.csv", "./datasets/Tareas.csv");
        List<Tarea> tareas = servicios.getTareas();
        List<Procesador> procesadores = servicios.getProcesadores();

        Greedy g = new Greedy(procesadores, tareas);
        g.greedy(tiempo);
        printSolucion(g);
    }

    public static void testearBacktracking(int tiempo){
        Servicios servicios = new Servicios("./datasets/Procesadores.csv", "./datasets/Tareas.csv");
        List<Tarea> tareas = servicios.getTareas();
        List<Procesador> procesadores = servicios.getProcesadores();

        Backtracking b = new Backtracking(procesadores, tareas);
        b.backtracking(tiempo);
        printSolucion(b);
    }

}
