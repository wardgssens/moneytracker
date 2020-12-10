package factory;

import ticket.*;

public class TicketFactory implements AbstractFactory<Ticket> {
    @Override
    public Ticket create(String type, String name) {
        // Using a String in switch statement can throw NullPointerException when the String is null.
        Ticket ticket = null;

        if (type != null) {
            type = type.toLowerCase();

            switch (type) {
                case "airplane":
                    ticket = new AirplaneTicket(name);
                    break;
                case "movie":
                    ticket = new MovieTicket(name);
                    break;
                case "restaurant":
                    ticket = new RestaurantTicket(name);
                    break;
                case "global":
                    ticket = new GlobalTicket(name);
                    break;
            }
        }

        return ticket;
    }
}
