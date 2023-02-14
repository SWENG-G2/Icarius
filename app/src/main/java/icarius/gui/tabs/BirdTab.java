package icarius.gui.tabs;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

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

public class BirdTab extends Tab{
    private JTextField birdNameField;
    private JTextField birdCampusField;
    public JButton addBirdButton;
    private JLabel birdResponse;
    private JTree[] campusTrees = {};
    private JScrollPane scrollPane;
    private JPanel treeView;
    private GridBagConstraints cT;

    public BirdTab(){
        super();
                this.tabName="Bird";

                //adding labels which won't need to change later
                c.weightx = 0.2;
                c.gridx = 0;
                c.gridy = 0;
                panel.add(new JLabel("Bird to Create:"), c);
        
                 
                c.weightx = 0.2;
                c.gridx = 0;
                c.gridy = 1;
                panel.add(new JLabel("Campus ID:"), c);
        
                c.weightx = 0.2;
                c.gridx = 0;
                c.gridy = 3;
                panel.add(new JLabel("Response:"), c);
        
                //adding any buttons, labels, or text fields which need variables for later
                birdNameField = new JTextField("");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 1;
                c.gridy = 0;
                panel.add(birdNameField, c);
        
                birdCampusField = new JTextField("");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.6;
                c.gridx = 1;
                c.gridy = 1;
                panel.add(birdCampusField, c);
        
                addBirdButton = new JButton("Add Bird");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 1;
                c.gridy = 2;
                panel.add(addBirdButton, c);
        
                birdResponse = new JLabel("");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 1;
                c.gridy = 3;
                panel.add(birdResponse, c);
        
                //TODO - Harry - add the rest of the buttons from the odysseus version of this and get them working
                
                treeView = new JPanel(new GridBagLayout());    
                cT = new GridBagConstraints();
                scrollPane = new JScrollPane(treeView);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.9;
                c.gridx = 3;
                c.gridy = 0;
                c.gridwidth = 2;
                c.gridheight = 4;
                panel.add(scrollPane, c);

                cT.fill = GridBagConstraints.HORIZONTAL;
                cT.weightx = 0.5;
                cT.gridx = 0;
                //cT.gridy = 0;
                cT.gridwidth = 1;
                cT.gridheight = 1;
                for(int i=0;i<15;i++){
                    cT.gridy = i;
                    treeView.add(new JLabel(" "), cT);
                }

                /* 
                addBirdButton.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent ae){
                        String campusFieldValue = birdCampusField.getText();
                        try{
                            int ID = Integer.parseInt(campusFieldValue);
                            
                            int index = Arrays.binarySearch(storedIDs, ID);
                            if(index >= 0){
                                JTree tree = campusTrees[index];
                                DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
                                DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
                                DefaultMutableTreeNode bird = new DefaultMutableTreeNode(birdNameField.getText());
                                root.add(bird);
        
                                model.reload(root);
                                birdResponse.setText("Bird: "+birdNameField.getText()+" added to campus: "+campusFieldValue);
        
                                
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
                */
                 
    }

}
