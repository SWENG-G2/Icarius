package icarius.gui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import icarius.App;
import icarius.entities.Campus;
import icarius.entities.User;
import icarius.gui.frames.MainFrame;
import icarius.gui.panels.UserInfoPanel;


public class UserForm extends JPanel{
    private User user;
    private GridBagConstraints c;
    public UserForm(User user){
        this.user = user;
        // Configure Layout
        c = configure();
        
        addInfoField("Username:", user.getUsername(), c);
        addInfoField("Admin:", user.getAdmin() ? "Yes" : "No", c);
        if(user.getAdmin()){
            addInfoField("Accessible Campuses:", "All", c);
        } else{
            addRemoveCampus(c);
            addAddCampus(c);
            accessableCampuses("Accessible Campuses:", user.getCampusPermissions(), c);
        }
    }

    private GridBagConstraints configure() {
        // Configure layout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 8;
        c.ipady = 8;
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

    private void addAddCampus(GridBagConstraints c) {
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;
        
        // Add Label
        add(new JLabel("Add campus"), c);

        // Add combo box
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;

        List<Campus> unaccessedCampuses = getUnaccesibleCampuses();
        Object[] array = unaccessedCampuses.toArray();

        JComboBox<Object> comboBox = new JComboBox<>(array);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                Campus selectedCampus = (Campus) comboBox.getSelectedItem();
                MainFrame frame = (MainFrame) getTopLevelAncestor();
                if (user.addCampus(selectedCampus.getId(), null)) {
                    // If added
                    frame.setNotification("Added Campus " + selectedCampus + " to user " + user.getUsername() + "'s permissions", null);
                    user.addPermission(selectedCampus);
                } else {
                    // If failed to add
                    frame.setNotification("Failed to add Campus " + selectedCampus + " to user " + user.getUsername() + "'s permissions", null);
                }

                refresh();
            }
        });
        add(comboBox, c);
        c.gridy++;
    }

    private void addRemoveCampus(GridBagConstraints c){
        c.gridx = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;

        // Add Campus ComboBox
        add(new JLabel("Remove campus"), c);

        // Add combo box
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx++;

        List<Campus> unaccessedCampuses = user.getCampusPermissions();
        Object[] array = unaccessedCampuses.toArray();

        JComboBox<Object> comboBox = new JComboBox<>(array);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                Campus selectedCampus = (Campus) comboBox.getSelectedItem();
                MainFrame frame = (MainFrame) getTopLevelAncestor();
                if (user.removeCampus(selectedCampus.getId(), null)) {
                    // If removed
                    frame.setNotification("Removed Campus " + selectedCampus + " from user " + user.getUsername() + "'s permissions", null);
                    user.removePermission(selectedCampus);
                } else {
                    // If failed to remove
                    frame.setNotification("Failed to remove Campus " + selectedCampus + " from user " + user.getUsername() + "'s permissions", null);
                }

                refresh();
            }
        });
        add(comboBox, c);
        c.gridy++;
    }

    private List<Campus> getUnaccesibleCampuses() {
        List<Campus> unaccessibleCampuses = new ArrayList<>();

        for (Campus campus : App.db.getDatabase()) {
            // If campus not in user campus permissions
            if (!user.getCampusPermissions().contains(campus)) {
                unaccessibleCampuses.add(campus);
            }
        }

        return unaccessibleCampuses;
    }

    private void refresh(){
        UserInfoPanel parent = (UserInfoPanel) getParent();
        parent.setUserInfoPage(user);
    }

}