package icarius.gui.tabs;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import icarius.entities.Campus;

import java.awt.GridBagConstraints;
import java.util.Arrays;
import java.util.List;


public class AdminTab extends Tab{
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField usernameField;
    private JTextField passwordField;
    private JComboBox campusComboBox;
    private JComboBox userComboBox;

    private String[] users = {};

    private JButton createUserButton;
    private JButton addCampusButton;

    public AdminTab(){
        this.tabName="Admin";
        // TODO - Harry - Create credentials button action?

        //adding labels which won't need to change later
        
        c.weightx = 0.2;
        c.gridwidth=1;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Create New User:"), c);

        c.weightx = 0.2;
        c.gridwidth=1;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(new JLabel("Add Campus To User:"), c);

        c.gridwidth=1;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Username:"), c);

        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Password:"), c);

        c.weightx = 0.2;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(new JLabel("User:"), c);

        c.weightx = 0.2;
        c.gridx = 2;
        c.gridy = 2;
        panel.add(new JLabel("Campus:"), c);

        //adding any buttons, labels, or text fields which need variables for later

        usernameField = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.2;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(usernameField, c);

        passwordField = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.2;
        c.gridx = 1;
        c.gridy = 2;
        panel.add(passwordField, c);

        String[] emptyArray = {};
        campusComboBox = new JComboBox<>(emptyArray);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 2;
        panel.add(campusComboBox, c);

        userComboBox = new JComboBox<>(emptyArray);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 1;
        panel.add(userComboBox, c);

        createUserButton = new JButton("Create User");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.2;
        c.gridx = 1;
        c.gridy = 3;
        panel.add(createUserButton, c);

        addCampusButton = new JButton("Add Campus");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 3;
        panel.add(addCampusButton, c);

        //Creating the table
        tableModel = new DefaultTableModel(){
            @Override
            //Stops the user editing the table by pressing on it
            public boolean isCellEditable(int row, int column){
                //all cells return false
                return false;
            }
        };
        table = new JTable(tableModel);
        tableModel.addColumn("Username");
        
        tableModel.addColumn("Campuses");
        table.setShowGrid(true);

        for(int i=0; i<4; i++){
            String[] blankRow = {"",""};
            tableModel.addRow(blankRow);
        }

        scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);

       c.fill = GridBagConstraints.HORIZONTAL;
       c.weightx = 0.5;
       c.gridx = 0;
       c.gridy = 5;
       c.gridwidth = 4;
       c.gridheight = 1;
       panel.add(scrollPane, c);
    }
    
    //TODO - Change this to campus
    public void updateTable(String username, String password, List<Campus> campuses){
        tableModel.setRowCount(0);

    }

    public void updateCampusComboBox(List<Campus> campuses){
        String[] campusesText={};
        for (Campus camp : campuses){
            campusesText=Arrays.copyOf(campusesText, campusesText.length+1);
            campusesText[campusesText.length-1]=camp.getName();
        }
        DefaultComboBoxModel model = (DefaultComboBoxModel) campusComboBox.getModel();
        model.removeAllElements();

        for (String text : campusesText){
            model.addElement(text);
        }

    }

    //TODO - write an update table function
}
