package view.panels.NewTicket;

import database.PersonDatabase;
import person.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

public class NewTicketPanel extends JPanel {
    JComboBox<String> cbTicketType;
    JTextField tfTicketDescription;
    EntriesPanel entriesPanel;
    JButton btAddEntry;
    JButton btAddTicket;
    JButton btClearTicket;

    public NewTicketPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 0, 0);
        this.add(new JLabel("Ticket type: "), gbc);

        String[] ticketTypes = {"Airplane", "Movie", "Restaurant"};
        cbTicketType = new JComboBox<>(ticketTypes);
        gbc.gridx = 7;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 0, 0, 10);
        this.add(cbTicketType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 6;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 0, 0);
        this.add(new JLabel("Description: "), gbc);


        tfTicketDescription = new JTextField();
        gbc.gridx = 7;
        gbc.gridy = 1;
        gbc.gridwidth = 6;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 0, 0, 10);
        this.add(tfTicketDescription, gbc);

        entriesPanel = new EntriesPanel();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 12;
        gbc.gridheight = 20;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(entriesPanel, gbc);

        btAddTicket = new JButton("Add ticket");
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 10, 10, 1);
        this.add(btAddTicket, gbc);

        btAddEntry = new JButton("Add entry");
        gbc.gridx = 4;
        gbc.gridy = 12;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 1, 10, 1);
        this.add(btAddEntry, gbc);

        btClearTicket = new JButton("Clear");
        gbc.gridx = 8;
        gbc.gridy = 12;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 1, 10, 10);
        this.add(btClearTicket, gbc);
    }

    public String getSelectedTicketType() {
        return (String) cbTicketType.getSelectedItem();
    }

    public String getTicketDescription() {
        return tfTicketDescription.getText();
    }

    public void setTicketDescription(String value) {
        tfTicketDescription.setText(value);
    }

    public EntriesPanel getEntriesPanel() {
        return entriesPanel;
    }

    public void addListenerAddTicket(ActionListener listener) {
        btAddTicket.addActionListener(listener);
    }

    public void addListenerAddEntry(ActionListener listener) {
        btAddEntry.addActionListener(listener);
    }

    public void addListenerClearTicket(ActionListener listener) {
        btClearTicket.addActionListener(listener);
    }

    public void updatePersonList(Observable o) {
        PersonDatabase pdb = (PersonDatabase) o;
        ArrayList<Person> persons = pdb.getPersons();
        Person everyone = pdb.getEveryone();

        entriesPanel.updatePersonList(persons, everyone);
    }
}


