package icarius.gui.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTree;
import icarius.App;
import icarius.entities.Database;
import icarius.gui.Gui;
import icarius.gui.panels.TreePanel;
import lombok.Getter;
import icarius.gui.panels.FormPanel;

public class MainTab extends JPanel {
    private TreePanel dbTreePanel;
    private @Getter FormPanel formPanel;

    /**
     * Tab accessable by all users of the GUI.
     * Birds and campuses are displayed in a JTree on the left of the tab.
     * Right of tab contains add, edit, and detail fields for selected bird or campus.
     * 
     */
    public MainTab() {
        // Configure Tab
        setLayout(new BorderLayout());
        
        // Create and Add Panel containing database tree
        add(getTreePanel(), BorderLayout.WEST);

        // Create and Add Panel to edit selected entity
        formPanel = new FormPanel();
        add(formPanel, BorderLayout.CENTER);
    }


    /**
     * creates tree panel using data from database, sets preferred size of it, and returns it.
     * @return TreePanel dbTreePanel
     */
    public TreePanel getTreePanel() {
        // Fetch Database
        App.db = new Database(App.userClient);
        dbTreePanel = new TreePanel();

        // Set tree panel width to 1/3 of frame
        int width = Gui.MAIN_FRAME_X_SIZE / 3;
        dbTreePanel.setPreferredSize(new Dimension(width, this.getHeight()));
        return dbTreePanel;
    }
    

    /**
     * refreshes JTree and updates details form
     * @param campusOrBird
     */
    public void refreshDatabaseTree(Object campusOrBird) {
        // Save current status
        JTree oldTree = dbTreePanel.getTree();

        // refresh tree
        remove(dbTreePanel);
        getTreePanel();
        add(dbTreePanel, BorderLayout.WEST);


        // Expand tree to previous state
        JTree newTree = dbTreePanel.getTree();
        expandTree(oldTree, newTree);


        // Update form panel
        if (campusOrBird != null) {
            formPanel.setDetailsPage(campusOrBird);
        }
        add(formPanel, BorderLayout.CENTER);


        revalidate();
        repaint();
    }

    /**
     * expands new tree to its previous state
     * @param oldTree
     * @param newTree
     */
    private void expandTree(JTree oldTree, JTree newTree) {
        // Get Tree Roots
        Object oldTreeRoot = oldTree.getModel().getRoot();
        Object newTreeRoot = newTree.getModel().getRoot();

        // Row number of campus in each tree
        int newRowNum = 0;
        int oldRowNum = 0;
        
        // For each campus
        for(int campusNum = 0; campusNum < oldTree.getModel().getChildCount(oldTreeRoot); campusNum++){
            if( oldTree.isExpanded(oldRowNum) ){
                // If campus expanded in old tree, expand in new tree
                newTree.expandRow(newRowNum);

                // Get Row number of next campus in old tree
                Object oldTreeCampus = newTree.getModel().getChild(oldTreeRoot, campusNum);
                int oldTreeCampusChildNum = newTree.getModel().getChildCount(oldTreeCampus);
                oldRowNum += oldTreeCampusChildNum + 1;

                // Get Row number of next campus in new tree
                Object newTreeCampus = newTree.getModel().getChild(newTreeRoot, campusNum);
                int newTreeCampusChildNum = newTree.getModel().getChildCount(newTreeCampus);
                newRowNum += newTreeCampusChildNum + 1;
            } else {
                // If campus not expanded, go to next tree
                oldRowNum++;
                newRowNum++;
            }
        }

    }
}
