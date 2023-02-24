package icarius.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import icarius.gui.items.TempCampus;
import icarius.gui.items.TempBird;
import icarius.entities.*;
import icarius.gui.tabs.BirdTab;
import icarius.gui.tabs.CampusTab;
import icarius.gui.tabs.KeyTab;
import icarius.gui.tabs.loginTab;
import icarius.user.User;

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
import javax.swing.JTabbedPane;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

public class Gui {
    private TempCampus[] campuses={};
    private TempBird[] birds={};
    private JFrame mainFrame;
    private int nextID=0;

    private JFrame loginFrame;
    private static final int LOGIN_WINDOW_X_SIZE = 250;
    private static final int LOGIN_WINDOW_Y_SIZE = 150;
    private loginTab LoginTab;

    private JTabbedPane tabbedPane;
    private static final int MAIN_WINDOW_X_SIZE=600;
    private static final int MAIN_WINDOW_Y_SIZE=500;

    private CampusTab campusTab;
    private BirdTab birdTab;
    private KeyTab keyTab;


    public Gui(){
        super();
        this.setupFlatLaf();
        this.setupMainFrame();
        this.setupLoginFrame();
    }

    private void setupFlatLaf(){
        // sets the theme of the GUI
        FlatMacDarkLaf.setup();

        // These are for the look of components in the GUI from flatlaf
        UIManager.put("Button.arc", 5); // Makes button edges slightly curved
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("TabbedPane.showTabSeparators", true);
        UIManager.put("ScrollBar.showButtons", true);
        UIManager.put("Component.innerFocusWidth", 0);
        UIManager.put("Button.innerFocusWidth", 0);
        UIManager.put("Component.arrowType", "chevron");
        UIManager.put("Tree.showsRootHandles", true);
        UIManager.put("Tree.wideSelection",false);
        UIManager.put("Tree.paintLines", true);
        //TODO - Harry - get rid of the grey background of the trees
    }

