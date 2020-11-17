package person;

public abstract class Person {
    String name;
    
    public String getName() {
        return name;
    }

    abstract boolean canPay();
}
