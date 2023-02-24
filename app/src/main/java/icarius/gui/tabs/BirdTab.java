package icarius.gui.tabs;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import icarius.gui.items.TempCampus;

//import ch.qos.logback.classic.db.names.ColumnName;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.Dimension;



import javax.swing.JButton;
import javax.swing.JComponent;
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
    private JPanel subPanel1;
    private JPanel subPanel2;

    public JButton addBirdButton;
    private JLabel response;
    private JScrollPane scrollPane;

    private JTextField nameField;
    private JButton heroImageUp;
    private JButton listImageUp;
    private JButton soundUp;
    private JTextField aboutField;
    private JButton videoUp;
    private JTextField locationField;
    private JButton locationImageUp;
    private JTextField dietField;
    private JButton dietImageUp;
    private JComponent[] components = {};


    private JLabel nameText;
    private JLabel campusText;
    private JLabel heroImageLink;
    private JLabel listImageLink;
    private JLabel soundLink;
    private JLabel aboutText;
    private JLabel videoLink;
    private JLabel locationText;
    private JLabel locationImageLink;
    private JLabel dietText;
    private JLabel dietImageLink;
    private JLabel[] birdLabels = {};

    private JPanel treeView;
    private GridBagConstraints cT;
    
    private JFrame popUpFrame;
    private JPanel popUpPanel;
    private DefaultTableModel tableModel;
    private JTable table;

    private String campus = "";

    
    private String[] rowNames={"Hero Image", "List Image", "Sound", "About", 
                                "Video", "Location", "Location Image", "Diet", "Diet Image"};

    private String[] labelNames = {"Bird Name:", "Hero Image:", "List Image:",
    "Sound:", "About:", "Video:", "Location:", "Location Image:", "Diet:", "Diet Image:"};
  
    public BirdTab(){
        super();
        this.tabName="Bird";

        subPanel1 = new JPanel();
        subPanel2 = new JPanel();
        subPanel1.setLayout(new GridBagLayout());
        subPanel2.setLayout(new GridBagLayout());



        //adding labels which won't need to change later
        

        int i = 0;
        for (String s: labelNames){
            c.weightx = 0.2;
            c.gridx = 3;
            c.gridy = i;
            panel.add(new JLabel(s), c);
            i++;
        }

        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 14;
        panel.add(new JLabel("Response:"), c);

        //Initialises all of the JComponents
        nameField = new JTextField("");
        heroImageUp = new JButton("Upload Hero Image");  //TODO - nio not io for thing to search for files
        listImageUp = new JButton("Upload List Image");
        soundUp = new JButton("Upload Sound");
        aboutField = new JTextField("");
        videoUp = new JButton("Upload Video");
        locationField = new JTextField("");
        locationImageUp = new JButton("Upload Location Image");
        dietField = new JTextField("");
        dietImageUp = new JButton("Upload Diet Image");
        addBirdButton = new JButton("Add Bird");


        JComponent[] tempComponents={nameField, heroImageUp, listImageUp, soundUp, aboutField,
            videoUp, locationField, locationImageUp, dietField, dietImageUp, addBirdButton};

        components=Arrays.copyOf(tempComponents, tempComponents.length);


        i=0;
        for (JComponent component : components){
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.3;
            c.gridx = 4;
            c.gridy = i;
            panel.add(component, c);
            component.setVisible(false);
            i++;
        }

        nameText = new JLabel("");
        campusText = new JLabel("");
        heroImageLink = new JLabel("");
        listImageLink = new JLabel("");
        soundLink = new JLabel("");
        aboutText = new JLabel("");
        videoLink = new JLabel("");
        locationText = new JLabel("");
        locationImageLink = new JLabel("");
        dietText = new JLabel("");
        dietImageLink = new JLabel("");

        JLabel[] tempLabels = {nameText, campusText, heroImageLink, listImageLink, soundLink,
                            aboutText, videoLink, locationText, locationImageLink, dietText, dietImageLink};
        
        birdLabels = Arrays.copyOf(tempLabels, tempLabels.length);

        i=0;
        for (JLabel label: birdLabels){
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.3;
            c.gridx = 4;
            c.gridy = i;
            panel.add(label, c);
            label.setVisible(true);
            i++;
        }


        response = new JLabel("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.8;
        c.gridx = 4;
        c.gridy = 14;
        c.gridwidth = 3;
        panel.add(response, c);


        

        
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
                        if (selRow >= 1){
                            popUpFrame.setVisible(false);
                            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selPath.getLastPathComponent();
                            String nodeName = selectedNode.toString();
        
                            DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)selPath.getPath()[0];
                            campus=rootNode.toString();
                            if(nodeName=="+[Add Bird]"){
                                addBirdPressed(true);
                            } else{
                                nameText.setText(nodeName);
                                addBirdPressed(false);
                            }
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

    

    private void addBirdPressed(boolean bool){
        if (bool==true){
            for(JComponent comp : components){
                comp.setVisible(true);
            }
            for(JLabel label : birdLabels){
                label.setVisible(false);
            }
        }else{
            for(JComponent comp : components){
                comp.setVisible(false);
            }
            for(JLabel label : birdLabels){
                label.setVisible(true);
            } 
        }
    }

    public String getCampus(){
        return campus;
    }

}
