package icarius.gui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import icarius.auth.UserClient;
import icarius.entities.Database;
import icarius.gui.Gui;
import icarius.gui.frames.MainFrame;
import icarius.gui.panels.TreePanel;
import icarius.gui.panels.FormPanel;

public class MainTab extends JPanel {
    public MainTab(UserClient user) {
        // Configure Tab
        setLayout(new BorderLayout());
        
        // Create and Add Panel containing database tree
        add(getTreePanel(user), BorderLayout.WEST);

        // Create and Add Panel to edit selected entity
        add(new FormPanel(), BorderLayout.CENTER);
    }

    public TreePanel getTreePanel(UserClient user) {
        // Fetch Database
        Database db = new Database(user);
        TreePanel dbTreePanel = new TreePanel(db);

        // Set tree panel width to 1/3 of frame
        int width = Gui.MAIN_FRAME_X_SIZE / 3;
        dbTreePanel.setPreferredSize(new Dimension(width, this.getHeight()));
        return dbTreePanel;
    }
    
    public void refreshDatabaseTree() {
        removeAll();

        // Create and Add Panel containing database tree
        UserClient user = ((MainFrame) getTopLevelAncestor()).getUser();
        add(getTreePanel(user), BorderLayout.WEST);

        // Create and Add Panel to edit selected entity
        add(new FormPanel(), BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
