package Helper;

import Entidad.Procesador;
import Entidad.Tarea;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVHelper {
    private final String COMMA_DELIMITER = ";";

    //indice de tareas por ID para optimizar servicio 1
    private Map<String, Tarea> indexTareas;

    //listas de tareas criticas y no criticas para optimizar servicio 2
    private List<Tarea> tareasCriticas;
    private List<Tarea> tareasNoCriticas;

    //arbol rojo-negro para optimizar servicio 3
    private NavigableMap<Integer, List<Tarea>> tareasPorPrioridad;

    private List<Procesador> procesadores;

    public CSVHelper(String pathProcesadores, String pathTareas) {
        this.tareasNoCriticas = new LinkedList<>();
        this.tareasCriticas = new LinkedList<>();
        this.indexTareas = new HashMap<>();
        this.tareasPorPrioridad = new TreeMap<>();
        this.procesadores = new ArrayList<>();
        init(pathProcesadores, pathTareas);
    }

    public void init(String pathProcesadores, String pathTareas) {
        this.crearListaProcesadores(pathProcesadores);
        this.crearListaTareas(pathTareas);
    }

    private void crearListaProcesadores(String csvFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                this.procesadores.add(this.crearProcesador(values));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void crearListaTareas(String csvFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                asignarTareas(this.crearTarea(values));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void asignarTareas(Tarea tarea) {
        asignarASublistaCriticas(tarea);
        asignarATareasPorPrioridad(tarea);
        indexTareas.put(tarea.getIdTarea(), tarea);
    }

    private Procesador crearProcesador(String[] values) {
        String idProcesador = values[0];
        String codigoProcesador = values[1];
        boolean estaRefrigerado = Boolean.parseBoolean(values[2]);
        int anioFuncionamiento = Integer.parseInt(values[3]);

        return new Procesador(idProcesador, codigoProcesador, estaRefrigerado, anioFuncionamiento);
    }

    private void asignarATareasPorPrioridad(Tarea tarea) {
        int nivelPrioridad = tarea.getNivelPrioridad();

        if (tareasPorPrioridad.get(nivelPrioridad) == null) {
            tareasPorPrioridad.put(nivelPrioridad, new LinkedList<>());
            tareasPorPrioridad.get(nivelPrioridad).add(tarea);
        } else {
            tareasPorPrioridad.get(tarea.getNivelPrioridad()).add(tarea);
        }
    }

    private void asignarASublistaCriticas(Tarea tarea) {
        if (tarea.esCritica())
            tareasCriticas.add(tarea);
        else
            tareasNoCriticas.add(tarea);
    }

    private Tarea crearTarea(String[] values) {
        String idTarea = values[0];
        String nombreTarea = values[1];
        int tiempoEjecucion = Integer.parseInt(values[2]);
        boolean esCritica = Boolean.parseBoolean(values[3]);
        int nivelPrioridad = Integer.parseInt(values[4]);

        return new Tarea(idTarea, nombreTarea, tiempoEjecucion, esCritica, nivelPrioridad);
    }

    public List<Procesador> getProcesadores() {
        return procesadores;
    }

    public NavigableMap<Integer, List<Tarea>> getTareasPorPrioridad() {
        return tareasPorPrioridad;
    }

    public List<Tarea> getTareasCriticas() {
        return tareasCriticas;
    }

    public List<Tarea> getTareasNoCriticas() {
        return tareasNoCriticas;
    }

    public Map<String, Tarea> getIndexTareas() {
        return indexTareas;
    }
}