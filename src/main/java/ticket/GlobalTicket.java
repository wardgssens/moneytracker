package ticket;

import java.time.LocalDateTime;

public class GlobalTicket extends Ticket {

    public GlobalTicket(String description) {
        super("Global: " + description);
    }

    public GlobalTicket(LocalDateTime timestamp, String description) {
        super(timestamp, "Global: " + description);
    }
}
