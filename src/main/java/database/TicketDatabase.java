package database;

import factory.AbstractFactory;
import factory.FactoryProvider;
import person.Person;
import ticket.Ticket;
import ticket.TicketEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class TicketDatabase extends Observable {
    private static TicketDatabase uniqueInstance;

    private AbstractFactory<Ticket> ticketFactory;
    private AbstractFactory<Person> personFactory;

    private ArrayList<Ticket> tickets;
    private Ticket globalTicket;

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
        updateTickets(true);
    }

    public void remTicket(Ticket t) {
        tickets.remove(t);
        updateTickets(true);
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void updateTickets(boolean force) {
        if (force)
            setChanged();
        notifyObservers("ticket-list");
    }

    public void updateGlobalTicket(boolean force) {
        if (force)
            setChanged();
        notifyObservers("global-ticket");
    }

    public void clear() {
        tickets.clear();
    }

    public void calculateGlobalTicket() {
        // Total all TicketEntries in a ConcurrentHashMap. It needs to be concurrent to iterate over it using
        // two iterators at the same time.
        Map<Person, Double> totals = new ConcurrentHashMap<>();
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

                totals.put(paidBy, totals.getOrDefault(paidBy, 0.0) - amount);
                if (paidFor.isEveryone()) {
                    commonAmount += amount;
                } else {
                    totals.put(paidFor, totals.getOrDefault(paidFor, 0.0) + amount);
                }
            }
        }

        // Split costs.
        double splitCost = commonAmount / totals.size();
        BiFunction<Person, Double, Double> splitFunction = (person, amount) -> amount + splitCost;
        totals.replaceAll(splitFunction);

        // Create Global Ticket.
        globalTicket = ticketFactory.create("global", "");

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

        updateGlobalTicket(true);
    }

    public Ticket getGlobalTicket() {
        return globalTicket;
    }
}
