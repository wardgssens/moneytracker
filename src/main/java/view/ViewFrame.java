package view;

import controller.Controller;
import view.panels.PersonsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ViewFrame extends JFrame implements Observer {
    PersonsPanel personsPanel;
    // Controller

    public ViewFrame()
    {
        super("Money tracker");
    }

    public void initialize(Controller controller)
    {
        this.setSize(375, 667);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        // Pass controller to panels
        personsPanel = new PersonsPanel(controller);

        this.add(personsPanel);
        this.setVisible(true);
    }

    public void update(Observable o, Object arg) {
        switch ((String) arg) {
            case "person-list":
                personsPanel.updateList(o);
                break;
        }
    }
}
