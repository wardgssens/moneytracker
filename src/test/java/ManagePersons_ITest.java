import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import controller.Controller;
import database.PersonDatabase;
import database.TicketDatabase;
import person.Individual;
import person.Person;
import view.ViewFrame;
import view.panels.PersonsPanel;

import javax.swing.*;
import java.lang.reflect.Field;

public class ManagePersons_ITest {

    private TicketDatabase ticketdb;
    private PersonDatabase persondb;
    private ViewFrame frame;
    private Controller controller;

    public ManagePersons_ITest() {
        // Left blank.
    }

    @Before
    public void initialize() {
        ticketdb = TicketDatabase.getInstance();
        ticketdb.clear();
        persondb = PersonDatabase.getInstance();
        persondb.clear();
        frame = new ViewFrame();
        persondb.addObserver(frame);
        ticketdb.addObserver(frame);
        controller = new Controller(persondb, ticketdb, frame);
    }

    @Test
    public void t_addPerson() throws Exception {
        // Make private fields accessible
        Field f_tfName = PersonsPanel.class.getDeclaredField("tfAddPerson");
        f_tfName.setAccessible(true);
        Field f_btAddPerson = PersonsPanel.class.getDeclaredField("btAddPerson");
        f_btAddPerson.setAccessible(true);
        Field f_lstPersons = PersonsPanel.class.getDeclaredField("personJList");
        f_lstPersons.setAccessible(true);

        // Enter text in name field.
        ((JTextField) f_tfName.get(frame.getPersonsPanel())).setText("John Doe");

        // Press button to add person.
        ((JButton) f_btAddPerson.get(frame.getPersonsPanel())).doClick();

        // Verify if new person is visible in the view.
        ListModel<Person> listModel = (ListModel<Person>) ((JList) f_lstPersons.get(frame.getPersonsPanel())).getModel();
        assertEquals("Check if the list contains one item.", listModel.getSize(), 1);
        assertEquals("Check if the person was added correctly.", listModel.getElementAt(0).getName(), "John Doe");
    }

    @Test
    public void t_remPerson() throws Exception {
        // Make private fields accessible
        Field f_btRemPerson = PersonsPanel.class.getDeclaredField("btRemPerson");
        f_btRemPerson.setAccessible(true);
        Field f_lstPersons = PersonsPanel.class.getDeclaredField("personJList");
        f_lstPersons.setAccessible(true);

        // Add a user to the database and update the view.
        Person m_person = new Individual("John Doe");
        persondb.addPerson(m_person);

        // Verify if new person is in list.
        ListModel<Person> listModel = (ListModel<Person>) ((JList) f_lstPersons.get(frame.getPersonsPanel())).getModel();
        assertEquals("Check if the list contains one item.", listModel.getSize(), 1);
        assertEquals("Check if the person was added correctly.", listModel.getElementAt(0).getName(), "John Doe");

        // Select person to remove.
        ((JList) f_lstPersons.get(frame.getPersonsPanel())).setSelectedIndex(0);

        // Press button to remove person.
        ((JButton) f_btRemPerson.get(frame.getPersonsPanel())).doClick();

        // Verify if the list is empty.
        listModel = (ListModel<Person>) ((JList) f_lstPersons.get(frame.getPersonsPanel())).getModel();
        assertEquals("Check if the list is empty", listModel.getSize(), 0);
    }
}
