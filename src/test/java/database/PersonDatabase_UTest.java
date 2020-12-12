package database;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import person.Individual;
import person.Person;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class PersonDatabase_UTest {
    public PersonDatabase_UTest() {
        // Left blank.
    }

    @Before
    public void initialize() {

    }

    // I don't test get and set functions in this unit test.

    @Test
    public void t_addPerson() throws Exception {
        // Access private field 'persons'
        Field f_persons = PersonDatabase.class.getDeclaredField("persons");
        f_persons.setAccessible(true);

        // Create a test unit of PersonDatabase & populate it with a mock ArrayList.
        PersonDatabase ut_pdb = PersonDatabase.getInstance();
        ArrayList<Person> m_persons = new ArrayList<>();
        f_persons.set(ut_pdb, m_persons);

        // Create mock Person.
        Person m_person = new Individual("Mock person");

        // Execute function under test.
        ut_pdb.addPerson(m_person);

        // Verify if m_person was added to db.
        assertTrue("Test if person was added to database.", ((ArrayList<Person>) f_persons.get(ut_pdb)).contains(m_person));
        // The update to Observers is tested separately in this unit test.
    }


    @Test
    public void t_remPerson() throws Exception {
        // Access private field 'persons'
        Field f_persons = PersonDatabase.class.getDeclaredField("persons");
        f_persons.setAccessible(true);

        // Create a test unit of PersonDatabase & populate it with a mock ArrayList.
        PersonDatabase ut_pdb = PersonDatabase.getInstance();
        ArrayList<Person> m_persons = new ArrayList<>();
        f_persons.set(ut_pdb, m_persons);

        // Create a mock Person and add to the mock ArrayList.
        Person m_person = new Individual("Mock person");
        m_persons.add(m_person);

        // Execute function under test.
        ut_pdb.remPerson(m_person);

        // Verify if the mock Person was removed from db.
        assertFalse("Test if person was removed from database.", ((ArrayList<Person>) f_persons.get(ut_pdb)).contains(m_person));
        // The update to Observers is tested separately in this unit test.
    }

    @Test
    public void t_clear() throws Exception {
        // Access private field 'persons'
        Field f_persons = PersonDatabase.class.getDeclaredField("persons");
        f_persons.setAccessible(true);

        // Create a test unit of PersonDatabase & populate it with a mock ArrayList.
        PersonDatabase ut_pdb = PersonDatabase.getInstance();
        ArrayList<Person> m_persons = new ArrayList<>();
        f_persons.set(ut_pdb, m_persons);

        // Create a mock Person and add to the mock ArrayList.
        Person m_person = new Individual("Mock person");
        m_persons.add(m_person);

        // Execute function under test.
        ut_pdb.clear();

        // Verify if the db if empty.
        assertTrue("Test if person database is empty.", ((ArrayList<Person>) f_persons.get(ut_pdb)).isEmpty());
    }

    @Test
    public void t_updatePersons() {
        // Create a test unit of PersonDatabase & populate it with a mock ArrayList.
        PersonDatabase ut_pdb = PersonDatabase.getInstance();

        // Create a mock observer and add it to the test unit.
        class MockObserver implements Observer {
            public boolean toggled = false;
            @Override
            public void update(Observable o, Object arg) {
                toggled = !toggled;
            }
        }
        MockObserver m_observer = new MockObserver();
        ut_pdb.addObserver(m_observer);

        // Execute function under test without force.
        ut_pdb.updatePersons(false);

        // Verify the mock observer got no update.
        assertFalse("Test if observer got no update", m_observer.toggled);

        // Execute function under test with force.
        ut_pdb.updatePersons(true);

        // Verify the mock observer got update.
        assertTrue("Test if observer got update", m_observer.toggled);
    }
}
