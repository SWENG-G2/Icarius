package icarius.gui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;

import icarius.entities.Campus;
import icarius.entities.User;
import icarius.gui.panels.UserInfoPanel;


public class UserForm extends JPanel{
    private User user;
    private GridBagConstraints c;
    public UserForm(User user){
        // Configure Layout
        c = configure();
        
        addInfoField("Username:", user.getUsername(), c);
        addInfoField("Admin:", user.getAdmin() ? "Yes" : "No", c);
        if(user.getAdmin()){
            addInfoField("Accessible Campuses:", "All", c);
        } else{
            //addComboBox(user, c);
            accessableCampuses("Accessible Campuses:", user.getCampusPermissions(), c);
        }
    }

    private GridBagConstraints configure() {
        // Configure layout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        return c;
    }

    private void addInfoField(String labelText, String information, GridBagConstraints c) {
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        c.ipadx = 8;
        c.ipady = 8;
        c.gridx = 0;
        
        // Add Label
        add(new JLabel(labelText), c);

        // Add TextField
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;

        // Cut information to length if necessary
        if (information != null && information.length() > 40) {
            String text = information.substring(0, 40);
            information = "..." + text;
        };
        add(new JLabel(information), c);

        // Increment y for next item
        c.gridy++;
    }

    private void accessableCampuses(String labelText, List<Campus> campuses, GridBagConstraints c) {
        //TODO - Connall - you said I didn't need to put this in a scroll pane as each non admin
        //       user would only have access to so many campuses. If you've changed your mind
        //       let me know and I'll add one in (or you can, or just make the whole thing extend scroll pane)
        
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        c.ipadx = 8;
        c.ipady = 8;
        c.gridx = 0;
        
        // Add Label
        add(new JLabel(labelText), c);

        // Add TextField
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;

        for (Campus campus : campuses){
            String campusName = campus.getName();
            // Cut information to length if necessary
            if (campusName != null && campusName.length() > 40) {
                String text = campusName.substring(0, 40);
                campusName = "..." + text;
            };
            add(new JLabel(campusName), c);
            // Increment y for next item
            c.gridy++;
        }
    }

    private void addComboBox(User user, GridBagConstraints c){
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        c.ipadx = 8;
        c.ipady = 8;
        c.gridx = 0;
        
        // Add Label
        add(new JLabel("Add campus"), c);

        // Add combo box
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;

        String[] unaccessedCampuses = {};
        //TODO - unaccessedCampuses array should be all of the campuses which the user does not have access to
        JComboBox addComboBox = new JComboBox<>(unaccessedCampuses);
        addComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                String selectedCampus = (String)addComboBox.getSelectedItem();
                //TODO - add the selected campus to the users list of accessable campuses
                refresh();
            }
        });
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Remove campus"), c);
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;

        String[] accessableCampuses = {};
        //TODO - campuses array should be all of the campuses which the user does not have access to
        JComboBox removeComboBox = new JComboBox<>(accessableCampuses);
        removeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                String selectedCampus = (String)removeComboBox.getSelectedItem();
                //TODO - remove the selected campus from the users list of accessable campuses
                refresh();
            }
        });
        c.gridy++;
    }

    private void refresh(){
        UserInfoPanel parent = (UserInfoPanel) getParent();
        parent.setUserInfoPage(user);
    }

}
