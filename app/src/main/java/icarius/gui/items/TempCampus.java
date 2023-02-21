
package icarius.gui.items;

import java.util.Arrays;

import java.awt.event.*;


import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import org.w3c.dom.events.MouseEvent;


public class TempCampus {
    private String name;
    private int ID;
    private JTree tree;
    private DefaultMutableTreeNode root;
    private MouseListener mL;

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
