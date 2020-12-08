package factory;

import person.Everyone;
import person.Individual;
import person.Person;

public class PersonFactory implements AbstractFactory<Person> {
    @Override
    public Person create(String type, String name) {
        Person person = null;

        if (type != null) {
            type = type.toLowerCase();

            if (type.equals("individual")) {
                person = new Individual(name);
            } else if (type.equals("everyone")) {
                person = new Everyone();
            }
        }

        return person;
    }
}
