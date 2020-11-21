package factory;

/**
 * Abstract factory code used from: https://www.baeldung.com/java-abstract-factory-pattern
 */
public interface AbstractFactory<T> {
    T create(String type, String name);
}
