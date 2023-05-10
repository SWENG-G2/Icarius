package icarius.gui.forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import icarius.entities.Bird;
import icarius.entities.Campus;
import icarius.gui.panels.FormPanel;

public class AddForm extends JPanel {
    private FormPanel parent;
    private Campus campus;

    private JTextField NameField;

    // Add Campus Form
    public AddForm(FormPanel parent) {
        // Configure Layout
        GridBagConstraints c = configure(parent);

        // Add Inputs
        NameField = addTextField("Campus Name:", c);

        // Add Buttons
        addCreateCampusButton(c);
    }

    // Add Bird Form
    public AddForm(FormPanel parent, Campus campus) {
        // Configure Layout
        GridBagConstraints c = configure(parent);
        this.campus = campus;

        // Add Inputs
        NameField = addTextField("Bird Name:", c);

        // Add Buttons
        addCreateBirdButton(c);
    }

    // Add Bird Form

    private GridBagConstraints configure(FormPanel parent) {
        // Configure layout
        this.parent = parent;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        c.ipadx = 8;
        c.ipady = 8;
        c.gridy = 0;
        return c;
    }

    // Returns added textfield
    private JTextField addTextField(String labelText, GridBagConstraints c) {
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        
        // Add Label
        add(new JLabel(labelText), c);

        // Add TextField
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField textField = new JTextField(18);
        add(textField, c);

        // Increment y for next item
        c.gridy++;
        return textField;
    }

    public void addCreateCampusButton(GridBagConstraints c) {
        c.gridx = 1;
        JButton createButton = new JButton("Create Campus");
        createButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                Campus newCampus = new Campus(parent.gui.user.getOkHttpClient());
                newCampus.setName(NameField.getText());
                // TODO - (CONNALL) make create methods return boolean
                newCampus.create(parent.gui.user, null);

                // TODO - Uncomment when above todo complete
                // if ( campus.create(parent.gui.user, null) ) {
                //     // Success
                //     parent.gui.footerPanel.setNotification(newCampus.getName() + " added to campus list.");
                // } else {
                //     // Failure
                //     parent.gui.footerPanel.setNotification("Failed to add " + newCampus.getName() + " to campus list!");
                // }

                // Refresh Tree
                parent.parent.refreshDatabaseTree();
            }
        });
        add(createButton, c);
    }

    public void addCreateBirdButton(GridBagConstraints c) {
        c.gridx = 1;
        JButton createButton = new JButton("Create Bird");
        createButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                Bird newBird = new Bird(parent.gui.user.getOkHttpClient());
                newBird.setName(NameField.getText());
                newBird.setCampusId(campus.getId());
                // TODO - (CONNALL) make create methods return boolean
                newBird.create(parent.gui.user, null);

                // TODO - Uncomment when above todo complete
                // if ( newBird.create(parent.gui.user, null) ) {
                //     // Success
                //     parent.gui.footerPanel.setNotification(newBird.getName() + " added to " + campus.getName());
                // } else {
                //     // Failure
                //     parent.gui.footerPanel.setNotification("Failed to add " + newBird.getName() + " to " + campus.getName() + "!");
                // }

                // Refresh Tree
                parent.parent.refreshDatabaseTree();
            }
        });
        add(createButton, c);
    }
}
