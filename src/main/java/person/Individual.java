package person;

public class Individual extends Person {
    public Individual(String name) {
        super.name = name;
    }

    public boolean canPay() {
        return true;
    }
}
