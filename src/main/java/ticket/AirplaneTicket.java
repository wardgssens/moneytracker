package ticket;

import java.time.LocalDateTime;

public class AirplaneTicket extends Ticket {

    public AirplaneTicket(String description) {
        super("Airplane: " + description);
    }
}
