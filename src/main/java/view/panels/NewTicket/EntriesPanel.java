package view.panels.NewTicket;

import person.Person;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class EntriesPanel extends JPanel {
    ArrayList<EntryPanel> panels;

    public EntriesPanel() {
        panels = new ArrayList<>();
        panels.add(new EntryPanel());

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(panels.get(0));
    }

    public void clear() {
        panels.clear();
        this.removeAll();

        panels.add(new EntryPanel());
        this.add(panels.get(0));
    }

    public void addEntry() {
        EntryPanel panel = new EntryPanel();
        panels.add(panel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(panel);
    }

    public ArrayList<EntryPanel> getEntryPanels() {
        return panels;
    }

    public void updatePersonList(ArrayList<Person> persons, Person everyone) {
        for (EntryPanel entry : panels) {
            entry.setPersonList(persons, everyone);
        }
    }
}
