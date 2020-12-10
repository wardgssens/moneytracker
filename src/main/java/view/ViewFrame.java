package view;

import com.formdev.flatlaf.FlatLightLaf;
import view.panels.GlobalTicketPanel;
import view.panels.NewTicket.NewTicketPanel;
import view.panels.PersonsPanel;
import view.panels.TicketListPanel;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class ViewFrame extends JFrame implements Observer {
    private PersonsPanel personsPanel;
    private TicketListPanel ticketListPanel;
    private NewTicketPanel newTicketPanel;
    private GlobalTicketPanel globalTicketPanel;

    public ViewFrame()
    {
        super("Money tracker");
        initialize();
    }

    private void initialize()
    {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
        }

        this.setSize(375, 667);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        personsPanel = new PersonsPanel();
        tabbedPane.addTab("Persons", personsPanel);

        ticketListPanel = new TicketListPanel();
        tabbedPane.addTab("Ticket list", ticketListPanel);

        newTicketPanel = new NewTicketPanel();
        tabbedPane.addTab("New ticket", newTicketPanel);

        globalTicketPanel = new GlobalTicketPanel();
        tabbedPane.addTab("Global ticket", globalTicketPanel);

        this.add(tabbedPane);
        this.setVisible(true);
    }

    public void update(Observable o, Object arg) {
        switch ((String) arg) {
            case "person-list":
                personsPanel.updatePersonList(o);
                newTicketPanel.updatePersonList(o);
                break;
            case "ticket-list":
                ticketListPanel.updateTicketList(o);
                break;
            case "global-ticket":
                globalTicketPanel.updateGlobalTicket(o);
                break;
        }
    }

    public PersonsPanel getPersonsPanel() {
        return this.personsPanel;
    }
    public TicketListPanel getTicketListPanel() { return this.ticketListPanel; }
    public NewTicketPanel getNewTicketPanel() { return this.newTicketPanel; }
    public GlobalTicketPanel getGlobalTicketPanel() { return this.globalTicketPanel; }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
