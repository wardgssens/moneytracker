package person;

public class Individual extends Person {
    public Individual(String name) {
        super.name = name;
    }

    public boolean isEveryone() {
        return false;
    }
}
