import database.TicketDatabase;
import factory.AbstractFactory;
import factory.FactoryProvider;
import person.Person;
import ticket.Ticket;

public class Main {
    public static void main(String[] args) {
        AbstractFactory<Person> personFactory = FactoryProvider.getFactory("person");
        AbstractFactory<Ticket> ticketFactory = FactoryProvider.getFactory("ticket");

        // Create persons.
        Person a = personFactory.create("individual", "A");
        Person b = personFactory.create("individual", "B");
        Person c = personFactory.create("individual", "C");
        Person d = personFactory.create("individual", "D");
        Person everyone = personFactory.create("everyone", "");

        // Create ticketdatabase.
        TicketDatabase ticketdb = TicketDatabase.getInstance();

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
    }
}
