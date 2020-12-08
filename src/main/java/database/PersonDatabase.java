package database;

import person.Person;

import java.util.ArrayList;
import java.util.Observable;

public class PersonDatabase extends Observable {
    private static PersonDatabase uniqueInstance;

    private ArrayList<Person> persons;

    public static PersonDatabase getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new PersonDatabase();

        return uniqueInstance;
    }

    private PersonDatabase() {
        persons = new ArrayList<>();
    }

    public void addPerson(Person person) {
        persons.add(person);
        updatePersons(true);
    }

    public void remPerson(Person person) {
        persons.remove(person);
        updatePersons(true);
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void updatePersons(boolean force) {
        if (force)
            setChanged();
        notifyObservers("person-list");
    }

    public void clear() {
        persons.clear();
    }
}
