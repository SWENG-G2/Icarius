package icarius.gui.tabs;

import icarius.gui.items.TempCampus;

//import ch.qos.logback.classic.db.names.ColumnName;

import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import java.awt.event.*;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;

public class MainTab extends Tab{
    private JPanel subPanel1;
    private JPanel subPanel2;

    public JButton addBirdButton;
    private JLabel response;
    private JScrollPane scrollPane;

    private JTextField campusField;
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

    public JButton addCampusButton;
    public JButton removeCampusButton;

    private String[] removeCampusText = {};
    private JComboBox removeCampusBox;

    private JLabel campusText;
    private JLabel nameText;
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
    


    private DefaultMutableTreeNode addCampusRoot;
    private JTree addCampusTree;

    private String campus = "";


    private String[] labelNames = {"Campus Name:","Bird Name:", "Hero Image:", "List Image:",
    "Sound:", "About:", "Video:", "Location:", "Location Image:", "Diet:", "Diet Image:"};
  

    public MainTab(){
        super();
        this.tabName="Main";

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
        campusField = new JTextField("");
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
        addCampusButton = new JButton("Add campus");


        JComponent[] tempComponents={nameField, heroImageUp, listImageUp, soundUp, aboutField,
            videoUp, locationField, locationImageUp, dietField, dietImageUp, addBirdButton};

        components=Arrays.copyOf(tempComponents, tempComponents.length);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 4;
        c.gridy = 0;
        panel.add(campusField, c);
        campusField.setVisible(true);
        

        i=0;
        for (JComponent component : components){
            i++;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.3;
            c.gridx = 4;
            c.gridy = i;
            panel.add(component, c);
            component.setVisible(false);
        }

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 4;
        c.gridy = i;
        panel.add(addCampusButton, c);
        addCampusButton.setVisible(true);


        campusText = new JLabel("");
        nameText = new JLabel("");
        heroImageLink = new JLabel("");
        listImageLink = new JLabel("");
        soundLink = new JLabel("");
        aboutText = new JLabel("");
        videoLink = new JLabel("");
        locationText = new JLabel("");
        locationImageLink = new JLabel("");
        dietText = new JLabel("");
        dietImageLink = new JLabel("");

        JLabel[] tempLabels = {nameText, heroImageLink, listImageLink, soundLink,
                            aboutText, videoLink, locationText, locationImageLink, dietText, dietImageLink};
        
        birdLabels = Arrays.copyOf(tempLabels, tempLabels.length);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 4;
        c.gridy = 0;
        panel.add(campusText, c);
        campusText.setVisible(false);

        i=1;
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

        removeCampusBox = new JComboBox<>(removeCampusText);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.8;
        c.gridx = 0;
        c.gridy = 15;
        c.gridwidth = 1;
        panel.add(removeCampusBox,c);

        removeCampusButton = new JButton("Remove Campus");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.8;
        c.gridx = 0;
        c.gridy = 16;
        c.gridwidth = 1;
        panel.add(removeCampusButton,c);
        removeCampusButton.setVisible(false);
        
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

        cT.weightx = 0.5;
        cT.gridx = 0;
        cT.gridy = 0;
        cT.gridwidth = 3;
        addCampusRoot = new DefaultMutableTreeNode("+[Add Campus]");
        addCampusTree = new JTree(addCampusRoot);
        treeView.add(addCampusTree, cT);

        MouseListener mL = new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                addCampusPressed(true);
            }
        };
        addCampusTree.addMouseListener(mL);

        scrollPane.setPreferredSize(new DimensionUIResource(75, 200));                     
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
                        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)selPath.getPath()[0];
                        campus=rootNode.toString(); //TODO - Harry - remember what this was for
                        campusText.setText(rootNode.toString());
                        addCampusPressed(false);
                        if (selRow >= 1){
                            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selPath.getLastPathComponent();
                            String nodeName = selectedNode.toString();
        
                            if(nodeName=="+[Add Bird]"){
                                addBirdPressed(true);


                            } else{
                                campus=rootNode.toString(); //TODO - Harry - remember what this was for
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

        cT.weightx = 0.5;
        cT.gridx = 0;
        cT.gridy = i;
        cT.gridwidth = 3;
        treeView.add(addCampusTree, cT);

        

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

    private void addCampusPressed(boolean bool){
        if (bool==true){
            for(JComponent comp : components){
                comp.setVisible(false);
            }
            for(JLabel label : birdLabels){
                label.setVisible(false);
            }
            addCampusButton.setVisible(true);
            campusField.setVisible(true);
            campusText.setVisible(false);
        }else{
            addCampusButton.setVisible(false);
            campusField.setVisible(false);
            campusText.setVisible(true);
            campusField.setText("");
        }
    }

    public String getSelectedCampus(){
        return campus;
    }

    public String getCampusFieldValue(){
        return campusField.getText();
    }
    
    public void updateCampusRemover(TempCampus[] campuses){
        String[] campusesText={};
        for (TempCampus camp : campuses){
            campusesText=Arrays.copyOf(campusesText, campusesText.length+1);
            campusesText[campusesText.length-1]=camp.getName();
        }
        removeCampusText = Arrays.copyOf(campusesText, campusesText.length);
        DefaultComboBoxModel model = (DefaultComboBoxModel) removeCampusBox.getModel();
        model.removeAllElements();

        for (String text : removeCampusText){
            model.addElement(text);
        }

        if(removeCampusText.length>0){
            removeCampusButton.setVisible(true);
        }else{
            removeCampusButton.setVisible(false);
        }
    }

    public String campusToRemove(){
        return String.valueOf(removeCampusBox.getSelectedItem());
    }
}
