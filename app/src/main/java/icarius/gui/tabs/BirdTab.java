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

    private JButton heroImageUp;
    private JButton listImageUp;
    private JButton soundUp;
    private JTextField aboutField;
    private JButton videoUp;
    private JTextField locationField;
    private JButton locationImageUp;
    private JTextField dietField;
    private JButton dietImageUp;
    
    private JPanel treeView;
    private GridBagConstraints cT;
    
    private JFrame popUpFrame;
    private JPanel popUpPanel;
    private DefaultTableModel tableModel;
    private JTable table;

    private String[] rowNames={"Hero Image", "List Image", "Sound", "About", 
                                "Video", "Location", "Location Image", "Diet", "Diet Image"};

    public BirdTab(){
        super();
        this.tabName="Bird";

        //adding labels which won't need to change later
        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 1;
        panel.add(new JLabel("Bird to Create:"), c);
        
                 
        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 2;
        panel.add(new JLabel("Campus ID:"), c);

        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 3;
        panel.add(new JLabel("Hero Image:"), c);

        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 4;
        panel.add(new JLabel("List Image:"), c);

        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 5;
        panel.add(new JLabel("Sound:"), c);

        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 6;
        panel.add(new JLabel("About:"), c);
        
        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 7;
        panel.add(new JLabel("Video:"), c);

        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 8;
        panel.add(new JLabel("Location:"), c);

        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 9;
        panel.add(new JLabel("Location Image:"), c);

        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 10;
        panel.add(new JLabel("Diet:"), c);

        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 11;
        panel.add(new JLabel("Diet Image:"), c);
        
        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 13;
        panel.add(new JLabel("Response:"), c);

               
        
        //adding any buttons, labels, or text fields which need variables for later

        response = new JLabel("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.8;
        c.gridx = 4;
        c.gridy = 13;
        c.gridwidth = 3;
        panel.add(response, c);

        nameField = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 4;
        c.gridy = 1;
        panel.add(nameField, c);
        
        campusField = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 4;
        c.gridy = 2;
        panel.add(campusField, c);
        
        heroImageUp = new JButton("Upload Hero Image");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 4;
        c.gridy = 3;
        panel.add(heroImageUp, c);

        listImageUp = new JButton("Upload List Image");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 4;
        c.gridy = 4;
        panel.add(listImageUp, c);

        soundUp = new JButton("Upload Sound");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 4;
        c.gridy = 5;
        panel.add(soundUp, c);

        aboutField = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 4;
        c.gridy = 6;
        panel.add(aboutField, c);

        videoUp = new JButton("Upload Video");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 4;
        c.gridy = 7;
        panel.add(videoUp, c);

        locationField = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 4;
        c.gridy = 8;
        panel.add(locationField, c);

        locationImageUp = new JButton("Upload Location Image");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 4;
        c.gridy = 9;
        panel.add(locationImageUp, c);

        dietField = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 4;
        c.gridy = 10;
        panel.add(dietField, c);

        dietImageUp = new JButton("Upload Diet Image");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 4;
        c.gridy = 11;
        panel.add(dietImageUp, c);

        addBirdButton = new JButton("Add Bird");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 3;
        c.gridy = 12;
        panel.add(addBirdButton, c);
        
        //TODO - Harry - add the rest of the buttons from the odysseus version of this and get them working
                
        treeView = new JPanel(new GridBagLayout());    
        cT = new GridBagConstraints();
        scrollPane = new JScrollPane(treeView);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 13;
        panel.add(scrollPane, c);

        cT.fill = GridBagConstraints.HORIZONTAL;
        cT.weightx = 0.5;
        cT.gridx = 0;
        cT.gridwidth = 1;
        cT.gridheight = 1;

        scrollPane.setPreferredSize(new DimensionUIResource(75, 200));
                
        popUpFrame = new JFrame();
        popUpPanel = new JPanel();
        //popUpPanel.setLayout(new GridBagLayout());
        popUpFrame.add(popUpPanel);

        tableModel = new DefaultTableModel(){
            @Override
            //Stops the user editing the table by pressing on it
            public boolean isCellEditable(int row, int column){
                //all cells return false
                return false;
            }
        };
        table = new JTable(tableModel);
        
        table.setShowGrid(true);
        tableModel.addColumn("Item");
        tableModel.addColumn("Description");
    
        String[] firstRow = {"Item", "Description"};
        tableModel.addRow(firstRow);

        for(String s : rowNames){
            String[] blankRow = {s,""};
            tableModel.addRow(blankRow);
        }

        c.weightx=0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        
        popUpPanel.add(table);
        popUpPanel.repaint();                       
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
                            popUpFrame.setVisible(false);
                            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selPath.getLastPathComponent();
                            String nodeName = selectedNode.toString();
                            popUpFrame.setTitle(nodeName);
                            popUpFrame.pack();
                            popUpPanel.repaint();
                            popUpFrame.setVisible(true);
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
