
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
