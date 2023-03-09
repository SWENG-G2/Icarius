
package icarius.gui.items;

import java.util.Arrays;

import java.awt.event.*;


import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import org.w3c.dom.events.MouseEvent;


public class TempCampus {
    private String name;
    private int ID;
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


    public JTree getTree(){
        return tree;
    }

    public DefaultMutableTreeNode getRoot(){
        return root;
    }

}
