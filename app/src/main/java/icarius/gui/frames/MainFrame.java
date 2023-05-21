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

import icarius.auth.User;
import icarius.gui.Gui;
import icarius.gui.tabs.CreateUserTab;
import icarius.gui.tabs.MainTab;
import icarius.gui.tabs.UsersTab;
import lombok.Getter;

public class MainFrame extends JFrame {
    public @Getter User user;
    public @Getter MainTab mainTab;
    private JLabel notificationLabel = new JLabel(" ");

    public MainFrame(Point pos, User user) {
        this.user = user;

        setTitle("Icarius");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Gui.MAIN_FRAME_X_SIZE, Gui.MAIN_FRAME_Y_SIZE);
        setLocationRelativeTo(null);
        setResizable(false);
        if (pos != null) setLocation(pos);
        setVisible(true);

        add(getMainPanel());
        add(getFooterPanel(), BorderLayout.SOUTH);

        validate();
    }

    public void setNotification(String message, Color color) {
        notificationLabel.setText(message);
        if (color == null) {
            notificationLabel.setForeground(Color.WHITE);
        } else {
            notificationLabel.setForeground(color);
        }
    }

    private JTabbedPane getMainPanel() {
        JTabbedPane mainPanel = new JTabbedPane();
        mainTab = new MainTab(user);
        mainPanel.addTab("Main", mainTab);
        if (user.isAdmin()) {
            mainPanel.addTab("Users", new UsersTab());
            mainPanel.addTab("Create User+", new CreateUserTab());
        }
        return mainPanel;
    }

    private JPanel getFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));

        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new LoginFrame(getLocation(), user);
                dispose(); // Close Main Frame
            }
        });
        footerPanel.add(logoutButton, BorderLayout.LINE_START);

        footerPanel.add(notificationLabel, BorderLayout.LINE_END);

        return footerPanel;
    }
}
