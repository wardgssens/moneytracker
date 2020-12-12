package person;

import java.io.Serializable;

public abstract class Person implements Serializable {
    String name;

    public String getName() {
        return name;
    }

    public abstract boolean isEveryone();

    @Override
    public String toString() {
        return name;
    }
}
