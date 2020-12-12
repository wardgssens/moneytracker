package ticket;

import person.Person;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


public abstract class Ticket implements Serializable {
    Set<TicketEntry> entries;
    private LocalDateTime timestamp;
    private String description;

    public Ticket(String description) {
        this.entries = new HashSet<>();
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void addEntry(TicketEntry entry) {
        entries.add(entry);
    }

    public void addEntry(double amount, Person paidBy, Person paidFor) {
        entries.add(new TicketEntry(amount, paidBy, paidFor));
    }

    public Set<TicketEntry> getEntries() {
        return entries;
    }

    public String detailsToString() {
        StringBuilder details = new StringBuilder();
        for (TicketEntry entry : entries) {
            details.append(entry.toString()).append("\n");
        }

        return details.toString();
    }

    @Override
    public String toString() {
        return description + " @ " + timestamp.format(DateTimeFormatter.ofPattern("dd-MM-yy hh:mm"));
    }
}
