package icarius.gui.panels;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import icarius.entities.Bird;
import icarius.entities.Campus;
import icarius.entities.Database;
import icarius.gui.tabs.MainTab;

public class DatabasePanel extends JScrollPane {
    private MainTab mainTab;
    private JTree tree;
    public static final String ADD_BIRD_TEXT = "+[Add Bird]";
    public static final String ADD_CAMPUS_TEXT = "+[Add Campus]";
    
    public DatabasePanel(MainTab mainTab) {
        this.mainTab = mainTab;

        // Configure JSrollPane
        setLayout(new ScrollPaneLayout.UIResource());
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setViewport(createViewport());
        setVerticalScrollBar(createVerticalScrollBar());
        setBorder(null);

        // Create and Add Tree Panel to DatabasePanel JScrollPane
        tree = new JTree(createTreeModel(mainTab.database));
        tree.setRootVisible(false);
        setViewportView(tree);

        // Add SelectionListener to tree
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(treeSelectionListener);
    }

    public DefaultMutableTreeNode createTreeModel(Database database) {
        // Create the Tree using Database
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        if (database != null) {
            for (Campus campus : database.getDatabase()) {
                // Add each Campus node to tree
                DefaultMutableTreeNode campusNode = new DefaultMutableTreeNode(campus.getName());

                for (Bird bird : campus.getBirds()) {
                    // Add each Bird as a child node to Campus node
                    DefaultMutableTreeNode birdNode = new DefaultMutableTreeNode(bird.getName());
                    campusNode.add(birdNode);
                }

                // Add "Add Bird to Campus" button as last child node to each campus node
                DefaultMutableTreeNode addBirdSelection = new DefaultMutableTreeNode(ADD_BIRD_TEXT);
                campusNode.add(addBirdSelection);
                root.add(campusNode);
            }
        }

        // Add "Add Campus" button as last node
        DefaultMutableTreeNode addCampusSelection = new DefaultMutableTreeNode(ADD_CAMPUS_TEXT);
        root.add(addCampusSelection);
        return root;
    }

    // Actions
    private TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
        public void valueChanged(TreeSelectionEvent e) {
            // Get path to selected object
            TreePath path = tree.getSelectionPath();
            if (path == null) return;
            
            String selectedLocationText, selectedAnimalText;
            switch (path.getPathCount()) {
                case 2:
                    // If Campus selected
                    selectedLocationText = path.getPathComponent(1).toString();
                    if (selectedLocationText.equals(ADD_CAMPUS_TEXT)) {
                        // Add Campus Selection
                        mainTab.editPanel.setAddCampus();
                    } else {
                        // Edit Campus Selection
                        Campus campus = mainTab.database.getCampus(selectedLocationText);
                        mainTab.editPanel.setDetailsPage(campus);
                    }
                    break;

                case 3:
                    // If Bird selected
                    selectedLocationText = path.getPathComponent(1).toString();
                    selectedAnimalText = path.getPathComponent(2).toString();
                    if (selectedAnimalText.equals(ADD_BIRD_TEXT)) {
                        // Add Bird Selection
                        mainTab.editPanel.setAddBird();
                    } else {
                        // Edit Bird Selection
                        Campus campus = mainTab.database.getCampus(selectedLocationText);
                        Bird bird = campus.getBird(selectedAnimalText);
                        mainTab.editPanel.setDetailsPage(bird);
                    }
                    break;

                default:
                    // If no selection
                    return;
            }
        }
    };
}
