package icarius.gui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import icarius.App;
import icarius.entities.Database;
import icarius.gui.Gui;
import icarius.gui.panels.TreePanel;
import icarius.gui.panels.FormPanel;

public class MainTab extends JPanel {
    public MainTab() {
        // Configure Tab
        setLayout(new BorderLayout());
        
        // Create and Add Panel containing database tree
        add(getTreePanel(), BorderLayout.WEST);

        // Create and Add Panel to edit selected entity
        add(new FormPanel(), BorderLayout.CENTER);
    }

    public TreePanel getTreePanel() {
        // Fetch Database
        App.db = new Database(App.userClient);
        TreePanel dbTreePanel = new TreePanel();

        // Set tree panel width to 1/3 of frame
        int width = Gui.MAIN_FRAME_X_SIZE / 3;
        dbTreePanel.setPreferredSize(new Dimension(width, this.getHeight()));
        return dbTreePanel;
    }
    
    public void refreshDatabaseTree() {
        removeAll();

        // Create and Add Panel containing database tree
        add(getTreePanel(), BorderLayout.WEST);

        // Create and Add Panel to edit selected entity
        add(new FormPanel(), BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
