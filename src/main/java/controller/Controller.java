package controller;

import database.PersonDatabase;
import database.TicketDatabase;
import factory.AbstractFactory;
import factory.FactoryProvider;
import person.Person;
import ticket.Ticket;
import view.ViewFrame;

import javax.swing.text.View;

public class Controller {
    private AbstractFactory<Ticket> ticketFactory;
    private AbstractFactory<Person> personFactory;

    // Model
    private PersonDatabase pdb;
    private TicketDatabase tdb;

    // View
    private ViewFrame view;

    public Controller(PersonDatabase pdb, TicketDatabase tdb, ViewFrame view) {
        ticketFactory = FactoryProvider.getFactory("ticket");
        personFactory = FactoryProvider.getFactory("person");

        this.pdb = pdb;
        this.tdb = tdb;

        this.view = view;

        initListeners();

        pdb.updatePersons(true);
        tdb.updateTickets(true);
    }

    private void initListeners() {
        // Add person to database
        view.getPersonsPanel().addListenerAddPerson(e -> {
            String name = view.getPersonsPanel().getTextAddPerson();
            view.getPersonsPanel().setTextAddPerson("");
            Person p = personFactory.create("individual", name);
            pdb.addPerson(p);
        });

        // Remove person from database
        view.getPersonsPanel().addListenerRemovePerson(e -> {
            Person p = view.getPersonsPanel().getSelectedPerson();
            if (p != null)
                pdb.remPerson(p);
        });

        // Show detailed ticket information
        view.getTicketListPanel().addListenerShowTicket(e -> {
            Ticket t = view.getTicketListPanel().getSelectedTicket();
            if (t != null)
                view.getTicketListPanel().showTicketInfo(t);
        });

        // Remove ticket from database
        view.getTicketListPanel().addListenerRemoveTicket(e -> {
            Ticket t = view.getTicketListPanel().getSelectedTicket();
            if (t != null)
                tdb.remTicket(t);
        });

    }

}
