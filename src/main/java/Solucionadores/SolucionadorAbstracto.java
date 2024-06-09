package Solucionadores;

public interface SolucionadorAbstracto {
    //interfaz para forzar a que las clases Backtracking y Greedy compartan ciertos criterios
    //como el tener su manera de verificar si la solucion es valida
    boolean haySolucion();
    int getMetrica();
    boolean verificarSolucion(int countTareas);
    String printMetrica();
}
