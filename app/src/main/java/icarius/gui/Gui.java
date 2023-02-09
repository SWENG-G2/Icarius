package icarius.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import ch.qos.logback.classic.db.names.ColumnName;

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

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Color;



import org.apache.naming.java.javaURLContextFactory;
import org.yaml.snakeyaml.util.ArrayUtils;

import javax.swing.JTabbedPane;



public class Gui {

    private static final int LOGIN_WINDOW_X_SIZE = 250;
	private static final int LOGIN_WINDOW_Y_SIZE = 150;
    private static final int MAIN_WINDOW_X_SIZE = 300;
	private static final int MAIN_WINDOW_Y_SIZE = 500;
    private JButton createCampusButton;
    private JButton removeCampusButton;
    private JLabel campusResponse;
    private JTextField campusNameField;
    private JTextField campusIDField;

    //explanation in campus panel
    private int nextID = 0;

    //The values in this are temporary
    //TODO - Alan - write some code somewhere that puts all of the campus IDs stored on the server
    //into this array, only needs to be done once when the program is loaded
    private int[] storedIDs = {1, 2};

    //This is temporary, when the GUI is connected to the rest of the program this will probably be replaced
    private String[][] campusTableData = {
        {"UoY", "1"},
        {"Durham", "2"}
    };

    //This is temporary, when the GUI is connected to the rest of the program this will probably be replaced
    private String[][] keyTableData ={
        {"sysadmin", "1", "pass"},
        {"admin", "1", "pass"}
    };

    
    public Gui(){
        super();
        this.setupGUI();
    }

