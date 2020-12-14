package view.panels.NewTicket;

import person.Person;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class EntriesPanel extends JPanel {
    ArrayList<EntryPanel> entries;

    public EntriesPanel() {
        entries = new ArrayList<>();
        entries.add(new EntryPanel());

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(entries.get(0));
    }

    public void clear() {
        entries.clear();
        this.removeAll();

        entries.add(new EntryPanel());
        this.add(entries.get(0));
    }

    public void addEntry() {
        EntryPanel panel = new EntryPanel();
        entries.add(panel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(panel);
    }

    public ArrayList<EntryPanel> getEntryPanels() {
        return entries;
    }

    public void updatePersonList(ArrayList<Person> persons, Person everyone) {
        for (EntryPanel entry : entries) {
            entry.setPersonList(persons, everyone);
        }
    }
}
