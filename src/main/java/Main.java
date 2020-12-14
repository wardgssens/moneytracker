import controller.Controller;
import database.PersonDatabase;
import database.TicketDatabase;
import view.ViewFrame;

public class Main {
    public static void main(String[] args) {
        // Model
        TicketDatabase ticketdb = TicketDatabase.getInstance();
        PersonDatabase persondb = PersonDatabase.getInstance();

        // View
        ViewFrame frame = new ViewFrame();
        persondb.addObserver(frame);
        ticketdb.addObserver(frame);

        // Controller
        new Controller(persondb, ticketdb, frame);
    }
}
