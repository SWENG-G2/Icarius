package icarius.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import icarius.App;
import icarius.auth.User;
import icarius.entities.Bird;
import icarius.entities.Campus;
import icarius.gui.items.TempCampus;
import icarius.gui.tabs.MainTab;
import icarius.gui.tabs.AdminTab;
import icarius.gui.tabs.loginTab;
import icarius.http.GetRequest;
import icarius.http.PostRequest;
import okhttp3.OkHttpClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.swing.JTabbedPane;
import javax.swing.JTree;

import java.awt.BorderLayout;

public class Gui {
    public User user;
    public List<Campus> campuses;
    
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


    public Gui(User user){
        this.user = user;
        this.setupFlatLaf();
        this.setupMainFrame();
        this.setupLoginFrame();
        this.initializeGUI();
    }

    private void updateCampusesArray(){
        // send and store GET request response
        GetRequest request = new GetRequest("/campus/list", user.getOkHttpClient());
        String response = request.send();
        
        if (response != null) {
            String campusId;
            List<Campus> updatedList = new ArrayList<>();
            // Fetch name from XML response
            try {
                Document document = DocumentHelper.parseText(response);
                Element root = document.getRootElement();
                // iterate through child elements of presentation with element name "slide"
                for (Iterator<Element> it = root.elementIterator("slide"); it.hasNext();) {
                    Element slide = it.next();
                    Campus newCampus = new Campus(user.getOkHttpClient());
                    campusId = slide.attributeValue("title");
                    newCampus.setId(Long.parseLong(campusId));
                    newCampus.read(null);
                    updatedList.add(newCampus);
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            
            this.campuses = updatedList;
        }

        // TODO - Harry - This function updates campus array but not tree?
    }

    private void initializeGUI(){
        updateCampusesArray();
        mainTab.updateTree();
        mainTab.updateCampusRemover();
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
        
        //TODO - Connall - set to false
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

        //TODO - Connall - set to true
        //false for testing purposes, normally this would be true
        loginFrame.setVisible(false);
    }
    
    private void configureLoginButton(){
        LoginTab.loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                String usernameEntered = LoginTab.usernameField.getText();
                String keyEntered = LoginTab.getKey();

                // TODO - Connall - connect this to the stored usernames and IDs in the server
                // IMPORTANT - sysadmin logins and regular admin logins need to be seperated
                // as the key panel of icarus is only added to the main frame if sysadmin logs in

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
                if (campusFieldValue.isBlank()) {
                    // If input is null
                    mainTab.setResponse("Campus Name field cannot be left blank");
                } else if ( campusExists(campusFieldValue) ) {
                    mainTab.setResponse("Campus: "+campusFieldValue+" already exists");
                } else {
                    Campus newCampus = new Campus(user.getOkHttpClient());
                    newCampus.setName(campusFieldValue);
                    newCampus.create(user, null);
                    updateCampusesArray();

                    adminTab.updateCampusComboBox(campuses);

                    mainTab.createTree(campusFieldValue);
                    mainTab.updateTree();
                    mainTab.updateCampusRemover();

                    mainTab.setResponse(campusFieldValue + " added to campus list");
                }
            }
        });
        
        mainTab.removeCampusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String campusFieldValue = mainTab.campusToRemove();
                if (campusFieldValue.isBlank()) {
                    mainTab.setResponse("Campus: " + campusFieldValue + " does not exist");
                } else {
                    Campus campusToRemove = null;

                    // Search for campus
                    for (Campus campus : campuses) {
                        if (campus.getName() == campusFieldValue) {
                            campusToRemove = campus;
                            break;
                        }
                    }

                    // Remove campus from server and locally
                    if (campusToRemove.delete(user, null)) {
                        campuses.remove(campusToRemove);
                        mainTab.setResponse("Campus: " + campusFieldValue + " has been successfully removed.");
                    } else {
                        mainTab.setResponse("Campus: Failed to remove " + campusFieldValue + " from server!");
                    }

                    updateCampusesArray();
                    adminTab.updateCampusComboBox(campuses);

                    mainTab.removeTree(campusFieldValue);
                    mainTab.updateTree();
                    mainTab.updateCampusRemover();
                }
            }
        });
    }

    private void configureBirdButtons(){
        mainTab.addBirdButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                String campusPressed = mainTab.getSelectedCampus();
                if (mainTab.nameFieldText().isBlank() == false){
                        Campus campus = null;
                        for (Campus c : campuses){
                            if (c.getName()==campusPressed){
                                campus = c;
                            }
                        }

                        if(campus != null){
                            JTree tree=mainTab.getCampusTree(campusPressed);
                            if(tree != null){
                                Boolean doesBirdNameExist = mainTab.birdAlreadyExists(tree, mainTab.nameFieldText());
                                if(doesBirdNameExist==false){
                                    // Create Bird
                                    Bird newBird = new Bird(user.getOkHttpClient());
                                    newBird.setName(campusPressed);
                                    newBird.create(user, null);

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

        //TODO - Harry - write a func to delete a bird

        mainTab.saveCampusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                mainTab.saveCampusPressed(user, campuses);

            }
        });

        mainTab.saveBirdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                mainTab.saveBirdPressed();
            }
        });

    }

    private boolean campusExists(String name) {
        for (Campus campus : campuses) {
            if (campus.getName() == name) {
                return true;
            }
        }
        return false;
    }

}

