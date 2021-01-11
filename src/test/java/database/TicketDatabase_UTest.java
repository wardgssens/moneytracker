package database;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.*;

import person.Everyone;
import person.Individual;
import person.Person;
import ticket.*;

public class TicketDatabase_UTest {
    public TicketDatabase_UTest() {
        // Left blank.
    }

    @Before
    public void initialize() {

    }

    // I don't test get and set functions in this unit test.

    @Test
    public void t_addTicket() throws Exception {
        // Access private field 'tickets'
        Field f_tickets = TicketDatabase.class.getDeclaredField("tickets");
        f_tickets.setAccessible(true);

        // Create a test unit of TicketDatabase & populate it with a mock ArrayList.
        TicketDatabase ut_tdb = TicketDatabase.getInstance();
        ArrayList<Ticket> m_tickets = new ArrayList<>();
        f_tickets.set(ut_tdb, m_tickets);

        // Create mock Ticket.
        Ticket m_ticket = new MovieTicket("Mock ticket");

        // Execute function under test.
        ut_tdb.addTicket(m_ticket);

        // Verify if m_person was added to db.
        assertTrue("Test if ticket was added to database.", ((ArrayList<Ticket>) f_tickets.get(ut_tdb)).contains(m_ticket));
        // The update to Observers is tested separately in this unit test.
    }

    @Test
    public void t_remTicket() throws Exception {
        // Access private field 'tickets'
        Field f_tickets = TicketDatabase.class.getDeclaredField("tickets");
        f_tickets.setAccessible(true);

        // Create a test unit of TicketDatabase & populate it with a mock ArrayList.
        TicketDatabase ut_tdb = TicketDatabase.getInstance();
        ArrayList<Ticket> m_tickets = new ArrayList<>();
        f_tickets.set(ut_tdb, m_tickets);

        // Create mock Ticket and add it to mock ArrayList
        Ticket m_ticket = new MovieTicket("Mock ticket");
        m_tickets.add(m_ticket);

        // Execute function under test.
        ut_tdb.remTicket(m_ticket);

        // Verify if m_person was added to db.
        assertFalse("Test if ticket was added to database.", ((ArrayList<Ticket>) f_tickets.get(ut_tdb)).contains(m_ticket));
        // The update to Observers is tested separately in this unit test.
    }

    @Test
    public void t_clear() throws Exception {
        // Access private field 'tickets'
        Field f_tickets = TicketDatabase.class.getDeclaredField("tickets");
        f_tickets.setAccessible(true);

        // Create a test unit of TicketDatabase & populate it with a mock ArrayList.
        TicketDatabase ut_tdb = TicketDatabase.getInstance();
        ArrayList<Ticket> m_tickets = new ArrayList<>();
        f_tickets.set(ut_tdb, m_tickets);

        // Create mock Ticket and add it to mock ArrayList
        Ticket m_ticket = new MovieTicket("Mock ticket");
        m_tickets.add(m_ticket);

        // Execute function under test.
        ut_tdb.clear();

        // Verify if the db if empty.
        assertTrue("Test if database is empty.", ((ArrayList<Ticket>) f_tickets.get(ut_tdb)).isEmpty());
    }

    @Test
    public void t_updateTickets() {
        // Create a test unit of PersonDatabase & populate it with a mock ArrayList.
        TicketDatabase ut_tdb = TicketDatabase.getInstance();

        // Create a mock observer and add it to the test unit.
        class MockObserver implements Observer {
            public String msg = "";
            @Override
            public void update(Observable o, Object arg) {
                msg = (String) arg;
            }
        }
        MockObserver m_observer = new MockObserver();
        ut_tdb.addObserver(m_observer);

        // Execute function under test without force.
        ut_tdb.updateTickets(false);

        // Verify the mock observer got no update.
        assertEquals("Test if observer got no update", m_observer.msg, "");

        // Execute function under test with force.
        ut_tdb.updateTickets(true);

        // Verify the mock observer got ticket update.
        assertEquals("Test if observer got tickets update", m_observer.msg, "ticket-list");
    }

    @Test
    public void t_updateGlobalTicket() {
        // Create a test unit of PersonDatabase & populate it with a mock ArrayList.
        TicketDatabase ut_tdb = TicketDatabase.getInstance();

        // Create a mock observer and add it to the test unit.
        class MockObserver implements Observer {
            public String msg = "";
            @Override
            public void update(Observable o, Object arg) {
                msg = (String) arg;
            }
        }
        MockObserver m_observer = new MockObserver();
        ut_tdb.addObserver(m_observer);

        // Execute function under test without force.
        ut_tdb.updateGlobalTicket(false);

        // Verify the mock observer got no update.
        assertEquals("Test if observer got no update.", m_observer.msg, "");

        // Execute function under test with force.
        ut_tdb.updateGlobalTicket(true);

        // Verify the mock observer got ticket update.
        assertEquals("Test if observer got global ticket update.", m_observer.msg, "global-ticket");
    }

    @Test
    public void t_calculateGlobalTicket() throws Exception {
        // Access private field
        Field f_tickets = TicketDatabase.class.getDeclaredField("tickets");
        f_tickets.setAccessible(true);
        Field f_globalTicket = TicketDatabase.class.getDeclaredField("globalTicket");
        f_globalTicket.setAccessible(true);

        // Create a test unit of TicketDatabase & populate it with a mock ArrayList and mock GlobalTicket.
        TicketDatabase ut_tdb = TicketDatabase.getInstance();
        ArrayList<Ticket> m_tickets = new ArrayList<>();
        f_tickets.set(ut_tdb, m_tickets);
        Ticket m_globalTicket = new GlobalTicket("mock");
        f_globalTicket.set(ut_tdb, m_globalTicket);

        // Create mock persons
        Person m_personA = new Individual("A");
        Person m_personB = new Individual("B");
        Person m_personC = new Individual("C");
        Person m_everyone = new Everyone();

        // Create mock tickets and add it to mock ArrayList
        Ticket m_movieTicket = new MovieTicket("Mock ticket");
        m_movieTicket.addEntry(90, m_personA, m_everyone);
        m_tickets.add(m_movieTicket);

        Ticket m_restTicket = new RestaurantTicket("Mock ticket");
        m_restTicket.addEntry(20, m_personA, m_personB);
        m_restTicket.addEntry(25, m_personA, m_personC);
        m_tickets.add(m_restTicket);

        Ticket m_planeTicket = new AirplaneTicket("Mock ticket");
        m_planeTicket.addEntry(300, m_personC, m_everyone);
        m_tickets.add(m_planeTicket);

        // Execute function under test.
        ut_tdb.calculateGlobalTicket();

        // Verify the correctness of the global ticket.
        Ticket res_globalTicket = (Ticket) f_globalTicket.get(ut_tdb);

        Map<Person, Double> totals = new HashMap<>();
        for (TicketEntry entry : res_globalTicket.getEntries()) {
            totals.compute(entry.getPaidFor(), (k, v) -> (v != null) ? v - entry.getAmount() : 0 - entry.getAmount());
            totals.compute(entry.getPaidBy(), (k, v) -> (v != null) ? v - entry.getAmount() : 0 + entry.getAmount());
        }

        assertEquals("Check total of person A.", totals.get(m_personA), 5, 0.001);
        assertEquals("Check total of person B.", totals.get(m_personB), -150, 0.001);
        assertEquals("Check total of person C.", totals.get(m_personC), 145, 0.001);
    }
}
