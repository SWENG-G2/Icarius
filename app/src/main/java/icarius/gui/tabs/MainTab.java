package icarius.gui.tabs;

import icarius.gui.items.TempCampus;


import java.util.Arrays;
import java.util.Enumeration;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import javax.swing.plaf.DimensionUIResource;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import java.awt.event.*;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;


public class MainTab extends Tab{

    private JScrollPane scrollPane;

    public JButton createCampusButton;
    public JButton removeCampusButton;
    public JButton addBirdButton;

    private JComboBox campusComboBox;

    public JButton saveCampusButton;
    public JButton saveBirdButton;

    private JPanel treeView;
    private GridBagConstraints cT;
    
    private SubTabMain subTab;

    private DefaultMutableTreeNode addCampusRoot;
    private JTree addCampusTree;

    private String campus = "";


    public MainTab(){
        super();
        this.tabName="Main";

        subTab = new SubTabMain();

        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 0;
        c.gridheight = 15;
        c.gridwidth = 3;
        panel.add(subTab.returnPanel(), c);

        subTab.returnPanel().setPreferredSize(new DimensionUIResource(350, 300));  
        //adding labels which won't need to change later

        c.gridheight=1;
        c.gridwidth=1;

        String[] comboBoxText={};

        campusComboBox = new JComboBox<>(comboBoxText);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.8;
        c.gridx = 0;
        c.gridy = 15;
        c.gridwidth = 3;
        panel.add(campusComboBox,c);

        removeCampusButton = new JButton("Remove Campus");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.8;
        c.gridx = 0;
        c.gridy = 16;
        c.gridwidth = 1;
        panel.add(removeCampusButton,c);
        removeCampusButton.setVisible(false);
                        
        createCampusButton = subTab.createCampusButton;
        addBirdButton = subTab.addBirdButton;
        saveCampusButton = subTab.saveCampusButton;
        saveBirdButton = subTab.saveBirdButton;

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
        cT.gridy = 0;
        cT.gridwidth = 3;
        addCampusRoot = new DefaultMutableTreeNode("+[Add Campus]");
        addCampusTree = new JTree(addCampusRoot);
        treeView.add(addCampusTree, cT);

        MouseListener mL = new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                subTab.addCampusNodePressed(true);
                subTab.visibleEditBirdButton(false);
                subTab.visibleEditCampusButton(false);
                subTab.showStaticLabels(false);
            }
        };
        addCampusTree.addMouseListener(mL);

        scrollPane.setPreferredSize(new DimensionUIResource(20, 300));  
        
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
                        campus=rootNode.toString();
                        subTab.setCampusText(rootNode.toString());
                        subTab.addCampusNodePressed(false);
                        if (selRow >= 1){
                            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selPath.getLastPathComponent();
                            String nodeName = selectedNode.toString();
                            subTab.editBirdSelected(false);
                            subTab.showStaticLabels(true);
                            if(nodeName=="+[Add Bird]"){
                                subTab.addBirdNodePressed(true);
                                subTab.visibleEditBirdButton(false);
                                subTab.visibleEditCampusButton(false);

                            } else{
                                campus=rootNode.toString();
                                //TODO - get the relevent bird data, put it into this array (should only hold 10 strings)
                                String[] birdInfo = {nodeName};
                                subTab.setBirdLabels(birdInfo);
                                subTab.addBirdNodePressed(false);
                                subTab.visibleEditBirdButton(true);
                                subTab.visibleEditCampusButton(false);
                            }
                        } else{
                            subTab.showStaticLabels(false);
                            subTab.campusNodePressed();
                            subTab.visibleEditBirdButton(false);
                            subTab.visibleEditCampusButton(true);
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
        subTab.setResponse(text);
    }

    public String nameFieldText(){
        return subTab.nameFieldText();
    }

    





    public String getSelectedCampus(){
        return campus;
    }

    public String getCampusFieldValue(){
        return subTab.getCampusFieldValue();
    }
    
    public void updateCampusRemover(TempCampus[] campuses){
        String[] campusesText={};
        for (TempCampus camp : campuses){
            campusesText=Arrays.copyOf(campusesText, campusesText.length+1);
            campusesText[campusesText.length-1]=camp.getName();
        }
        DefaultComboBoxModel model = (DefaultComboBoxModel) campusComboBox.getModel();
        model.removeAllElements();

        for (String text : campusesText){
            model.addElement(text);
        }

        if(campusesText.length>0){
            removeCampusButton.setVisible(true);
        }else{
            removeCampusButton.setVisible(false);
        }
    }

    public String campusToRemove(){
        return String.valueOf(campusComboBox.getSelectedItem());
    }



    public JButton[] uploadButtons(){
        return subTab.uploadButtons();
    }



    public void saveCampusPressed(TempCampus[] campuses){
        String newName = subTab.getCampusFieldValue();
        String oldName = subTab.getSelectedCampus();
        TempCampus campus = null;
        for (TempCampus camp : campuses){
            if (oldName.equals(camp.getName())){
                campus=camp;
            }
        }
        if (campus != null){
            campus.setName(newName);
            updateCampusRemover(campuses);
            campus.getRoot().setUserObject(newName);
            treeView.repaint();
            subTab.setResponse("Campus "+oldName+ " has been changed to "+ newName);
            subTab.editCampusClosed(newName);
        }
    }

    public void saveBirdPressed(TempCampus[] campuses){
        String newName = subTab.getNameFieldText();
        String oldName = subTab.getSelectedBird();
        //TODO - when using this with the actual server oldName can probably just be pulled from there
        TempCampus campus = null;
        for (TempCampus camp : campuses){
            if (camp.getName()==subTab.getCampusText()){
                campus=camp;
            }
        }
        if (campus != null){
            TreePath path = getNamedNode(campus.getRoot(), oldName);
            if (path != null){
                JTree tree = campus.getTree();
                tree.setSelectionPath(path);
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)path.getLastPathComponent();
                selectedNode.setUserObject(newName);
                treeView.repaint();
                subTab.setResponse("Bird: "+oldName+" has been changed to "+ newName);
                subTab.editBirdClosed(newName);
                //TODO - Harry - figure out how to resize the node so that the new name fits properly
            }
        }
    }
    private TreePath getNamedNode(DefaultMutableTreeNode root, String name){
        Enumeration<TreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements()){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if (node.toString().equalsIgnoreCase(name)){
                return new TreePath(node.getPath());
            }
        }
        return null;
    }
}
