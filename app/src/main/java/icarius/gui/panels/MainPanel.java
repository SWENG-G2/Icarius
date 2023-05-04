package icarius.gui.panels;

import javax.swing.JTabbedPane;

import icarius.gui.Gui;
import icarius.gui.tabs.UsersTab;
import icarius.gui.tabs.CreateUserTab;
import icarius.gui.tabs.MainTab;

public class MainPanel extends JTabbedPane {
    MainTab databaseTab;
    UsersTab usersTab = new UsersTab();
    CreateUserTab createUsersTab = new CreateUserTab();

    public MainPanel(Gui gui) {
        databaseTab = new MainTab(gui);
        addTab("Main", databaseTab);
    }

    public void showAdmin() {
        addTab("Users", usersTab);
        addTab("Create User+", createUsersTab);
    }

    public void hideAdmin() {
        removeAll();
        addTab("Main", databaseTab);
    }
}
