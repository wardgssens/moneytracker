package ticket;

import person.Person;

public class GlobalTicket extends Ticket {

    public GlobalTicket(String description) {
        super("Global: " + description);
    }

    @Override
    public void addEntry(double amount, Person paidBy, Person paidFor) {
        super.entries.add(new GlobalTicketEntry(amount, paidBy, paidFor));
    }
}
