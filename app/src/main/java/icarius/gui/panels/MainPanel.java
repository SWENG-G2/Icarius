package icarius.gui.panels;

import javax.swing.JTabbedPane;

import icarius.gui.Gui;
import icarius.gui.tabs.AdminTab;
import icarius.gui.tabs.MainTab;

public class MainPanel extends JTabbedPane {
    MainTab databaseTab;
    AdminTab adminTab = new AdminTab();

    public MainPanel(Gui gui) {
        databaseTab = new MainTab(gui);
        addTab("Main", databaseTab);
    }

    public void showAdmin() {
        addTab("Admin", adminTab);
    }

    public void hideAdmin() {
        removeAll();
        addTab("Main", databaseTab);
    }
}
