package Helper;

import Factory.Factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CSVHelper<T> {
    private final String COMMA_DELIMITER = ";";
    private final Factory<T> factory;

    public CSVHelper(Factory<T> factory) {
        this.factory = factory;
    }

    public List<T> crearLista(String csvFile) {
        List<T> result = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                result.add(factory.create(values));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

}