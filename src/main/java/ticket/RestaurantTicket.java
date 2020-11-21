package ticket;

import java.time.LocalDateTime;

public class RestaurantTicket extends Ticket {

    public RestaurantTicket(String description) {
        super("Restaurant: " + description);
    }

    public RestaurantTicket(LocalDateTime timestamp, String description) {
        super(timestamp, "Restaurant: " + description);
    }
}
