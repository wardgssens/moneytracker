package view.panels.NewTicket;

import person.Person;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EntryPanel extends JPanel {
    private JComboBox<Person> cbPaidBy;
    private JComboBox<Person> cbPaidFor;
    private JTextField tfAmount;

    public EntryPanel() {
        this.setLayout(new GridLayout(3, 2, 0, 5));

        tfAmount = new JTextField(6);
        this.add(new JLabel("Amount: "));
        this.add(tfAmount);

        cbPaidBy = new JComboBox<>();
        this.add(new JLabel("Paid by: "));
        this.add(cbPaidBy);

        cbPaidFor = new JComboBox<>();
        this.add(new JLabel("Paid for: "));
        this.add(cbPaidFor);
    }

    public String getTextAmount() {
        return tfAmount.getText();
    }

    public Person getSelectedPaidBy() {
        return (Person) cbPaidBy.getSelectedItem();
    }

    public Person getSelectedPaidFor() {
        return (Person) cbPaidFor.getSelectedItem();
    }

    public void setPersonList(ArrayList<Person> persons, Person everyone) {
        // Get current selections
        Person paidBy = getSelectedPaidBy();
        Person paidFor = getSelectedPaidFor();

        cbPaidFor.removeAllItems();
        cbPaidBy.removeAllItems();

        // Add all items to ComboBoxes
        for (Person p : persons) {
            cbPaidFor.addItem(p);
            cbPaidBy.addItem(p);
        }
        cbPaidFor.addItem(everyone);

        // Reselect the previous selection. If the previous selection is not in the ComboBox, select defaults.
        if (paidBy != null)
            cbPaidBy.setSelectedItem(paidBy);
        else
            cbPaidBy.setSelectedItem(null);

        if (paidFor != null)
            cbPaidFor.setSelectedItem(paidFor);
        else
            cbPaidFor.setSelectedItem(everyone);
    }
}
