package database;

import factory.AbstractFactory;
import factory.FactoryProvider;
import person.Person;
import ticket.Ticket;
import ticket.TicketEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;

public class TicketDatabase {
    private static TicketDatabase uniqueInstance;

    private AbstractFactory<Ticket> ticketFactory;
    private AbstractFactory<Person> personFactory;

    private ArrayList<Ticket> tickets;

    private TicketDatabase() {
        ticketFactory = FactoryProvider.getFactory("ticket");
        personFactory = FactoryProvider.getFactory("person");

        tickets = new ArrayList<>();
    }

    public static TicketDatabase getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new TicketDatabase();

        return uniqueInstance;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void clearDatabase() {
        tickets = new ArrayList<>();
    }

    public Ticket createGlobalTicket() {
        // Total all TicketEntries.
        Map<Person, Double> totals = new HashMap<>();
        double commonAmount = 0;

        Iterator<Ticket> ticketIterator = tickets.iterator();
        Iterator<TicketEntry> entryIterator;


        while (ticketIterator.hasNext()) {
            entryIterator = ticketIterator.next().getEntries().iterator();

            while (entryIterator.hasNext()) {
                TicketEntry entry = entryIterator.next();

                Person paidBy = entry.getPaidBy();
                Person paidFor = entry.getPaidFor();
                Double amount = entry.getAmount();

                totals.put(paidBy, totals.get(paidBy) - amount);
                if (paidFor.isEveryone()) {
                    commonAmount += amount;
                } else {
                    totals.put(paidFor, totals.get(paidFor) + amount);
                }
            }
        }

        // Split costs.
        double splitCost = commonAmount / totals.size();
        BiFunction<Person, Double, Double> splitFunction = (person, amount) -> amount + splitCost;
        totals.replaceAll(splitFunction);

        // Create Global Ticket.
        Ticket globalTicket = ticketFactory.create("global", "");

        for (Map.Entry<Person, Double> entry : totals.entrySet()) {
            Person person = entry.getKey();
            double total = entry.getValue();

            // Algorithm to creat entries.
        }

        return globalTicket;
    }
}
