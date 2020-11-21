package ticket;

import java.time.LocalDateTime;

public class MovieTicket extends Ticket {

    public MovieTicket(String description) {
        super("Movie: " + description);
    }

    public MovieTicket(LocalDateTime timestamp, String description) {
        super(timestamp, "Movie: " + description);
    }
}
