package Solucionadores;

public interface SolucionadorAbstracto {
    boolean haySolucion();
    int getMetrica();
    boolean verificarSolucion(int countTareas);
    String printMetrica();
}
