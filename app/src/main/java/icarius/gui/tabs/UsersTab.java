package icarius.gui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import icarius.auth.UserClient;
import icarius.gui.Gui;
import icarius.gui.panels.UserInfoPanel;
import icarius.gui.panels.UserListPanel;

public class UsersTab extends JPanel {
    public UserListPanel userListPanel;

    public UsersTab(UserClient user) {
        // Configure Tab
        setLayout(new BorderLayout());

        // Create and Add Panel containing a list of created users displayed as buttons
        userListPanel = new UserListPanel(user);
        // Set user list panel width to 1/3 of frame
        int width = Gui.MAIN_FRAME_X_SIZE / 3;
        userListPanel.setPreferredSize(new Dimension(width, this.getHeight()));
        add(userListPanel, BorderLayout.WEST);

        // Create and Add Panel to edit selected entity
        UserInfoPanel userInfoPanel = new UserInfoPanel();
        add(userInfoPanel, BorderLayout.CENTER);
    }
}
