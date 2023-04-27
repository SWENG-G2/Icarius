package icarius.gui.tabs;

import icarius.auth.User;
import icarius.entities.Bird;
import icarius.entities.Campus;
import icarius.http.PostRequest;
import okhttp3.Response;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import javax.swing.plaf.DimensionUIResource;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.TreePath;

import java.awt.event.*;
import java.io.File;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;


public class MainTab extends Tab{

    private JScrollPane scrollPane;

    public JButton createCampusButton;
    public JButton removeCampusButton;
    public JButton addBirdButton;
    public JButton deleteCampusButton;
    public JButton deleteBirdButton;

    public JTree[] trees = {};


    public JButton saveCampusButton;
    public JButton saveBirdButton;

    private JPanel treeView;
    private GridBagConstraints cT;
    
    private SubTabMain subTab;

    private DefaultMutableTreeNode addCampusRoot;
    private JTree addCampusTree;

    private String selectedCampus = "";
    private String selectedBird = "";

    private String HIFilePath = "";
    private String LiIFilePath = "";
    private String SFilePath="";
    private String VFilePath="";
    private String loIFilePath="";
    private String DIFilePath = "";
    private String[] filePaths = {HIFilePath,LiIFilePath,SFilePath,VFilePath,loIFilePath,DIFilePath};

    //filePaths stores the filePaths of the files selected from each of the upload buttons before they are uploaded
    //I've got it so that when anything else is selected these reset to being blank, to prevent any errors from occuring
    //(if you're refactoring you probably need to make sure that this happens when cancel is pressed in the bird edit menu too
    // I haven't done it because that in subTabMain, if I had more time I'd have moved that into mainTab so that this would work)


