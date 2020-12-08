package ticket;

import java.time.LocalDateTime;

public class RestaurantTicket extends Ticket {

    public RestaurantTicket(String description) {
        super("Restaurant: " + description);
    }

}
