package icarius.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import icarius.gui.items.TempCampus;
import icarius.gui.tabs.MainTab;
import icarius.gui.tabs.AdminTab;
import icarius.gui.tabs.loginTab;
//import icarius.user.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

import java.awt.BorderLayout;

public class Gui {
    private TempCampus[] campuses={};
    private JFrame mainFrame;

    private JFrame loginFrame;
    private static final int LOGIN_WINDOW_X_SIZE = 250;
    private static final int LOGIN_WINDOW_Y_SIZE = 150;
    private loginTab LoginTab;

    private JTabbedPane tabbedPane;
    private static final int MAIN_WINDOW_X_SIZE=600;
    private static final int MAIN_WINDOW_Y_SIZE=500;

    private MainTab mainTab;
    private AdminTab adminTab;


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

        mainTab = new MainTab();
        adminTab = new AdminTab();
    

        this.configureCampusButtons();
        this.configureBirdButtons();

        tabbedPane.addTab(mainTab.returnName(), null, mainTab.returnPanel());
        tabbedPane.addTab(adminTab.returnName(), null, adminTab.returnPanel());
        
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
        mainTab.createCampusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String campusFieldValue = mainTab.getCampusFieldValue();

                if (campusFieldValue.isBlank() == false){
                    TempCampus campus = null;
                    for (TempCampus c : campuses){
                        if (campusFieldValue.equals(c.getName())){
                            campus = c;
                        }
                    }
                    if(campus == null){
                        TempCampus newCampus = new TempCampus(campusFieldValue);

                        campuses=Arrays.copyOf(campuses, campuses.length+1);
                        campuses[campuses.length-1]=newCampus;


                        adminTab.updateCampusComboBox(campuses);

                        mainTab.createTree(campusFieldValue);
                        mainTab.updateTree();
                        mainTab.updateCampusRemover();

                        mainTab.setResponse(campusFieldValue + " added to campus list");
                    }else{
                        mainTab.setResponse("Campus: "+campusFieldValue+" already exists");
                    }
                } else{
                    mainTab.setResponse("Campus Name field cannot be left blank");
                }
            }
        });
        
        mainTab.removeCampusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String campusFieldValue = mainTab.campusToRemove();
                if (campusFieldValue != null) {
                    TempCampus campus = null;
                        for (TempCampus c : campuses){
                            if (c.getName()==campusFieldValue){
                                campus = c;
                            }
                        }
                    if (campus != null) {
                        TempCampus[] copyCampuses = {};
            
                        for (TempCampus i : campuses) {
                            if (i != campus) {
                                copyCampuses=Arrays.copyOf(copyCampuses, copyCampuses.length +1);
                                copyCampuses[copyCampuses.length-1]=i;
                            }
                        };

                        campuses=copyCampuses;
                        adminTab.updateCampusComboBox(campuses);

                        mainTab.removeTree(campusFieldValue);
                        mainTab.updateTree();
                        mainTab.updateCampusRemover();


                        mainTab.setResponse("Campus: " + campusFieldValue + " has been successfully removed.");
                    } else {
                        mainTab.setResponse("Campus: " + campusFieldValue + " does not exist");
                    }
                } else{
                    mainTab.setResponse("Campus: "+campusFieldValue+ " does not exist");
                }
            }
        });
    }

    private void configureBirdButtons(){
        mainTab.addBirdButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                String campusPressed = mainTab.getSelectedCampus();
                if (mainTab.nameFieldText().isBlank() ==false){
                        
                        TempCampus campus = null;
                        for (TempCampus c : campuses){
                            if (c.getName()==campusPressed){
                                campus = c;
                            }
                        }

                        if(campus != null){
                            JTree tree=mainTab.getCampusTree(campusPressed);
                            if(tree != null){
                                Boolean doesBirdNameExist = mainTab.birdAlreadyExists(tree, mainTab.nameFieldText());
                                if(doesBirdNameExist==false){
                                    mainTab.addBird(mainTab.nameFieldText(), tree);
                                    mainTab.updateTree();
                                    mainTab.setResponse("Bird: "+mainTab.nameFieldText()+" added to campus: "+campusPressed);
                                }else{
                                    mainTab.setResponse("Bird: "+mainTab.nameFieldText()+ ", Campus: "+ campusPressed+ " already exists");
                                }
                            } else{
                                System.out.println("The bird hasn't been added");
                            }
                        } else{
                            mainTab.setResponse("Campus: "+campusPressed+" does not exist");
                        }
                    
                }else{
                    mainTab.setResponse("Please fill in the required fields");
                }
            }
         });

        mainTab.saveCampusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                mainTab.saveCampusPressed(campuses);

            }
        });

        mainTab.saveBirdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                mainTab.saveBirdPressed();
            }
        });

    }


}

