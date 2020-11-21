package database;

public class TicketDatabase {
    private static TicketDatabase uniqueInstance;

    private final ArrayList<Ticket> =

    private TicketDatabase() {
    }

    public static TicketDatabase getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new TicketDatabase();

        return uniqueInstance;
    }

    public void addTicket()
}
