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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import icarius.App;
import icarius.entities.User;
import icarius.gui.frames.MainFrame;
import icarius.http.ConnectionException;

public class CreateUserTab extends JPanel {
    private JTextField nameField;
    private JPasswordField passField;
    private JPasswordField confirmPassField;
    private JComboBox<String> roleBox;

    private String[] roles = {"User", "Admin"};
    
    // TODO - Confirm password field

    public CreateUserTab() {
        // Configure Layout
        GridBagConstraints c = configure();

        // Add Fields
        nameField = addTextField("Username:", c);
        passField = addPasswordField("Password:", c);
        confirmPassField = addPasswordField("Confirm Password", c);

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

        // Returns added textfield
        private JPasswordField addPasswordField(String labelText, GridBagConstraints c) {
            // Configure Layout
            c.fill = GridBagConstraints.NONE;
            c.gridx = 0;
            
            // Add Label
            add(new JLabel(labelText), c);
    
            // Add TextField
            c.gridx++;
            c.fill = GridBagConstraints.HORIZONTAL;
            JPasswordField passwordField = new JPasswordField(18);
            add(passwordField, c);
    
            // Increment y for next item
            c.gridy++;
            return passwordField;
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
            if (!nameField.getText().isBlank() && !String.valueOf(passField.getPassword()).isBlank()){
                if (String.valueOf(passField.getPassword()).equals(String.valueOf(confirmPassField.getPassword()))){
                    // Create user with entered username
                    User user = new User(App.userClient, nameField.getText());
                    
                    // If admin role selected set user to admin
                    if (roleBox.getSelectedItem().equals("Admin")) {
                        user.setAdmin(true);
                    }
                    
                    // Create user in server
                    try {
                        if (user.create(String.valueOf(passField.getPassword()), null)) {
                            // User created successfully
                            frame.setNotification("User: "+nameField.getText()+" Created", null);
                            frame.getUsersTab().userListPanel.updateUserList();
                            nameField.setText("");
                            passField.setText("");
                            confirmPassField.setText("");
                        } else {
                            // Failed to create user
                            frame.setNotification("Failed to create User: "+nameField.getText(), Color.RED);
                        }
                        
                        // Refresh user list
                        frame.getUsersTab().userListPanel.updateUserList();
                    } catch (ConnectionException ce) {
                        frame.setNotification(ce.getMessage(), Color.RED);
                    }
                }else{
                    frame.setNotification("Password and confirm password field must match", null);
                }
            }else{
                frame.setNotification("Username and password fields cannot be left blank", null);
            }
        }
    };
}
