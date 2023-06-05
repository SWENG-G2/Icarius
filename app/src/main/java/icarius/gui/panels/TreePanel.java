package icarius.gui.panels;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import icarius.App;
import icarius.entities.Bird;
import icarius.entities.Campus;
import icarius.gui.tabs.MainTab;
import lombok.Getter;

public class TreePanel extends JScrollPane {
    private @Getter JTree tree;
    public static final String ADD_BIRD_TEXT = "+[Add Bird]";
    public static final String ADD_CAMPUS_TEXT = "+[Add Campus]";
    
    public TreePanel() {
        // Configure JSrollPane
        setLayout(new ScrollPaneLayout.UIResource());
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setViewport(createViewport());
        setVerticalScrollBar(createVerticalScrollBar());
        setBorder(null);

        // Create and Add Tree Panel to DatabasePanel JScrollPane
        tree = new JTree(createTreeModel());
        tree.setRootVisible(false);
        setViewportView(tree);

        // Add SelectionListener to tree
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(treeSelectionListener);
    }

    public DefaultMutableTreeNode createTreeModel() {
        // Create the Tree using Database
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("FaunaFinder");

        if (App.db != null) {
            for (Campus campus : App.db.getDatabase()) {
                // If User has permissions for this campus or is Admin, add to tree
                if (App.userClient.getAdmin() || App.userClient.getCampusPermissions().contains(campus.getId())) {
                    root.add(constructCampusNode(campus));
                }
            }
        }

        // Add "Add Campus" button as last node if admin
        if (App.userClient.getAdmin()) {
            DefaultMutableTreeNode addCampusSelection = new DefaultMutableTreeNode(ADD_CAMPUS_TEXT);
            root.add(addCampusSelection);
        }
        return root;
    }

    private DefaultMutableTreeNode constructCampusNode(Campus campus) {
        // Create campus node
        DefaultMutableTreeNode campusNode = new DefaultMutableTreeNode(campus.getName());

        for (Bird bird : campus.getBirds()) {
            // Add each Bird as a child node to Campus node
            DefaultMutableTreeNode birdNode = new DefaultMutableTreeNode(bird.getName());
            campusNode.add(birdNode);
        }

        // Add "Add Bird to Campus" button as last child node to each campus node
        DefaultMutableTreeNode addBirdSelection = new DefaultMutableTreeNode(ADD_BIRD_TEXT);
        campusNode.add(addBirdSelection);

        return campusNode;
    }

    // Actions
    private TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
        public void valueChanged(TreeSelectionEvent e) {
            // Get path to selected object
            TreePath path = tree.getSelectionPath();
            if (path == null) return;

            // Get Form JPanel
            FormPanel formPanel = ((MainTab) getParent()).getFormPanel();
            
            String selectedLocationText, selectedAnimalText;
            switch (path.getPathCount()) {
                case 2:
                    // If Campus selected
                    selectedLocationText = path.getPathComponent(1).toString();
                    if (selectedLocationText.equals(ADD_CAMPUS_TEXT)) {
                        // Add Campus Selection
                        formPanel.setAddCampus();
                    } else {
                        // Edit Campus Selection
                        Campus campus = App.db.getCampus(selectedLocationText);
                        formPanel.setDetailsPage(campus);
                    }
                    break;

                case 3:
                    // If Bird selected
                    selectedLocationText = path.getPathComponent(1).toString();
                    selectedAnimalText = path.getPathComponent(2).toString();
                    if (selectedAnimalText.equals(ADD_BIRD_TEXT)) {
                        // Add Bird Selection
                        Campus campus = App.db.getCampus(selectedLocationText);
                        formPanel.setAddBird(campus);
                        getParent().getComponent(1);
                    } else {
                        // Edit Bird Selection
                        Campus campus = App.db.getCampus(selectedLocationText);
                        Bird bird = campus.getBird(selectedAnimalText);
                        bird.read(null);
                        formPanel.setEditPage(bird);
                    }
                    break;

                default:
                    // If no selection
                    return;
            }
        }
    };
}
