package database;

import factory.FactoryProvider;
import person.Person;

import java.util.ArrayList;
import java.util.Observable;

public class PersonDatabase extends Observable {
    private static PersonDatabase uniqueInstance;

    private ArrayList<Person> persons;
    private Person everyone;

    public static PersonDatabase getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new PersonDatabase();

        return uniqueInstance;
    }

    private PersonDatabase() {
        persons = new ArrayList<>();
        everyone = (Person) FactoryProvider.getFactory("person").create("everyone", "");
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
    public void setPersons(ArrayList<Person> persons) { this.persons = persons; }

    public Person getEveryone() {
        return everyone;
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
