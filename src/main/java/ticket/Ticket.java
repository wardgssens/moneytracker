package ticket;

import java.util.Set;
import java.util.HashSet;
import java.time.LocalDateTime;


public abstract class Ticket {
    private Set<TicketEntry> entries;
    private LocalDateTime timestamp;
    private String description;

    public Ticket(String description) {
        this.entries = new HashSet<>();
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }

    public Ticket(LocalDateTime timestamp, String description) {
        this.entries = new HashSet<>();
        this.timestamp = timestamp;
        this.description = description;
    }

    void addEntry(TicketEntry entry) {
        entries.add(entry);
    }

    Set<TicketEntry> getEntries() {
        return entries;
    }
}
