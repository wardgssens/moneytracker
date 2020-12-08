package view;

import ticket.Ticket;
import view.panels.PersonsPanel;
import view.panels.TicketListPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ViewFrame extends JFrame implements Observer {
    private PersonsPanel personsPanel;
    private TicketListPanel ticketListPanel;

    public ViewFrame()
    {
        super("Money tracker");
        initialize();
    }

    private void initialize()
    {
        this.setSize(375, 667);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        personsPanel = new PersonsPanel();
        tabbedPane.addTab("Persons", personsPanel);

        ticketListPanel = new TicketListPanel();
        tabbedPane.addTab("Ticket list", ticketListPanel);

        this.add(tabbedPane);
        this.setVisible(true);
    }

    public void update(Observable o, Object arg) {
        switch ((String) arg) {
            case "person-list":
                personsPanel.updatePersonList(o);
                break;
            case "ticket-list":
                ticketListPanel.updateTicketList(o);
                break;
        }
    }

    public PersonsPanel getPersonsPanel() {
        return this.personsPanel;
    }
    public TicketListPanel getTicketListPanel() { return this.ticketListPanel; }
}
