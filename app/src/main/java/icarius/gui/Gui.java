package icarius.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;


import icarius.gui.frames.MainFrame;
import icarius.gui.items.campus;
import icarius.gui.tabs.BirdTab;
import icarius.gui.tabs.CampusTab;
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
    private campus[] campuses={};
    private JFrame mainFrame;
    private int nextID=0;

    private JFrame loginFrame;
    private static final int LOGIN_WINDOW_X_SIZE = 250;
    private static final int LOGIN_WINDOW_Y_SIZE = 150;
    private loginTab LoginTab;

    private JTabbedPane tabbedPane;
    private static final int MAIN_WINDOW_X_SIZE=400;
    private static final int MAIN_WINDOW_Y_SIZE=400;

    private CampusTab campusTab;
    private BirdTab birdTab;


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
                //TODO remove key tab
                loginFrame.setVisible(true);
                mainFrame.setVisible(false);
            }
        });

        campusTab = new CampusTab();
        birdTab = new BirdTab();
    
        campusTab.updateTable(campuses);

        this.configureCampusButtons();
        this.configureBirdButtons();

        tabbedPane.addTab(campusTab.returnName(), null, campusTab.returnPanel());
        tabbedPane.addTab(birdTab.returnName(), null, birdTab.returnPanel());
        mainFrame.setVisible(false);

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
        loginFrame.setVisible(true);
    }
    
    private void configureLoginButton(){
        LoginTab.loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                String usernameEntered = LoginTab.usernameField.getText();
                String keyEntered = LoginTab.getKey();
                // TODO - Alan - connect this to the stored usernames and IDs in the server
                // IMPORTANT - sysadmin logins and regular admin logins need to be seperated
                // as the key panel of icarus is only added to the main frame
                // if a sysadmin logs in
                if (usernameEntered.equals("sysadmin") && keyEntered.equals("pass")) {
                    loginFrame.setVisible(false);
                    //tabbedPane.addTab("Key", null, keyPanel);
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
                campusTab.setResponse(campusFieldValue + " added to campus list with ID: " + Integer.toString(nextID));

                // TODO - Alan - add to the server database itself

                // TODO - add this into the new version of the program
                // addCampusTree(nextID);

                campus newCampus = new campus(campusFieldValue, nextID);
                campuses=Arrays.copyOf(campuses, campuses.length+1);
                campuses[campuses.length-1]=newCampus;
                campusTab.updateTable(campuses);

                nextID = nextID + 1;
                // TODO - Alan - add the added campus to the server, make sure you sort of the
                // nextID variable first (see elsewhere)

            }
        });
        
        campusTab.removeCampusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String campusFieldValue = campusTab.campusID();
                try {
                    int ID = Integer.parseInt(campusFieldValue);
                    int[] storedIDs = {};
                    for (campus i : campuses){
                        storedIDs=Arrays.copyOf(storedIDs, storedIDs.length+1);
                        storedIDs[storedIDs.length-1]=i.getID();
                    }
                    int index = Arrays.binarySearch(storedIDs, ID);
                    if (index >= 0) {
                        campus[] copyCampuses = {};
                        // removeCampusTrees(); //TODO - update when you update this
                        // This copys all of the values of the storedIDs array except for the one
                        // remove
                        for (campus i : campuses) {
                            if (i.getID() != ID) {
                                copyCampuses=Arrays.copyOf(copyCampuses, copyCampuses.length +1);
                                copyCampuses[copyCampuses.length-1]=i;
                                // addCampusTree(i); //TODO - update when you update this
                            }
                        };

                        // TODO when you update trees
                        /*
                         * JTree[] copyTrees = {};
                         * for (JTree i: campusTrees){
                         * if (Arrays.asList(campusTrees).indexOf(i) != index){
                         * copyTrees= Arrays.copyOf(copyTrees, copyTrees.length + 1);
                         * copyTrees[copyTrees.length-1]=i;
                         * }
                         * };
                         */
                        campuses=copyCampuses;
                        campusTab.updateTable(campuses);
                        // campusTrees = copyTrees; //TODO

                        // TODO - Alan - remove the campus from the database from the server
                        // nothing else in here needs to be changed I think

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

    }

}
