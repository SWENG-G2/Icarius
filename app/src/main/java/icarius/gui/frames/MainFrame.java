package icarius.gui.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import icarius.App;
import icarius.gui.Gui;
import icarius.gui.tabs.CreateUserTab;
import icarius.gui.tabs.MainTab;
import icarius.gui.tabs.UsersTab;
import lombok.Getter;

public class MainFrame extends JFrame {
    private @Getter MainTab mainTab;
    private @Getter UsersTab usersTab;
    private JLabel notificationLabel = new JLabel(" ");

    /**
     * Initialises the main frame of the GUI
     * 
     * @param pos
     */
    public MainFrame(Point pos) {
        setTitle("Icarius");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Gui.MAIN_FRAME_X_SIZE, Gui.MAIN_FRAME_Y_SIZE);
        setLocationRelativeTo(null);
        // setResizable(false);
        if (pos != null)
            setLocation(pos);
        setVisible(true);

        add(getMainPanel());
        add(getFooterPanel(), BorderLayout.SOUTH);

        validate();
    }

    /**
     * sets the message in the notification section of the footer panel
     * 
     * @param message
     * @param color
     */
    public void setNotification(String message, Color color) {
        notificationLabel.setText(message);
        if (color == null) {
            notificationLabel.setForeground(Color.WHITE);
        } else {
            notificationLabel.setForeground(color);
        }
    }

    /**
     * Creates the main panel and adds the mainTab. If the logged in user is an
     * admin users and create user tabs
     * 
     * @return JTabbedPane mainPanel
     */
    private JTabbedPane getMainPanel() {
        JTabbedPane mainPanel = new JTabbedPane();
        mainTab = new MainTab();
        mainPanel.addTab("Main", mainTab);
        if (App.userClient.isAdmin()) {
            usersTab = new UsersTab(this);
            mainPanel.addTab("Users", usersTab);
            mainPanel.addTab("Create User+", new CreateUserTab());
        }
        return mainPanel;
    }

    /**
     * creates and sets the layout of the footer panel, which contains the log out
     * button and notification label.
     * 
     * @return JPanel footerPanel
     */
    private JPanel getFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));

        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new LoginFrame(getLocation());
                dispose(); // Close Main Frame
            }
        });
        footerPanel.add(logoutButton, BorderLayout.LINE_START);

        footerPanel.add(notificationLabel, BorderLayout.LINE_END);

        return footerPanel;
    }
}
