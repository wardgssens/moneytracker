package view.panels;

import database.TicketDatabase;
import ticket.Ticket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;

public class TicketListPanel extends JPanel {
    private DefaultListModel<Ticket> ticketListModel;
    private JList<Ticket> ticketJList;
    private JButton btShowTicket;
    private JButton btRemoveTicket;

    public TicketListPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());

        ticketListModel = new DefaultListModel<>();
        ticketJList = new JList<>(ticketListModel);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 16;
        gbc.gridheight = 16;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(ticketJList, gbc);

        btShowTicket = new JButton("Show");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 18;
        gbc.gridwidth = 8;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 10, 10, 1);
        this.add(btShowTicket, gbc);

        btRemoveTicket = new JButton("Remove");
        gbc = new GridBagConstraints();
        gbc.gridx = 10;
        gbc.gridy = 18;
        gbc.gridwidth = 8;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 1, 10, 10);
        this.add(btRemoveTicket, gbc);
    }

    public void addListenerShowTicket(ActionListener listener) {
        this.btShowTicket.addActionListener(listener);
    }

    public void addListenerDoubleClickItem(ActionListener listener) {
        // https://stackoverflow.com/questions/4344682/double-click-event-on-jlist-element
        this.ticketJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2)
                    listener.actionPerformed(new ActionEvent(e.getSource(), 1, "double-click"));
            }
        });
    }

    public void addListenerRemoveTicket(ActionListener listener) {
        this.btRemoveTicket.addActionListener(listener);
    }

    public Ticket getSelectedTicket() {
        return ticketJList.getSelectedValue();
    }

    public void showTicketInfo(Ticket t) {
        JOptionPane.showMessageDialog(this, t.detailsToString(), t.toString(), JOptionPane.PLAIN_MESSAGE);
    }


    public void updateTicketList(Observable o) {
        TicketDatabase tdb = (TicketDatabase) o;
        ArrayList<Ticket> tickets = tdb.getTickets();

        ticketListModel.clear();
        for (Ticket t : tickets) {
            ticketListModel.addElement(t);
        }
    }
}
