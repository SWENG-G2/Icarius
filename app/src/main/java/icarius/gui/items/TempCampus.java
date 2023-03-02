
package icarius.gui.items;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


public class TempCampus {
    private String name;
    private JTree tree;
    private DefaultMutableTreeNode root;

    public TempCampus(String Name){
        super();
        name = Name;
        root = new DefaultMutableTreeNode(name);
        tree = new JTree(root);
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
        model.insertNodeInto(new DefaultMutableTreeNode("+[Add Bird]"), root, 0);
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        name = newName;
    }


    public JTree getTree(){
        return tree;
    }

    public DefaultMutableTreeNode getRoot(){
        return root;
    }

}
