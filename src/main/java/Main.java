import controller.Controller;
import database.PersonDatabase;
import database.TicketDatabase;
import factory.AbstractFactory;
import factory.FactoryProvider;
import person.Person;
import ticket.Ticket;
import view.ViewFrame;

public class Main {
    public static void main(String[] args) {
        AbstractFactory<Person> personFactory = FactoryProvider.getFactory("person");
        AbstractFactory<Ticket> ticketFactory = FactoryProvider.getFactory("ticket");

        // Create ticketdatabase.
        TicketDatabase ticketdb = TicketDatabase.getInstance();

        // Create persondatabase.
        PersonDatabase persondb = PersonDatabase.getInstance();

        // Create person and put in db.
        Person a = personFactory.create("individual", "Orval");
        persondb.addPerson(a);
        Person b = personFactory.create("individual", "Alfredo");
        persondb.addPerson(b);
        Person c = personFactory.create("individual", "Andreas");
        persondb.addPerson(c);
        Person d = personFactory.create("individual", "Duane");
        persondb.addPerson(d);
        Person everyone = personFactory.create("everyone", "");

        // Create tickets and put in db.
        Ticket temp = ticketFactory.create("airplane", "tickets to Ibiza");
        temp.addEntry(500, a, b);
        ticketdb.addTicket(temp);

        temp = ticketFactory.create("movie", "baywatch with the boys");
        temp.addEntry(20, a, b);
        ticketdb.addTicket(temp);

        temp = ticketFactory.create("restaurant", "lunch");
        temp.addEntry(100, c, everyone);
        ticketdb.addTicket(temp);

        temp = ticketFactory.create("restaurant", "dinner");
        temp.addEntry(20, d, c);
        ticketdb.addTicket(temp);

        System.out.println(ticketdb.createGlobalTicket());

        ViewFrame frame = new ViewFrame();

        persondb.addObserver(frame);
        ticketdb.addObserver(frame);
        Controller controller = new Controller(persondb, ticketdb, frame);
    }
}
