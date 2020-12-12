package controller;

import database.PersonDatabase;
import database.TicketDatabase;
import factory.AbstractFactory;
import factory.FactoryProvider;
import person.Person;
import ticket.Ticket;
import view.ViewFrame;
import view.panels.NewTicket.EntryPanel;

import java.util.ArrayList;

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

            if (name != null && name.length() > 0 && name.length() < 20) {
                view.getPersonsPanel().setTextAddPerson("");
                Person p = personFactory.create("individual", name);
                pdb.addPerson(p);
            } else {
                view.showError("Enter a name.");
            }
        });

        // Remove person from database
        view.getPersonsPanel().addListenerRemovePerson(e -> {
            Person p = view.getPersonsPanel().getSelectedPerson();
            if (p != null)
                pdb.remPerson(p);
            else
                view.showError("Select a player to remove.");
        });

        // Show detailed ticket information on click of button "Show info".
        view.getTicketListPanel().addListenerShowTicket(e -> {
            Ticket t = view.getTicketListPanel().getSelectedTicket();
            if (t != null)
                view.getTicketListPanel().showTicketInfo(t);
            else
                view.showError("Select a ticket to show more information.");
        });

        // Show detailed ticket information when a ticket is double clicked.
        view.getTicketListPanel().addListenerDoubleClickItem(e -> {
            Ticket t = view.getTicketListPanel().getSelectedTicket();
            if (t != null)
                view.getTicketListPanel().showTicketInfo(t);
            else
                view.showError("Select a ticket to show more information.");
        });

        // Remove ticket from database
        view.getTicketListPanel().addListenerRemoveTicket(e -> {
            Ticket t = view.getTicketListPanel().getSelectedTicket();
            if (t != null)
                tdb.remTicket(t);
            else
                view.showError("Select a ticket to remove.");
        });

        // Add ticket to database
        view.getNewTicketPanel().addListenerAddTicket(e -> {
            String ticketType = view.getNewTicketPanel().getSelectedTicketType();
            String description = view.getNewTicketPanel().getTicketDescription();

            if (description != null && description.length() > 0 ) {
                Ticket t = ticketFactory.create(ticketType, description);

                try {
                    ArrayList<EntryPanel> entries = view.getNewTicketPanel().getEntriesPanel().getEntryPanels();
                    for (EntryPanel entry : entries) {
                        double amount = Double.parseDouble(entry.getTextAmount());
                        Person paidBy = entry.getSelectedPaidBy();
                        Person paidFor = entry.getSelectedPaidFor();
                        t.addEntry(amount, paidBy, paidFor);
                    }

                    tdb.addTicket(t);
                    view.getNewTicketPanel().setTicketDescription("");
                    view.getNewTicketPanel().getEntriesPanel().clear();

                } catch (NumberFormatException ex) {
                    view.showError("One of the amount fields is not valid.");
                }
            } else {
                view.showError("Please fill in the description field.");
            }
        });

        // Add an entry to the entries panel.
        view.getNewTicketPanel().addListenerAddEntry(e -> {
            view.getNewTicketPanel().getEntriesPanel().addEntry();
            pdb.updatePersons(true);
        });

        // Clear the new ticket form.
        view.getNewTicketPanel().addListenerClearTicket(e -> {
            view.getNewTicketPanel().setTicketDescription("");
            view.getNewTicketPanel().getEntriesPanel().clear();
            pdb.updatePersons(true);
        });

        // Calculate the global ticket.
        view.getGlobalTicketPanel().addListenerCalculate(e -> {
            tdb.calculateGlobalTicket();
        });
    }
}
