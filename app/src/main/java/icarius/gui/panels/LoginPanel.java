package icarius.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginPanel extends JPanel {
    public JTextField usernameField = new JTextField("", 20);
    public JPasswordField passwordField = new JPasswordField("", 20);

    public LoginPanel(ActionListener loginAction) {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(5,5,5,5));
        addLabels();
        addFields(loginAction);
        addLoginButton(loginAction);
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

    private void addFields(ActionListener loginAction) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        
        c.gridy = 0;
        add(usernameField, c);

        passwordField.addActionListener(loginAction);

        c.gridy = 1;
        add(passwordField, c);
    }

    private void addLoginButton(ActionListener loginAction) {
        // Create Button
        JButton logInButton = new JButton("Login");
        logInButton.addActionListener(loginAction);

        // Add Button
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        add(logInButton, c);
    }
}
