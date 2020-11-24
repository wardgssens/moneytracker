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

    public void addPerson(Person person) {
        persons.add(person);
        setChanged();
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void updatePersons(boolean force) {
        if (force)
            setChanged();
        notifyObservers(persons);
    }

    public void clear() {
        persons.clear();
    }
}
