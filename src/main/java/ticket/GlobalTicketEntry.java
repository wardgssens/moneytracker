package ticket;

import person.Person;

public class GlobalTicketEntry extends TicketEntry {

    public GlobalTicketEntry(double amount, Person paidBy, Person paidFor) {
        super(amount, paidBy, paidFor);
    }

    @Override
    public String toString() {
        return super.paidFor.getName() + " pays €" + super.amount + " to " + super.paidBy.getName();
    }
}
