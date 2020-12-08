package controller;

import database.PersonDatabase;
import database.TicketDatabase;
import factory.AbstractFactory;
import factory.FactoryProvider;
import person.Person;
import ticket.Ticket;
import view.ViewFrame;

public class Controller {
    private AbstractFactory<Ticket> ticketFactory;
    private AbstractFactory<Person> personFactory;

    // Model
    private PersonDatabase pdb;
    private TicketDatabase tdb;

    // View
    private ViewFrame view;

    public Controller(PersonDatabase pdb, TicketDatabase tdb) {
        ticketFactory = FactoryProvider.getFactory("ticket");
        personFactory = FactoryProvider.getFactory("person");

        this.pdb = pdb;
        this.tdb = tdb;


    }

    private void initListeners() {
        // Add person to database
        view.getPersonsPanel().addListenerAddPerson(e -> {
            String name = view.getPersonsPanel().getTextAddPerson();
            Person p = personFactory.create("individual", name);
            pdb.addPerson(p);
            System.out.println("Added" + p + " to database.");
        });


    }

}
