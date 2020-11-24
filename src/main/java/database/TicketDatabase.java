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

                totals.put(paidBy, (totals.get(paidBy) != null ? totals.get(paidBy) : 0)  - amount);
                if (paidFor.isEveryone()) {
                    commonAmount += amount;
                } else {
                    totals.put(paidFor, (totals.get(paidFor) != null ? totals.get(paidFor) : 0) + amount);
                }
            }
        }

        // Split costs.
        double splitCost = commonAmount / totals.size();
        BiFunction<Person, Double, Double> splitFunction = (person, amount) -> amount + splitCost;
        totals.replaceAll(splitFunction);

        // Create Global Ticket.
        Ticket globalTicket = ticketFactory.create("global", "");

        Iterator<Map.Entry<Person, Double>> outerTotalIterator = totals.entrySet().iterator();
        while (outerTotalIterator.hasNext()) {
            Map.Entry<Person, Double> outerEntry = outerTotalIterator.next();
            Person outerPerson = outerEntry.getKey();
            double outerTotal = outerEntry.getValue();

            Iterator<Map.Entry<Person, Double>> innerTotalsIterator = totals.entrySet().iterator();
            while (innerTotalsIterator.hasNext() && outerTotal < 0) {
                Map.Entry<Person, Double> innerEntry = innerTotalsIterator.next();
                Person innerPerson = innerEntry.getKey();
                double innerTotal = innerEntry.getValue();

                if (innerTotal > 0) {
                    double amount = Math.min(Math.abs(outerTotal), innerTotal);
                    outerTotal += amount;
                    totals.put(innerPerson, innerTotal - amount);
                    globalTicket.addEntry(amount, outerPerson, innerPerson);
                }

                if (innerTotal == 0) {
                    innerTotalsIterator.remove();
                }
            }

            if (outerTotal == 0) {
                outerTotalIterator.remove();
            }
        }


        return globalTicket;
    }
}
