package icarius.gui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import icarius.App;
import icarius.entities.Database;
import icarius.gui.Gui;
import icarius.gui.panels.TreePanel;
import lombok.Getter;
import icarius.gui.panels.FormPanel;

public class MainTab extends JPanel {
    private TreePanel dbTreePanel;
    private @Getter FormPanel formPanel;

    public MainTab() {
        // Configure Tab
        setLayout(new BorderLayout());
        
        // Create and Add Panel containing database tree
        add(getTreePanel(), BorderLayout.WEST);

        // Create and Add Panel to edit selected entity
        formPanel = new FormPanel();
        add(formPanel, BorderLayout.CENTER);
    }

    public TreePanel getTreePanel() {
        // Fetch Database
        App.db = new Database(App.userClient);
        dbTreePanel = new TreePanel();

        // Set tree panel width to 1/3 of frame
        int width = Gui.MAIN_FRAME_X_SIZE / 3;
        dbTreePanel.setPreferredSize(new Dimension(width, this.getHeight()));
        return dbTreePanel;
    }
    
    public void refreshDatabaseTree() {
        // Save current status
        TreePath selectionPath = dbTreePanel.getTree().getSelectionPath();
        JTree oldTree = dbTreePanel.getTree();

        // refresh tree
        remove(dbTreePanel);
        getTreePanel();
        add(dbTreePanel, BorderLayout.WEST);


        // Expand tree to previous state
        JTree newTree = dbTreePanel.getTree();
        dbTreePanel.getTree().setSelectionPath(selectionPath);
        expandTree(oldTree, newTree);

        // Create and Add Panel to edit selected entity
        //add(new FormPanel(), BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void expandTree(JTree oldTree, JTree newTree) {
        // compare trees and open new tree according to old
        // make sure to take note of:
        // adding/removing birds
        // adding a campus
        // doing so with multiple campuses open
    }
}
