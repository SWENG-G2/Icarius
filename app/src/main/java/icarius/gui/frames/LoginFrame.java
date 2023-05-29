package icarius.gui.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import icarius.App;
import icarius.auth.Credentials;
import icarius.gui.Gui;
import icarius.gui.panels.LoginPanel;
import icarius.http.ConnectionException;

public class LoginFrame extends JFrame {
    private LoginPanel loginPanel;
    public JLabel notificationLabel = new JLabel(" ", SwingConstants.CENTER);

    public LoginFrame(Point pos) {
        setTitle("Icarius Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Gui.MAIN_FRAME_X_SIZE, Gui.MAIN_FRAME_Y_SIZE);
        setLocationRelativeTo(null);
        setResizable(false);
        if (pos != null) setLocation(pos);
        setVisible(true);

        loginPanel = new LoginPanel(logInAction);
        add(loginPanel);

        notificationLabel.setForeground(Color.RED);
        notificationLabel.setBorder(new EmptyBorder(5,5,5,5));
        add(notificationLabel, BorderLayout.SOUTH);

        validate();
    }

    // Actions
    private ActionListener logInAction = new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
            // Get Entered Credentials
            String username = loginPanel.usernameField.getText();
            String password = String.valueOf(loginPanel.passwordField.getPassword());
            Credentials credentials = new Credentials(username, password);

            // Validate Credentials
            App.userClient.setCredentials(credentials);
            try {
                App.userClient.validate(null);
    
                if (App.userClient.getValid()) {
                    new MainFrame(getLocation());
                    dispose(); // Close the login frame
                } else {
                    // Invalid Credentials
                    notificationLabel.setText("Username or key incorrect");
                }
            } catch (ConnectionException ce) {
                notificationLabel.setText(ce.getMessage());
            }
        }
    };
}