    public MainTab(){
        super();
        this.tabName="Main";

        subTab = new SubTabMain();
        setupUploadButtons();

        deleteCampusButton = subTab.deleteCampusButton;
        deleteBirdButton = subTab.deleteBirdButton;

        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 0;
        c.gridheight = 15;
        c.gridwidth = 3;
        
        panel.add(subTab.returnPanel(), c);

        subTab.returnPanel().setPreferredSize(new DimensionUIResource(340, 350));  
        //adding labels which won't need to change later

        c.gridheight=1;
        c.gridwidth=1;


                        
        createCampusButton = subTab.createCampusButton;
        addBirdButton = subTab.addBirdButton;
        saveCampusButton = subTab.saveCampusButton;
        saveBirdButton = subTab.saveBirdButton;

        //This is the panel which contains the JTrees
        treeView = new JPanel(new GridBagLayout());    
        cT = new GridBagConstraints();
        scrollPane = new JScrollPane(treeView);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 15;
        panel.add(scrollPane, c);

        c.weighty = 1;

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
                subTab.showCampusLabel(true);
                subTab.editCampusOpen(false);
                subTab.showWelcomeMessage(false);
                subTab.showCampusField(true);
                subTab.showCancelBird(false);
                subTab.showDeleteBird(false);
                selectedCampus = "";
                selectedBird = "";
                for (String path: filePaths){
                    path = "";
                }
                subTab.clearResponse();
            }
        };
        addCampusTree.addMouseListener(mL);

        scrollPane.setPreferredSize(new DimensionUIResource(20, 300));  
        
    }






    public void updateTree(List<Campus> campuses){
        Component[] components = treeView.getComponents();
        for (Component c : components){
            treeView.remove(c);
        }
        int i = 0;
        for (JTree tree : trees){
            cT.weightx = 0.5;
            cT.gridx = 0;
            cT.gridy = i;
            cT.gridwidth = 3;
            treeView.add(tree, cT);
            i=i+1;
            
            MouseListener mL = new MouseAdapter() {
                public void mousePressed(MouseEvent e){
                    int selRow = tree.getRowForLocation(e.getX(), e.getY());
                    TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                    tree.setSelectionPath(selPath);
                    if (selRow > -1){    
                        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)selPath.getPath()[0];
                        selectedCampus=rootNode.toString();
                        subTab.setCampusText(rootNode.toString());
                        subTab.addCampusNodePressed(false);
                        subTab.editCampusOpen(false);
                        subTab.showWelcomeMessage(false);
                        subTab.clearResponse();
                        subTab.showDeleteBird(false);
                        if (selRow >= 1){
                            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selPath.getLastPathComponent();
                            String nodeName = selectedNode.toString();
                            subTab.editBirdSelected(false);
                            subTab.showStaticLabels(true);
                            if(nodeName=="+[Add Bird]"){
                                selectedBird="";
                                subTab.editBirdSelected(false);
                                subTab.visibleEditBirdButton(false);
                                subTab.visibleEditCampusButton(false);
                                subTab.showSaveCampus(false);
                                subTab.showCancelBird(false);
                                subTab.addBirdNodePressed(true);
                                //TODO - Double check that this for loop works
                                for (String path: filePaths){
                                    path = "";
                                }
                            } else{
                                selectedCampus=rootNode.toString();
                                selectedBird=nodeName;
                                String[] birdInfo = getBirdInfo(nodeName, selectedCampus, campuses);
                                if (birdInfo != null){
                                    subTab.setBirdLabels(birdInfo);
                                    //getBirdInfo sometimes returns null when it shouldn't if the bird has been added or edited 
                                    //whilst the current session of icarius is running. If it's not caught in an if statement it
                                    //crashes the program, if it is caught the program works exactly as it should. 
                                    for (String path: filePaths){
                                        path = "";
                                    }
                                }
                                  
                                subTab.showCancelBird(false);
                                subTab.addBirdNodePressed(false);
                                subTab.visibleEditBirdButton(true);
                                subTab.visibleEditCampusButton(false);
                            }
                        } else{
                            subTab.showStaticLabels(false);
                            subTab.showCampusLabel(true);
                            subTab.campusNodePressed();
                            subTab.visibleEditBirdButton(false);
                            subTab.visibleEditCampusButton(true);
                            subTab.showCancelBird(false);
                            selectedBird="";
                            for (String path: filePaths){
                                path = "";
                            }
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

    public String aboutFieldText(){
        return subTab.aboutFieldText();
    }    

    public String locationFieldText(){
        return subTab.locationFieldText();
    }



    public String dietFieldText(){
        return subTab.dietFieldText();
    }


    public String getSelectedCampus(){
        return selectedCampus;
    }

    public String getSelectedBird(){
        return selectedBird;
    }

    public String getCampusFieldValue(){
        return subTab.getCampusFieldValue();
    }
    
    public void showWelcomeMessage(boolean bool){
        boolean notBool;
        if (bool == true){
            notBool = false;
        } else{
            notBool = true;
        }
        subTab.showWelcomeMessage(bool);
        subTab.showStaticLabels(notBool);
        subTab.addBirdNodePressed(notBool);
        subTab.editCampusOpen(notBool);
        subTab.visibleEditBirdButton(notBool);
        subTab.visibleEditCampusButton(notBool);
        subTab.editBirdSelected(notBool);
        subTab.showSaveCampus(notBool);
        subTab.showCancelBird(notBool);
        subTab.showSaveBird(notBool);
        subTab.showBirdLabels(notBool);
        subTab.showDeleteBird(notBool);
    }



    public JButton[] uploadButtons(){
        return subTab.uploadButtons();
    }



    public void saveCampusPressed(User user, List<Campus> campuses){
        String newName = subTab.getCampusFieldValue();
        String oldName = subTab.getSelectedCampus();
        DefaultMutableTreeNode getRoot = null;
        boolean newNameExists = false;
        for (JTree tree : trees){
            DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();
            if (oldName.equals((String)root.getUserObject())){
                getRoot=root;
            }
            if (newName.equals((String)root.getUserObject())){
                newNameExists=true;
            }
        }
        if (getRoot != null){
            if(newNameExists==false){
                
                for (Campus camp : campuses){
                    if(oldName.equals(camp.getName())){
                        camp.setName(newName);
                        camp.update(user, null);
                        break;
                    }
                }
                getRoot.setUserObject(newName);
                treeView.repaint();
                subTab.setResponse("Campus "+oldName+ " has been changed to "+ newName);
                subTab.editCampusClosed(newName);
            }else{
                subTab.setResponse("Campus with name "+ newName+ " already exists");
            } 
        }
        
    }

    public void saveBirdPressed(User user, List<Campus> campuses){
        String newName = subTab.getNameFieldText();
        String oldName = subTab.getSelectedBird();
        DefaultMutableTreeNode getRoot = null;
        JTree getTree = null;
        for (JTree tree : trees){
            DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();
            if (subTab.getCampusText().equals((String)root.getUserObject())){
                getRoot=root;
                getTree = tree;
            }
        }
        if (getRoot != null && getTree != null){
            TreePath path = getNamedNode(getRoot, oldName);
            if (path != null){
                String[] birdInfo = subTab.getEditedBirdInfo();
                Boolean updated = updateBirdInfo(oldName, getRoot.toString(), campuses, birdInfo, user);
                if (updated == true){
                    JTree tree = getTree;
                    tree.setSelectionPath(path);
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)path.getLastPathComponent();
                    selectedNode.setUserObject(newName);
                    //TODO - Harry - figure out how to resize the node so that the new name fits properly
                    treeView.repaint();
                    String[] newBirdInfo=getBirdInfo(newName, getRoot.toString(), campuses);
                    if(newBirdInfo != null){
                        subTab.setBirdLabels(newBirdInfo);
                    } else{
                        System.out.println("ERROR 1 in GUI-MainTab-saveBirdPressed");
                    }
                    subTab.setResponse("Bird: "+newName+" has been updated");
                    subTab.editBirdClosed();
                } else{
                    System.out.println("ERROR 2 in GUI-MainTab-saveBirdPressed");
                }
                
            } else{
                System.out.println("Something has gone wrong");
                System.out.println("oldName = "+oldName);
                System.out.println("Root name = "+(String)getRoot.getUserObject());
            }
        }
    }
    private TreePath getNamedNode(DefaultMutableTreeNode root, String name){
        Enumeration<TreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements()){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if (node.toString().equalsIgnoreCase(name)){
                if(node.isRoot()==false){
                    return new TreePath(node.getPath()); 
                }
            }
        }
        return null;
    }

    public void createTree(String campus){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(campus);
        JTree tree = new JTree(root);
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode tempRoot = (DefaultMutableTreeNode)model.getRoot();
        model.insertNodeInto(new DefaultMutableTreeNode("+[Add Bird]"), tempRoot, 0);
        trees = Arrays.copyOf(trees, trees.length+1);
        trees[trees.length-1]=tree;
    }

    public void addBird(String bird, JTree tree){
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(bird);
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
        model.insertNodeInto(node, root, 0);
    }

    public void removeTree(String campus){
        JTree[] copyTrees = {};
        for (JTree tree : trees){
            DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();
            if (campus.equals((String)root.getUserObject())==false){
                copyTrees=Arrays.copyOf(copyTrees, copyTrees.length+1);
                copyTrees[copyTrees.length-1]=tree;
            } 
        }
        trees=Arrays.copyOf(copyTrees, copyTrees.length);
    }

    public JTree getCampusTree(String campus){
        for (JTree tree : trees){
            DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();
            if (campus.equals((String)root.getUserObject())){
                return tree;
            } 

        }
        return null;
    }

    //checks if a bird already exists
    public boolean birdAlreadyExists(JTree campus, String birdName){
        
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)campus.getModel().getRoot();
            
        
        if (root != null){
            TreePath newNamePath = getNamedNode(root, birdName);
            if(newNamePath==null){
                return false;
            } else{
                return true;
            }
        }
        System.out.println("ERROR in Gui MainTab birdAlreadyExists");
        return true;
    }

    public boolean deleteBird(JTree campus, String birdName){
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)campus.getModel().getRoot();
            
        
        if (root != null){
            TreePath newNamePath = getNamedNode(root, birdName);
            if(newNamePath==null){
                return false;
            } else{
                DefaultTreeModel model = (DefaultTreeModel)campus.getModel();
                model.removeNodeFromParent((MutableTreeNode)newNamePath.getLastPathComponent());
                return true;
            }
        }
        System.out.println("ERROR in Gui MainTab deleteBird");
        return false;
    }

    //This gets the bird info stored in the server for a specific bird (with name String birdName) to display when that birds node is pressed in the tree 
    private String[] getBirdInfo(String birdName, String campus, List<Campus> campuses){
        for (Campus c : campuses) {
            if (c.getName().equals(subTab.getCampusText())) {
                for (Bird b : c.getBirds()) {
                    if (birdName.equals(b.getName())) {
                        // TODO - Connall - there's something wrong with the getX() funtions where
                        // they can't return certain characters, such as an apostrophe (')
                        String[] birdInfo = {b.getName(), b.getHeroImageURL(), b.getListImageURL(),
                        b.getSoundURL(), b.getAboutMe(), b.getAboutMeVideoURL(),
                        b.getLocation(), b.getLocationImageURL(), b.getDiet(), b.getDietImageURL()};
                        for (String path : filePaths){
                            path = "";
                        }
                        return birdInfo;
                    }
                }
            }
        }
        return null;
    }

    //called when save is pressed in edit bird, updates the bird info in the server
    private boolean updateBirdInfo(String oldBirdName, String campus, List<Campus> campuses, String[] birdInfo, User user){
        for (Campus c : campuses) {
            if (c.getName().equals(subTab.getCampusText())) {
                for (Bird b : c.getBirds()) {
                    if (oldBirdName.equals(b.getName())) {

                        b.setName(birdInfo[0]);
                        b.setAboutMe(birdInfo[1]);
                        b.setLocation(birdInfo[2]);
                        b.setDiet(birdInfo[3]);
                        //TODO - Connall in each of these if statements create a url using the file path and set it in the bird
                        if (filePaths[0]!=""){} 
                        if (filePaths[1]!=""){}
                        if (filePaths[2]!=""){}
                        if (filePaths[3]!=""){}
                        if (filePaths[4]!=""){}
                        if (filePaths[5]!=""){}
                        b.update(user, null);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //TODO - make sure this is nio and not io 
    //idk what the difference between those is it's just something Guiseppe said once
    //this is pretty self explanatory though
    private void setupUploadButtons(){
        for (JButton button : subTab.uploadButtons){
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    final JFileChooser fc = new JFileChooser();
                    if (subTab.returnButtonIndex(button)==2){
                        fc.setDialogTitle("Select an audio file");
                        fc.setAcceptAllFileFilterUsed(false);
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3, MP4 and WAV files", "MP3", "MP4", "WAV");
                        fc.addChoosableFileFilter(filter);
                        
                    } else if (subTab.returnButtonIndex(button)==3){
                        fc.setDialogTitle("Select a video");
                        fc.setAcceptAllFileFilterUsed(false);
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("MP4, MOV, WMV and AVI files", "MP4", "MOV", "WMV","AVI");
                        fc.addChoosableFileFilter(filter);
                        
                    }else{
                        fc.setDialogTitle("Select an image");
                        fc.setAcceptAllFileFilterUsed(false);
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG, JPEG, JPG and GIF images", "png", "gif", "jpeg","JPG");
                        fc.addChoosableFileFilter(filter);
                        
                    }
                    
                    int returnVal = fc.showOpenDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION){
                        File file = fc.getSelectedFile();
                        System.out.println("Opening "+file.getName());
                        button.setText("File selected: "+file.getName());
                        
                        String path = file.getPath();
                        //filePaths is explained at the top where it is created
                        filePaths[subTab.returnButtonIndex(button)] = path;
                    }

                }
                });
            }
        }

        public String[] returnFilePaths(){
            return filePaths;
        }
}
