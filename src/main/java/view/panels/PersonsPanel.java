package view.panels;

import database.PersonDatabase;
import person.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

public class PersonsPanel extends JPanel {

    private JList<Person> personJList;
    private DefaultListModel<Person> personListModel;
    private JTextField tfAddPerson;
    private JButton btAddPerson;
    private JButton btRemPerson;

    public PersonsPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());

        personListModel = new DefaultListModel<>();
        personJList = new JList<>(personListModel);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 16;
        gbc.gridheight = 16;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10,10,10,10);
        this.add(personJList, gbc);

        tfAddPerson = new JTextField(14);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 18;
        gbc.gridwidth = 12;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0,10,10,1);
        this.add(tfAddPerson, gbc);

        btAddPerson = new JButton("+");
        gbc = new GridBagConstraints();
        gbc.gridx = 14;
        gbc.gridy = 18;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0,1,10,1);
        this.add(btAddPerson, gbc);

        btRemPerson = new JButton("-");
        gbc = new GridBagConstraints();
        gbc.gridx = 15;
        gbc.gridy = 18;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0,1,10,10);
        this.add(btRemPerson, gbc);
    }

    public void addListenerAddPerson(ActionListener actionListener)
    {
        this.btAddPerson.addActionListener(actionListener);
    }

    public void addListenerRemovePerson(ActionListener actionListener)
    {
        this.btRemPerson.addActionListener(actionListener);
    }

    public String getTextAddPerson() {
        return tfAddPerson.getText();
    }

    public void setTextAddPerson(String value) {
        tfAddPerson.setText(value);
    }

    public Person getSelectedPerson() {
        return personJList.getSelectedValue();
    }

    public void updatePersonList(Observable o) {
        PersonDatabase pdb = (PersonDatabase) o;
        ArrayList<Person> persons = pdb.getPersons();

        personListModel.clear();
        for (Person p : persons) {
            personListModel.addElement(p);
        }
    }
}
