package Factory;

import Entidad.Procesador;

public class ProcesadorFactory implements Factory<Procesador>{
    private static ProcesadorFactory instance;

    private ProcesadorFactory(){}

    public static ProcesadorFactory getInstance(){
        if (instance==null)
            instance = new ProcesadorFactory();

        return instance;
    }

    @Override
    public Procesador create(String[] values) {
        return createProccesador(values);
    }

    private Procesador createProccesador(String[] values) {
        String idProcesador = values[0];
        String codigoProcesador = values[1];
        boolean estaRefrigerado = Boolean.parseBoolean(values[2]);
        int anioFuncionamiento = Integer.parseInt(values[3]);

        return new Procesador(idProcesador, codigoProcesador , estaRefrigerado, anioFuncionamiento);
    }

}
