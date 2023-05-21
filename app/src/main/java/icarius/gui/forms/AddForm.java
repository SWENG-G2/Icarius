package icarius.gui.forms;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import icarius.auth.User;
import icarius.entities.Bird;
import icarius.entities.Campus;
import icarius.gui.frames.MainFrame;
import icarius.gui.tabs.MainTab;
import icarius.http.ConnectionException;

public class AddForm extends JPanel {
    private Campus campus;
    private JTextField NameField;

    // Add Campus Form
    public AddForm() {
        // Configure Layout
        GridBagConstraints c = configure();

        // Add Inputs
        NameField = addTextField("Campus Name:", c);

        // Add Buttons
        addCreateCampusButton(c);
    }

    // Add Bird Form
    public AddForm(Campus campus) {
        // Configure Layout
        GridBagConstraints c = configure();
        this.campus = campus;

        // Add Inputs
        NameField = addTextField("Bird Name:", c);

        // Add Buttons
        addCreateBirdButton(c);
    }

    // Add Bird Form

    private GridBagConstraints configure() {
        // Configure layout
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
                MainFrame frame = (MainFrame) getTopLevelAncestor();
                MainTab mainTab = frame.getMainTab();
                User user = frame.getUser();
            
                Campus newCampus = new Campus(user);
                newCampus.setName(NameField.getText());
                try {
                    if ( newCampus.create(user, null) ) {
                        // Success
                        frame.setNotification(newCampus.getName() + " added to campus list.", null);
                    } else {
                        // Failure
                        frame.setNotification("Failed to add " + newCampus.getName() + " to campus list!", null);
                    }

                    // Refresh Tree
                    mainTab.refreshDatabaseTree();
                } catch (ConnectionException ce) {
                    frame.setNotification(ce.getMessage(), Color.RED);
                }
                // TODO - (Connall) No Permission excemption
            }
        });
        add(createButton, c);
    }

    public void addCreateBirdButton(GridBagConstraints c) {
        c.gridx = 1;
        JButton createButton = new JButton("Create Bird");
        createButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                MainFrame frame = (MainFrame) getTopLevelAncestor();
                MainTab mainTab = frame.getMainTab();
                User user = frame.getUser();
                
                Bird newBird = new Bird(user);
                newBird.setName(NameField.getText());
                newBird.setCampusId(campus.getId());
                try {
                    if ( newBird.create(user, null) ) {
                        // Success
                        frame.setNotification(newBird.getName() + " added to " + campus.getName(), null);
                    } else {
                        // Failure
                        frame.setNotification("Failed to add " + newBird.getName() + " to " + campus.getName() + "!", null);
                    }
                } catch (ConnectionException ce) {
                    frame.setNotification(ce.getMessage(), Color.RED);
                }
                // TODO - (Connall) No Permission excemption

                // Refresh Tree
                mainTab.refreshDatabaseTree();
            }
        });
        add(createButton, c);
    }
}
