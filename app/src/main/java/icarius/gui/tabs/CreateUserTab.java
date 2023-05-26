package icarius.gui.tabs;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import icarius.auth.UserClient;
import icarius.entities.User;
import icarius.gui.frames.MainFrame;
import icarius.http.ConnectionException;

public class CreateUserTab extends JPanel {
    private JTextField nameField;
    private JTextField passField;
    private JComboBox<String> roleBox;

    private String[] roles = {"User", "Admin"};

    public CreateUserTab() {
        // Configure Layout
        GridBagConstraints c = configure();

        // Add Fields
        nameField = addTextField("Username:", c);
        passField = addTextField("Password:", c);
        roleBox = addComboBox("Role:", roles, c);

        // Add Create Button
        JButton createButton = new JButton("Create User");
        createButton.addActionListener(buttonAction);
        add(createButton, c);
    }

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

    private JComboBox<String> addComboBox(String labelText, String[] options, GridBagConstraints c) {
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;

        // Add Label
        add(new JLabel(labelText), c);

        // Add ComboBox
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JComboBox<String> comboBox = new JComboBox<>(options);
        add(comboBox, c);

        // Increment y for next item
        c.gridy++;
        return comboBox;
    }

    private ActionListener buttonAction = new ActionListener() {
        public void actionPerformed(ActionEvent ae){
            MainFrame frame = (MainFrame) getTopLevelAncestor();
            UserClient userClient = frame.getUser();
            if (!nameField.getText().isBlank() && !passField.getText().isBlank()){
                // Create user with entered username
                User user = new User(userClient, nameField.getText());
                
                // If admin role selected set user to admin
                if (roleBox.getSelectedItem().equals("Admin")) {
                    user.setAdmin(true);
                }
                
                // Create user in server
                try {
                    if (user.create(passField.getText(), null)) {
                        // User created successfully
                        frame.setNotification("User: "+nameField.getText()+" Created", null);
                        frame.getUsersTab().userListPanel.updateUserList();
                        // TODO - set text fields to blank / reset create user form
                        nameField.setText("");
                        passField.setText("");
                    } else {
                        // Failed to create user
                        frame.setNotification("Failed to create User: "+nameField.getText(), Color.RED);
                    }
                    //gui.mainPanel.updateUserList(null);
                } catch (ConnectionException ce) {
                    frame.setNotification(ce.getMessage(), Color.RED);
                }
            }else{
                frame.setNotification("Username and password fields cannot be left blank", null);
            }
        }
    };
}