    private void setupMainFrame(){
        mainFrame = new JFrame();
        mainFrame.setTitle("Icarus");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(MAIN_WINDOW_X_SIZE, MAIN_WINDOW_Y_SIZE);

        tabbedPane = new JTabbedPane();
        mainFrame.add(tabbedPane);
        
        JPanel logOutPanel = new JPanel();
        mainFrame.add(logOutPanel, BorderLayout.SOUTH);

        JButton logOutButton = new JButton("Log Out");
        logOutPanel.add(logOutButton);

        logOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                LoginTab.reset();
                //TODO - Harry - remove key tab when you set it up
                loginFrame.setVisible(true);
                mainFrame.setVisible(false);
            }
        });

        campusTab = new CampusTab();
        birdTab = new BirdTab();
        keyTab = new KeyTab();
    
        campusTab.updateTable(campuses);

        this.configureCampusButtons();
        this.configureBirdButtons();

        tabbedPane.addTab(campusTab.returnName(), null, campusTab.returnPanel());
        tabbedPane.addTab(birdTab.returnName(), null, birdTab.returnPanel());
        tabbedPane.addTab(keyTab.returnName(), null, keyTab.returnPanel());
        
        //done for testing purposes, it wouldn't be visable until the user logs in
        mainFrame.setVisible(true);

    }

    private void setupLoginFrame(){
        loginFrame = new JFrame();
        loginFrame.setTitle("Icarus Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(LOGIN_WINDOW_X_SIZE, LOGIN_WINDOW_Y_SIZE);
        loginFrame.setResizable(false);
        LoginTab = new loginTab();

        this.configureLoginButton();

        loginFrame.add(LoginTab.returnPanel());

        loginFrame.validate();

        //false for testing purposes, normally this would be true
        loginFrame.setVisible(false);
    }
    
    private void configureLoginButton(){
        LoginTab.loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                String usernameEntered = LoginTab.usernameField.getText();
                String keyEntered = LoginTab.getKey();

                // TODO - Alan - connect this to the stored usernames and IDs in the server
                // IMPORTANT - sysadmin logins and regular admin logins need to be seperated
                // as the key panel of icarus is only added to the main frame if sysadmin logs in

                // I'll add the key tab at some point in week 7 but for now just make it so that the login
                // works with the actual login stuff saved

                if (usernameEntered.equals("sysadmin") && keyEntered.equals("pass")) {
                    loginFrame.setVisible(false);
                    //TODO - Harry - sort out the key tab
                    mainFrame.validate();
                    mainFrame.setVisible(true);
                } else if (usernameEntered.equals("admin") && keyEntered.equals("pass")) {
                    loginFrame.setVisible(false);
                    mainFrame.setVisible(true);
                } else {
                    LoginTab.invalidLogin();
                }

            }
        });
    }

    private void configureCampusButtons(){
        campusTab.createCampusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String campusFieldValue = campusTab.campusText();

                if (campusFieldValue.isBlank() == false){
                    //TODO - Harry - When actual campus class is being used figure out what to change here
                    TempCampus newCampus = new TempCampus(campusFieldValue, nextID);

                    campuses=Arrays.copyOf(campuses, campuses.length+1);
                    campuses[campuses.length-1]=newCampus;
                    campusTab.updateTable(campuses);
                    birdTab.updateBirdTrees(campuses);
                    nextID = nextID + 1;
                    campusTab.setResponse(campusFieldValue + " added to campus list with ID: " + Integer.toString(nextID));
                } else{
                    campusTab.setResponse("Campus Name field cannot be left blank");
                    campusTab.setCampusText("");
                }
            }
        });
        
        campusTab.removeCampusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String campusFieldValue = campusTab.campusID();
                try {
                    int ID = Integer.parseInt(campusFieldValue);
                    int[] storedIDs = {};
                    for (TempCampus i : campuses){
                        storedIDs=Arrays.copyOf(storedIDs, storedIDs.length+1);
                        storedIDs[storedIDs.length-1]=i.getID();
                    }
                    int index = Arrays.binarySearch(storedIDs, ID);
                    if (index >= 0) {
                        TempCampus[] copyCampuses = {};
            
                        for (TempCampus i : campuses) {
                            if (i.getID() != ID) {
                                copyCampuses=Arrays.copyOf(copyCampuses, copyCampuses.length +1);
                                copyCampuses[copyCampuses.length-1]=i;
                            }
                        };

                        campuses=copyCampuses;
                        campusTab.updateTable(campuses);
                        birdTab.updateBirdTrees(campuses);

                        campusTab.setResponse("Campus ID: " + campusFieldValue + " has been successfully removed.");
                    } else {
                        campusTab.setResponse("Campus with ID " + campusFieldValue + " does not exist");
                    }
                } catch (NumberFormatException e) {
                    campusTab.setResponse("Campus ID must be an integer value");
                }
            }
        });
    }

    private void configureBirdButtons(){
        birdTab.addBirdButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                String campusPressed = birdTab.getCampus();
                if (birdTab.nameFieldText().isBlank() ==false){
                        /* 
                        TempCampus campus = null;
                        for (TempCampus c : campuses){
                            if (c.getID() == ID){
                                campus = c;
                            }
                        }
                        */

                        TempCampus campus = null;
                        for (TempCampus c : campuses){
                            if (c.getName()==campusPressed){
                                campus = c;
                            }
                        }

                        if(campus != null){
                            //create bird
                            birdTab.setResponse("the test is campus " + campus.getName() +" at id "+ campus.getID());
                            birds = Arrays.copyOf(birds, birds.length + 1);
                            TempBird bird = new TempBird(birdTab.nameFieldText(),campus);
                            bird.addCampus(campus);
                            birds[birds.length-1]= bird;

                            birdTab.updateBirdTrees(campuses);

                            birdTab.setResponse("Bird: "+birdTab.nameFieldText()+" added to campus: "+campusPressed);

                        } else{
                            birdTab.setResponse("Campus with ID "+campusPressed+" does not exist");
                        }
                    
                }else{
                    birdTab.setResponse("Please fill in the required fields");
                }
            }
         });
    }

}