    /**
     * 
     */
    private void setupGUI(){
        //sets the theme of the GUI
        FlatMacDarkLaf.setup();

        //These are for the look of components in the GUI from flatlaf
        UIManager.put("Button.arc", 5); //Makes button edges slightly curved
        UIManager.put( "Component.focusWidth", 1);
        UIManager.put( "TabbedPane.showTabSeparators", true );
        UIManager.put( "ScrollBar.showButtons", true );
        UIManager.put( "Component.innerFocusWidth", 0 );
        UIManager.put( "Button.innerFocusWidth", 0 );
        UIManager.put( "Component.arrowType", "triangle" );

        //Sets up the login frame
        JFrame loginFrame = new JFrame();
        loginFrame.setTitle("Icarus Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(LOGIN_WINDOW_X_SIZE, LOGIN_WINDOW_Y_SIZE);
        loginFrame.setResizable(false);

        //Sets up the main icarus frame
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Icarus");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(MAIN_WINDOW_X_SIZE, MAIN_WINDOW_Y_SIZE);
        mainFrame.setVisible(false);

        //------------Main Screen-------------

        JTabbedPane tabbedPane = new JTabbedPane();
        mainFrame.add(tabbedPane);


        //------------Duck Menu Panel-------------

        /*The duck menu is a JTree which displays all stored
        information on the ducks. It is sorted in the format 
        [campuses]->[ducks in selected campus]->[information on selected duck]
         */

        //This might be added as a side panel in the create duck panel in the final verison but I'll see how it goes

        //TODO - Harry - sort out the look of the Jtree to more closely resemble the ones in the flatlaf demo

        JPanel duckMenuPanel = new JPanel();
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Campuses");


        JTree tree = new JTree(root);
        duckMenuPanel.add(tree);

        for (int c: storedIDs){
            root.add(new DefaultMutableTreeNode(c));
        }

        //TODO - Harry - figure out how to add existing ducks and info here as well

        //------------Create Bird Panel-------------

        JPanel createDuckPanel = new JPanel();
        createDuckPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints cDc = new GridBagConstraints();
        
        //adding labels which won't need to change later
        cDc.weightx = 0.2;
        cDc.gridx = 0;
        cDc.gridy = 0;
        createDuckPanel.add(new JLabel("Bird to Create:"), cDc);

        cDc.weightx = 0.2;
        cDc.gridx = 0;
        cDc.gridy = 1;
        createDuckPanel.add(new JLabel("Campus ID:"), cDc);

        //adding any buttons, labels, or text fields which need variables for later
        JTextField birdCreateField = new JTextField("");
        cDc.fill = GridBagConstraints.HORIZONTAL;
        cDc.weightx = 0.8;
        cDc.gridx = 1;
        cDc.gridy = 0;
        createDuckPanel.add(birdCreateField, cDc);

        JTextField birdCampusField = new JTextField("");
        cDc.fill = GridBagConstraints.HORIZONTAL;
        cDc.weightx = 0.8;
        cDc.gridx = 1;
        cDc.gridy = 1;
        createDuckPanel.add(birdCampusField, cDc);

        JButton addBirdButton = new JButton("Add Duck");
        cDc.fill = GridBagConstraints.HORIZONTAL;
        cDc.weightx = 0.5;
        cDc.gridx = 1;
        cDc.gridy = 2;
        createDuckPanel.add(addBirdButton, cDc);

        JLabel birdResponse = new JLabel("");
        cDc.fill = GridBagConstraints.HORIZONTAL;
        cDc.weightx = 0.5;
        cDc.gridx = 0;
        cDc.gridy = 3;
        createDuckPanel.add(birdResponse, cDc);

        //TODO - Harry - add the rest of the buttons from the odysseus version of this and get them working
        
        addBirdButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                String campusFieldValue = birdCampusField.getText();
                try{
                    int ID = Integer.parseInt(campusFieldValue);
                    int index = Arrays.binarySearch(storedIDs, ID);
                    if(index >= 0){
                        //TODO - Harry - get this to actually add a bird to the JTree
                        /*
                        TreePath path = tree.getNextMatch(campusFieldValue, 0, Position.Bias.Forward);
                        MutableTreeNode mNode = (MutableTreeNode)path.getLastPathComponent();
                        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
                        mNode.add(new MutableTreeNode(birdCreateField.getText()));
                        model.reload(root);
                        duckMenuPanel.validate();
                        birdResponse.setText(birdCreateField.getText() + " has been added to campus "+campusFieldValue);
                        */

                        //TODO - Alan - You probably want to sort out the todo's that come after this first
                        //              as I haven't finished this yet. Uploading the picture and sound and 
                        //              whatever else will likely also be in this function, but I haven't
                        //              done any work on that yet.
                        //Connect this to the actual server
                    } else{
                        birdResponse.setText("Campus with ID "+campusFieldValue+" does not exist");
                    }
                } catch (NumberFormatException e){
                    birdResponse.setText("Campus must be an integer value");
                } 
            }
         });

         //TODO - Harry - add a remove bird button and edit bird button maybe? Figure out how these would both work
         //Maybe the edit bird button will just replace the version of the bird currently stored with a new one?
         //Or at least one that will inherit past data which hasn't been entered.
         

        //------------Campus Panel-------------

        JPanel campusPanel = new JPanel();
        campusPanel.setLayout(new GridBagLayout());
        GridBagConstraints cC = new GridBagConstraints(); //cC = campus [panel] constraints

        //adding labels which won't need to change later
        cC.weightx = 0.2;
        cC.gridx = 0;
        cC.gridy = 2;
        campusPanel.add(new JLabel("Response:"), cC);

        cC.weightx = 0.2;
        cC.gridx = 0;
        cC.gridy = 0;
        campusPanel.add(new JLabel("Campus Name:"), cC);

        cC.weightx = 0.2;
        cC.gridx = 0;
        cC.gridy = 1;
        campusPanel.add(new JLabel("Campus ID:"), cC);

        //adding any buttons, labels, or text fields which need variables for later
        cC.fill = GridBagConstraints.HORIZONTAL;
        cC.weightx = 0.6;
        cC.gridx = 1;
        cC.gridy = 0;
        campusPanel.add(campusNameField = new JTextField(""), cC);

        cC.fill = GridBagConstraints.HORIZONTAL;
        cC.weightx = 0.2;
        cC.gridx = 2;
        cC.gridy = 0;
        campusPanel.add(createCampusButton = new JButton("Create"), cC);

        cC.fill = GridBagConstraints.HORIZONTAL;
        cC.weightx = 0.8;
        cC.gridx = 1;
        cC.gridy = 2;
        cC.gridwidth=2;
        campusPanel.add(campusResponse = new JLabel(""), cC);


        cC.fill = GridBagConstraints.HORIZONTAL;
        cC.weightx = 0.6;
        cC.gridx = 1;
        cC.gridy = 1;
        cC.gridwidth=1;
        campusPanel.add(campusIDField = new JTextField(""), cC);

        cC.fill = GridBagConstraints.HORIZONTAL;
        cC.weightx = 0.2;
        cC.gridx = 2;
        cC.gridy = 1;
        campusPanel.add(removeCampusButton = new JButton("Remove"), cC);

        //Creating the campus table

        //TODO - Harry - set it so that the table perminantly has x (maybe 4?) rows, even if some of them are empty
        //               will probably need to edit the add and remove campus buttons actionPerformed
        //               to make these 4 aren't removed and unnecessary rows aren't added

        DefaultTableModel campusTableModel = new DefaultTableModel();
        JTable campusTable = new JTable(campusTableModel);
        campusTableModel.addColumn("Name");
        campusTableModel.addColumn("ID");
        campusTable.setShowGrid(true);
        for (String[] i: campusTableData){
            campusTableModel.insertRow(campusTableModel.getRowCount(), i);
        };


        JScrollPane cTableSPane = new JScrollPane(campusTable);
        cTableSPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        campusTable.setPreferredScrollableViewportSize(campusTable.getPreferredSize());
        campusTable.setFillsViewportHeight(true);

       cC.fill = GridBagConstraints.HORIZONTAL;
       cC.weightx = 0.5;
       cC.gridx = 1;
       cC.gridy = 4;
       cC.gridwidth = 2;
       cC.gridheight = 1;
       campusPanel.add(cTableSPane, cC);

        //Updates the nextID variable based on the size of the storedIDs array
        //storedIDs array represents the campus IDs that would be stored in the server database
        nextID = storedIDs[storedIDs.length-1] + 1;
        //TODO - Alan - you can probably leave this, but I'm not sure if there's already a similar
        //variable to this somewhere else in the icarus program (I haven't looked).
        //If there is change nextID to just equal that, or even replace it all together, up to you.       


        createCampusButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                String campusFieldValue = campusNameField.getText();
                campusResponse.setText(campusFieldValue + " added to campus list with ID: " + Integer.toString(nextID));
                root.add(new DefaultMutableTreeNode(nextID));
                DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
                model.reload(root);
                duckMenuPanel.validate();
                //TODO - Alan - add to the server database itself

                String[] row = {campusFieldValue, Integer.toString(nextID)};
                campusTableModel.insertRow(campusTableModel.getRowCount(), row);

                storedIDs = Arrays.copyOf(storedIDs, storedIDs.length+1);
                storedIDs[storedIDs.length-1] = nextID;

                nextID = nextID + 1;

                //TODO - Alan - add the added campus to the server, make sure you sort of the nextID variable first (see above) 
                mainFrame.validate();
            }
         });

         removeCampusButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               String campusFieldValue = campusIDField.getText();
               try{
                int ID = Integer.parseInt(campusFieldValue);
                int index = Arrays.binarySearch(storedIDs, ID);
                if(index >= 0){
                    campusTableModel.removeRow(index);
                    int[] copyIDs = {};
                    //This copys all of the values of the storedIDs array except for the one removed
                    for (int i : storedIDs){
                        if (i != ID){
                            copyIDs = Arrays.copyOf(copyIDs, copyIDs.length+1);
                            copyIDs[copyIDs.length-1]=i;
                        }
                    };
                    storedIDs = copyIDs;

                    TreePath path = tree.getNextMatch(campusFieldValue, 0, Position.Bias.Forward);
                    MutableTreeNode mNode = (MutableTreeNode)path.getLastPathComponent();
                    DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
                    model.removeNodeFromParent(mNode);

                    //TODO - Alan - remove the campus from the database from the server
                    //nothing else in here needs to be changed I think

                    campusResponse.setText("Campus ID " + campusFieldValue + " at index " +index+ " removed.");
                } else{
                    campusResponse.setText("Campus with ID "+campusFieldValue+" does not exist");
                }
               } catch (NumberFormatException e){
                campusResponse.setText("Campus ID must be an integer");
               }               
            }
         });



        //------------Key Panel-------------

         // TODO - Alan - Link this to the actual authentication service/ key storage service 
         // NOTE: I made this panel without fully knowing what to put in it, I just guessed
         //       based on the getAuth() function in AuthenticationService.java, so change
         //       whatever you need to here, 


        //Makes the panel and sets its layout to grid bag layout
        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new GridBagLayout());
        GridBagConstraints cK = new GridBagConstraints(); //cK = key [panel] constraints

        //adding labels which won't need to change later
        cK.weightx = 0.2;
        cK.gridx = 0;
        cK.gridy = 0;
        keyPanel.add(new JLabel("New Username:"), cK);

        cK.weightx = 0.2;
        cK.gridx = 0;
        cK.gridy = 1;
        keyPanel.add(new JLabel("Campus ID:"), cK);

        cK.weightx = 0.2;
        cK.gridx = 0;
        cK.gridy = 2;
        keyPanel.add(new JLabel("Response:"), cK);


        //adding any buttons, labels, or text fields which need variables for later
        JTextField newUserField = new JTextField("");
        cK.fill = GridBagConstraints.HORIZONTAL;
        cK.weightx = 0.6;
        cK.gridx = 1;
        cK.gridy = 0;
        keyPanel.add(newUserField, cK);

        JTextField userCampusField = new JTextField("");
        cK.fill = GridBagConstraints.HORIZONTAL;
        cK.weightx = 0.6;
        cK.gridx = 1;
        cK.gridy = 1;
        keyPanel.add(userCampusField, cK);

        JButton generateKeyButton = new JButton("Generate Key");
        cK.fill = GridBagConstraints.HORIZONTAL;
        cK.weightx = 0.2;
        cK.gridx = 2;
        cK.gridy = 1;
        keyPanel.add(generateKeyButton, cK);

        JLabel keyResponse = new JLabel("");
        cK.weightx = 0.2;
        cK.gridx = 1;
        cK.gridy = 2;
        keyPanel.add(keyResponse, cK);

        //Creating the key table

        //TODO - Harry - set it so that the table perminantly has x (maybe 4?) rows, even if some of them are empty
        //               will probably need to edit the generate key action performed function
        //               unnecessary rows aren't added.

        DefaultTableModel keyTableModel = new DefaultTableModel();
        JTable keyTable = new JTable(keyTableModel);
        keyTableModel.addColumn("Username");
        keyTableModel.addColumn("Campus");
        keyTableModel.addColumn("Key");
        keyTable.setShowGrid(true);

        //keyTableData is a stand in for key storage service, it's initialised as a private variable earlier on
        for (String[] i: keyTableData){
            keyTableModel.insertRow(keyTableModel.getRowCount(), i);
        };
         
        //Sets up the scroll plane which the table will be stored in
        JScrollPane kTableSPane = new JScrollPane(keyTable);
        kTableSPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        keyTable.setPreferredScrollableViewportSize(keyTable.getPreferredSize());
        keyTable.setFillsViewportHeight(true);

        //adds the table to the panel
        cK.fill = GridBagConstraints.HORIZONTAL;
        cK.weightx = 0.5;
        cK.gridx = 1;
        cK.gridy = 3;
        cK.gridwidth = 2;
        cK.gridheight = 1;
        keyPanel.add(kTableSPane, cK);

        //Template of the kind of thing this should do
        generateKeyButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                String campusFieldValue = userCampusField.getText();
                try{
                    int ID = Integer.parseInt(campusFieldValue);
                    //^this will trigger the exception if an int isn't entered
                    int index = Arrays.binarySearch(storedIDs, ID);
                    //Makes sure the entered campus is valid. If it isn't a negative value will be returned
                if(index >= 0){
                    //TODO - Alan - have a key be generated here using AuthenticationService.java 
                    //and have it be stored in KeyStorageService.java (I think, I'm not actually sure)

                    //"pass" is a temporary stand in for the key which is generated
                    String[] row = {newUserField.getText(), campusFieldValue, "pass"};
                    keyTableModel.insertRow(keyTableModel.getRowCount(), row);
                    keyResponse.setText("Key generated for user "+newUserField.getText()+" at campus " +campusFieldValue);
                    keyPanel.validate();
                    mainFrame.validate();

                }else{
                    keyResponse.setText("Campus " +campusFieldValue+ " does not exist" );
                }
                }catch (NumberFormatException e){
                    keyResponse.setText("Campus ID must be an integer");
                }   
            }
        });

        //TODO - Harry - check with other members to see if there needs to be a button to remove users

        //The key panel is only added if a sysadmin login is entered, so it'll only be added later
        tabbedPane.addTab("Campus", null, campusPanel);
        tabbedPane.addTab("Create Duck", null, createDuckPanel);
        tabbedPane.addTab("Duck Menu", null, duckMenuPanel);

        
        
        mainFrame.validate();

        //-----------Login Screen------------

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints cL = new GridBagConstraints(); //cL = login contraints
        
        //adding labels which won't need to change later
        cL.weightx = 0.1;
        cL.gridx = 0;
        cL.gridy = 1;
        loginPanel.add(new JLabel("Username:"), cL);

        cL.weightx = 0.1;
        cL.gridx = 0;
         cL.gridy = 2;
        loginPanel.add(new JLabel("Key:"), cL);

        //adding any buttons, labels, or text fields which need variables for later
        JTextField usernameTextField = new JTextField("");
        cL.fill = GridBagConstraints.HORIZONTAL;
        cL.weightx = 0.9;
        cL.gridx = 1;
        cL.gridy = 1;
        loginPanel.add(usernameTextField, cL);

        JPasswordField keyPasswordField = new JPasswordField();
        cL.fill = GridBagConstraints.HORIZONTAL;
        cL.weightx = 0.9;
        cL.gridx = 1;
        cL.gridy = 2;
        loginPanel.add(keyPasswordField, cL);

        JButton loginButton = new JButton("Login");
        cL.fill = GridBagConstraints.HORIZONTAL;
        cL.weightx = 0.5;
        cL.gridx = 1;
        cL.gridy = 3;
        loginPanel.add(loginButton, cL);

        JLabel loginLabel = new JLabel("");
        cL.weightx = 0.5;
        cL.gridwidth=2;
        cL.gridx = 0;
        cL.gridy = 0;
        loginPanel.add(loginLabel, cL);

        loginButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                
               String usernameEntered = usernameTextField.getText();
               String keyEntered = String.valueOf(keyPasswordField.getPassword());
               //If statements contain temporary usernames and passwords, to be updated later
               //TODO - Alan - connect this to the stored usernames and IDs in the server
               //IMPORTANT - sysadmin logins and regular admin logins need to be seperated
               //            as the key panel of icarus is only added  to the main frame
               //            if a sysadmin logs in
               if  (usernameEntered.equals("sysadmin") && keyEntered.equals("pass")){
                    loginFrame.setVisible(false);
                    tabbedPane.addTab("Key", null, keyPanel);
                    mainFrame.validate();
                    mainFrame.setVisible(true);
               } else if (usernameEntered.equals("admin") && keyEntered.equals("pass")){
                //Only users with sysadmin logins will be able to access the key tab
                loginFrame.setVisible(false);
                mainFrame.setVisible(true);
               } else{
                    loginLabel.setText("               Username or key incorrect");
                    loginLabel.setForeground(Color.RED);
                    keyPasswordField.setText("");
               }
            
            }
         });

         loginFrame.add(loginPanel);

         loginFrame.validate();

        //------------Log Out Panel-------------

        //As this button will have to stay in the same position regardless of which panel is accessed
        //it is placed in its own panel which will be placed below the tabbed panel
        JPanel logOutPanel = new JPanel();
        mainFrame.add(logOutPanel, BorderLayout.SOUTH); 

        JButton logOutButton = new JButton("Log Out");
        logOutPanel.add(logOutButton);

        logOutButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                usernameTextField.setText("");
                keyPasswordField.setText("");
                loginFrame.setVisible(true);
                mainFrame.setVisible(false);
            }
        });

        mainFrame.validate();
        //mainFrame.pack();
        loginFrame.setVisible(true);
    }

    
}


