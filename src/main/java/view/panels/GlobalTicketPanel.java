package view.panels;

import database.TicketDatabase;
import ticket.Ticket;
import ticket.TicketEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;

public class GlobalTicketPanel extends JPanel {
    private DefaultListModel<TicketEntry> paymentListModel;
    private JList<TicketEntry> paymentJList;
    private JButton btCalculate;

    public GlobalTicketPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());

        paymentListModel = new DefaultListModel<>();
        paymentJList = new JList<>(paymentListModel);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 16;
        gbc.gridheight = 16;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(paymentJList, gbc);

        btCalculate = new JButton("Calculate global ticket");
        gbc = new GridBagConstraints();
        gbc.gridx = 14;
        gbc.gridy = 18;
        gbc.gridwidth = 16;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 10, 10, 10);
        this.add(btCalculate, gbc);
    }

    public void addListenerCalculate(ActionListener listener) {
        btCalculate.addActionListener(listener);
    }

    public void updateGlobalTicket(Observable o) {
        TicketDatabase tdb = (TicketDatabase) o;
        Ticket t = tdb.getGlobalTicket();

        paymentListModel.removeAllElements();
        if (t != null) {
            for (TicketEntry e : t.getEntries()) {
                paymentListModel.addElement(e);
            }
        }
    }
}
