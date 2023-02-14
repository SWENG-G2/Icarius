package icarius.gui.tabs;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import icarius.gui.items.campus;

//import ch.qos.logback.classic.db.names.ColumnName;

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
import java.awt.Component;

public class CampusTab extends Tab{

    private JTextField campusNameField;
    public JButton createCampusButton;
    private JLabel response;
    private JTextField campusIDField;
    public JButton removeCampusButton;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;

    public CampusTab(){
        this.tabName="Campus";

        //adding labels which won't need to change later
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Response:"), c);

        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Campus Name:"), c);

        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Campus ID:"), c);

        //adding any buttons, labels, or text fields which need variables for later

        campusNameField = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(campusNameField, c);

        createCampusButton = new JButton("Create");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.2;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(createCampusButton, c);

        response = new JLabel("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.8;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth=2;
        panel.add(response, c);

        campusIDField = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth=1;
        panel.add(campusIDField, c);

        removeCampusButton = new JButton("Remove");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.2;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(removeCampusButton, c);

        //Creating the campus table

        //TODO - Harry - set it so that the table perminantly has x (maybe 4?) rows, even if some of them are empty
        //               will probably need to edit the add and remove campus buttons actionPerformed
        //               to make these 4 aren't removed and unnecessary rows aren't added

        tableModel = new DefaultTableModel(){
            @Override
            //Stops the user editing the table by pressing on it
            public boolean isCellEditable(int row, int column){
                //all cells return false
                return false;
            }
        };
        table = new JTable(tableModel);
        tableModel.addColumn("Name");
        tableModel.addColumn("ID");
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
       c.gridx = 1;
       c.gridy = 4;
       c.gridwidth = 2;
       c.gridheight = 1;
       panel.add(scrollPane, c);

    }
    
    public void revalidate(){
        panel.revalidate();
        panel.repaint();
    }

    public String campusText(){
        return campusNameField.getText();
    }

    public String campusID(){
        return campusIDField.getText();
    }


    public void updateTable(campus[] campuses){

        tableModel.setRowCount(0);
        for (campus i : campuses){
            String[] row = {i.getName(), Integer.toString(i.getID())};
            tableModel.addRow(row);
        }

        if (tableModel.getRowCount()<4){
            tableModel.setRowCount(4);
        }
        
        this.revalidate();

    }
  

    public void setResponse(String text){
        response.setText(text);
    }
}