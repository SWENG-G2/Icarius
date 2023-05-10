package icarius.gui.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import icarius.auth.Credentials;
import icarius.gui.Gui;
import lombok.Getter;

public class LoginPanel extends JPanel {
    private Gui gui;
    private @Getter JTextField usernameField = new JTextField("", 20);
    private @Getter JPasswordField passwordField = new JPasswordField("", 20);

    public LoginPanel(Gui gui) {
        this.gui = gui;
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(5,5,5,5));
        addLabels();
        addFields();
        addButton();
    }

    public void resetForm() {
        usernameField.setText("");
        passwordField.setText("");
    }

    private void addLabels() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.anchor = GridBagConstraints.EAST;
        c.ipadx = 8;

        c.gridy = 0;
        add(new JLabel("Username:"), c);

        c.gridy = 1;
        add(new JLabel("Key:"), c);
    }

    private void addFields() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        
        c.gridy = 0;
        add(usernameField, c);

        c.gridy = 1;
        add(passwordField, c);
    }

    private void addButton() {
        // Create Button
        JButton logInButton = new JButton("Login");
        logInButton.addActionListener(logInAction);

        // Add Button
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        add(logInButton, c);
    }

    // Actions
    private ActionListener logInAction = new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
            // Get Entered Credentials
            String username = getUsernameField().getText();
            String password = String.valueOf(getPasswordField().getPassword());
            Credentials credentials = new Credentials(username, password);

            // Validate Credentials
            gui.user.setCredentials(credentials);
            boolean valid = gui.user.validate(null);
            if (gui.user.getServerConnection() == false) return;
            if (valid) {
                // Valid Credentials
                if (gui.user.getAdmin()) {
                    // If Admin
                    gui.mainPanel.showAdmin();
                } else {
                    gui.mainPanel.hideAdmin();
                }
                gui.openMainPanel();
            } else {
                // Invalid Credentials
                gui.footerPanel.setNotification("Username or key incorrect", Color.RED);
            }
        }
    };
}
