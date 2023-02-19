
package icarius.gui.items;

import java.util.Arrays;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;


public class TempCampus {
    private String name;
    private int ID;
    private JTree tree;
    private DefaultMutableTreeNode root;

    public TempCampus(String Name, int id){
        super();
        name = Name;
        ID=id;
        root = new DefaultMutableTreeNode(name);
        tree = new JTree(root);
    }
    public String getName(){
        return name;
    }
    public int getID(){
        return ID;
    }

    public JTree getTree(){
        return tree;
    }

    public DefaultMutableTreeNode getRoot(){
        return root;
    }
}
