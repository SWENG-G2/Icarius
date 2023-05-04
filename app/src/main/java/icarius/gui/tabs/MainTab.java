package icarius.gui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import icarius.entities.Database;
import icarius.gui.Gui;
import icarius.gui.panels.TreePanel;
import icarius.gui.panels.FormPanel;

public class MainTab extends JPanel {
    Gui gui;
    public FormPanel editPanel;
    public Database database;
    
    public MainTab(Gui gui) {
        // Configure Tab
        setLayout(new BorderLayout());
        this.gui = gui;

        // Fetch Database
        database = new Database(gui.user);
        
        // Create and Add Panel containing database tree
        TreePanel databasePanel = new TreePanel(this);
        // Set tree panel width to 1/3 of frame
        int width = Gui.MAIN_FRAME_X_SIZE / 3;
        databasePanel.setPreferredSize(new Dimension(width, this.getHeight()));
        add(databasePanel, BorderLayout.WEST);

        // Create and Add Panel to edit selected entity
        editPanel = new FormPanel(gui, this);
        add(editPanel, BorderLayout.CENTER);
    }
    
    public void refreshDatabaseTree() {
        database.refresh(gui.user);
        removeAll();

        // Create and Add Panel containing database tree
        TreePanel databasePanel = new TreePanel(this);
        // Set tree panel width to 1/3 of frame
        int width = Gui.MAIN_FRAME_X_SIZE / 3;
        databasePanel.setPreferredSize(new Dimension(width, this.getHeight()));
        add(databasePanel, BorderLayout.WEST);

        // Create and Add Panel to edit selected entity
        editPanel = new FormPanel(gui, this);
        add(editPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
