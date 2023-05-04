package icarius.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import icarius.gui.Gui;

public class FooterPanel extends JPanel {
    private Gui gui;
    private int horizontalPadding = 8;
    private int verticalPadding = 8;
    private JLabel notificationLabel = new JLabel("");
    private JButton logOutButton = new JButton("Log Out");

    public FooterPanel(Gui gui) {
        this.gui = gui;

        // Set Panel Layout
        setLayout(new FlowLayout(FlowLayout.LEFT, horizontalPadding, verticalPadding));

        // Add Footer Components
        logOutButton.addActionListener(logOutAction);
        add(logOutButton, BorderLayout.LINE_START);
        add(notificationLabel, BorderLayout.LINE_END);
    }

    public void setNotification(String text, Color color) {
        if (color != null) {
            notificationLabel.setForeground(color);
        }

        notificationLabel.setText(text);
        System.out.println(notificationLabel.getHeight());
    }

    public void resetNotification() {
        notificationLabel.setText(" ");
    }

    public void hideLogOutButton() {
        logOutButton.setVisible(false);
    }

    public void showLogOutButton() {
        logOutButton.setVisible(true);
    }

    private ActionListener logOutAction = new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
            gui.openLoginPanel();
        }
    };
}

