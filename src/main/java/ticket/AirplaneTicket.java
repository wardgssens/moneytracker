package ticket;

import java.time.LocalDateTime;

public class AirplaneTicket extends Ticket {

    public AirplaneTicket(LocalDateTime timestamp, String description) {
        super(timestamp, "Airplane: " + description);
    }

    public AirplaneTicket(String description) {
        super("Airplane: " + description);
    }
}
