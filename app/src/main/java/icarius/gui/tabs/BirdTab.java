package icarius.gui.tabs;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import icarius.gui.items.TempCampus;

//import ch.qos.logback.classic.db.names.ColumnName;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.Dimension;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import java.awt.event.*;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

public class BirdTab extends Tab{
    private JTextField nameField;
    private JTextField campusField;
    public JButton addBirdButton;
    private JLabel response;
    private JScrollPane scrollPane;
    private JPanel treeView;
    private GridBagConstraints cT;
    private JFrame popUp;
    private DefaultTableModel tableModel;
    private JTable table;

    public BirdTab(){
        super();
        this.tabName="Bird";

        //adding labels which won't need to change later
        c.weightx = 0.2;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(new JLabel("Bird to Create:"), c);
        
                 
        c.weightx = 0.2;
        c.gridx = 2;
        c.gridy = 2;
        panel.add(new JLabel("Campus ID:"), c);
        
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 5;
        panel.add(new JLabel("Response:"), c);

               
        
        //adding any buttons, labels, or text fields which need variables for later

        response = new JLabel("");
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.8;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 3;
        panel.add(response, c);

        nameField = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 1;
        panel.add(nameField, c);
        
        campusField = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 2;
        panel.add(campusField, c);
        
        addBirdButton = new JButton("Add Bird");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 3;
        panel.add(addBirdButton, c);
        
                
        
        //TODO - Harry - add the rest of the buttons from the odysseus version of this and get them working
                
        treeView = new JPanel(new GridBagLayout());    
        cT = new GridBagConstraints();
        scrollPane = new JScrollPane(treeView);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.9;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 4;
        panel.add(scrollPane, c);

        cT.fill = GridBagConstraints.HORIZONTAL;
        cT.weightx = 0.5;
        cT.gridx = 0;
        cT.gridwidth = 1;
        cT.gridheight = 1;

        scrollPane.setPreferredSize(new DimensionUIResource(75, 200));
                
        popUp=new JFrame();

        tableModel = new DefaultTableModel(){
            @Override
            //Stops the user editing the table by pressing on it
            public boolean isCellEditable(int row, int column){
                //all cells return false
                return false;
            }
        };
        table = new JTable(tableModel);
        tableModel.addColumn("Item");
        tableModel.addColumn("Value");
        table.setShowGrid(true);

        for(int i=0; i<4; i++){
            String[] blankRow = {"",""};
            tableModel.addRow(blankRow);
        }
        popUp.add(table);
                               
    }

    public void updateBirdTrees(TempCampus[] campuses){
        Component[] components = treeView.getComponents();
        for (Component c : components){
            treeView.remove(c);
        }
        int i = 0;
        for (TempCampus c : campuses){
            cT.weightx = 0.5;
            cT.gridx = 0;
            cT.gridy = i;
            cT.gridwidth = 3;
            JTree tree=c.getTree();
            treeView.add(tree, cT);
            i=i+1;
            
            MouseListener mL = new MouseAdapter() {
                public void mousePressed(MouseEvent e){
                    int selRow = tree.getRowForLocation(e.getX(), e.getY());
                    TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                    tree.setSelectionPath(selPath);
                    if (selRow > -1){    
                        if (selRow == 1){
                            popUp.setVisible(false);
                            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selPath.getLastPathComponent();
                            String nodeName = selectedNode.toString();
                            popUp.setTitle(nodeName);
                            popUp.pack();
                            popUp.setVisible(true);
                            //TODO - Harry - close popup if the campus the bird is stored in gets removed
                        }
                    }
                }
            };
            
            tree.addMouseListener(mL);
            
        }
        treeView.repaint();
        panel.repaint();
        panel.revalidate();
    }

    public void setResponse(String text){
        response.setText(text);
    }

    public String nameFieldText(){
        return nameField.getText();
    }

    public String campusFieldText(){
        return campusField.getText();
    }

}
