package ticket;

import person.Person;

public class TicketEntry {
    private double amount;
    private Person paidBy;
    private Person paidFor;

    public TicketEntry(double amount, Person paidBy, Person paidFor) {
        this.amount = amount;
        this.paidBy = paidBy;
        this.paidFor = paidFor;
    }

    public double getAmount() {
        return amount;
    }

    public Person getPaidBy() {
        return paidBy;
    }

    public Person getPaidFor() {
        return paidFor;
    }

    @Override
    public String toString() {
        return paidBy.getName() + " paid " + amount + " for " + paidFor.getName();
    }
}
