package org.example;

import Solucionadores.Backtracking;
import Solucionadores.Greedy;
import Solucionadores.SolucionadorAbstracto;

public class Main {
    private final static String PROCESADORES = "src/main/resources/datasets/Procesadores.csv";

    private final static String TAREAS_1 = "src/main/resources/datasets/Tareas1.csv";
    private final static String TAREAS_2 = "src/main/resources/datasets/Tareas2.csv";

    public static void main(String[] args) {
        //Testea los 5 casos que figuran en la planilla para Backtracking y Greedy:
        testearBacktracking();
        testearGreedy();
        //Nota: Un tiempo de -1 en solucion obtenida representa que no hay solucion posible.
    }

    public static void printSolucion(SolucionadorAbstracto s) {
        System.out.println(s);
        System.out.println(s.printMetrica());
    }

    private static int testearGreedy(int tiempo, String tareas) {
        return testearGreedy(tiempo, PROCESADORES, tareas);
    }

    public static int testearGreedy(int tiempo, String procesadores, String tareas) {
        Servicios servicios = new Servicios(procesadores, tareas);
        Greedy g = new Greedy(servicios);
        g.greedy(tiempo);
        printSolucion(g);
        return g.getTiempoFinal();
    }

    public static int testearBacktracking(int tiempo, String procesadores, String tareas) {
        Servicios servicios = new Servicios(procesadores, tareas);
        Backtracking b = new Backtracking(servicios);
        b.backtracking(tiempo);
        printSolucion(b);
        return b.getTiempoFinal();
    }

    public static int testearBacktracking(int tiempo, String tareas) {
        return testearBacktracking(tiempo, PROCESADORES, tareas);
    }

    public static void testearBacktracking() {
        System.out.println(">>Backtracking");
        System.out.println(">Test set de tareas 1, tiempo = 200 ");
        int tiempoSolucionObtenida = testearBacktracking(200, TAREAS_1);
        System.out.println("Tiempo solucion optima = 90. Solucion obtenida = " + tiempoSolucionObtenida + "\n");

        System.out.println(">Test set de tareas 1, tiempo = 10 ");
        tiempoSolucionObtenida = testearBacktracking(10, TAREAS_1);
        System.out.println("Tiempo solucion optima = 110. Solucion obtenida = " + tiempoSolucionObtenida + "\n");

        System.out.println(">Test set de tareas 2, tiempo = 200 ");
        tiempoSolucionObtenida = testearBacktracking(200, TAREAS_2);
        System.out.println("Tiempo solucion optima = 140. Solucion obtenida = " + tiempoSolucionObtenida + "\n");


        System.out.println(">Test set de tareas 2, tiempo = 100 ");
        tiempoSolucionObtenida = testearBacktracking(100, TAREAS_2);
        System.out.println("Tiempo solucion optima = 200. Solucion obtenida = " + tiempoSolucionObtenida + "\n");


        System.out.println(">Test set de tareas 2, tiempo = 80 ");
        tiempoSolucionObtenida = testearBacktracking(80, TAREAS_2);
        System.out.println("Tiempo solucion optima = No hay solucion. Solucion obtenida = " + tiempoSolucionObtenida + "\n");
    }

    public static void testearGreedy() {
        System.out.println(">>Greedy");

        System.out.println(">Test set de tareas 1, tiempo = 200 ");
        int tiempoSolucionObtenida = testearGreedy(200, TAREAS_1);
        System.out.println("Tiempo solucion optima = 90. Solucion obtenida = " + tiempoSolucionObtenida + "\n");

        System.out.println(">Test set de tareas 1, tiempo = 10 ");
        tiempoSolucionObtenida = testearGreedy(10, TAREAS_1);
        System.out.println("Tiempo solucion optima = 110. Solucion obtenida = " + tiempoSolucionObtenida + "\n");

        System.out.println(">Test set de tareas 2, tiempo = 200 ");
        tiempoSolucionObtenida = testearGreedy(200, TAREAS_2);
        System.out.println("Tiempo solucion optima = 140. Solucion obtenida = " + tiempoSolucionObtenida + "\n");


        System.out.println(">Test set de tareas 2, tiempo = 100 ");
        tiempoSolucionObtenida = testearGreedy(100, TAREAS_2);
        System.out.println("Tiempo solucion optima = 200. Solucion obtenida = " + tiempoSolucionObtenida);
        System.out.println("No es un bug. Por como esta planteado el algoritmo, tiene sentido que no se pueda llegar a una solucion usando greedy. \n");

        System.out.println(">Test set de tareas 2, tiempo = 80 ");
        tiempoSolucionObtenida = testearGreedy(80, TAREAS_2);
        System.out.println("Tiempo solucion optima = No hay solucion. Solucion obtenida = " + tiempoSolucionObtenida);
    }
}
