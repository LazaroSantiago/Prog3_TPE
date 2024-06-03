package Factory;

public interface Factory<T> {
    T create(String[] values);
}
