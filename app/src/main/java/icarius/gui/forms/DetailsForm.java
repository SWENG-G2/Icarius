package icarius.gui.forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import icarius.entities.Bird;
import icarius.entities.Campus;
import icarius.gui.panels.FormPanel;

public class DetailsForm extends JPanel {
    FormPanel parent;

    // Campus Details Page
    public DetailsForm(Object o, FormPanel parent) {
        // Configure Layout
        GridBagConstraints c = configure(parent);

        // Add Details
        if (o instanceof Campus) addCampusDetails((Campus) o, c);
        if (o instanceof Bird) addBirdDetails((Bird) o, c);

        // Edit Button
        addEditButton(o, c);
    }

    private GridBagConstraints configure(FormPanel parent) {
        // Configure layout
        this.parent = parent;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        return c;
    }

    private void addCampusDetails(Campus campus, GridBagConstraints c) {
        addInfoField("Campus Name:", campus.getName(), c);
    }

    private void addBirdDetails(Bird bird, GridBagConstraints c) {
        addInfoField("Bird Name:", bird.getName(), c);
        addInfoField("List Image:", bird.getListImageURL(), c);
        addInfoField("Hero Image:", bird.getHeroImageURL(), c);
        addInfoField("Sound:", bird.getSoundURL(), c);
        addInfoField("About:", bird.getAboutMe(), c);
        addInfoField("Video:", bird.getAboutMeVideoURL(), c);
        addInfoField("Location:", bird.getLocation(), c);
        addInfoField("Location Image:", bird.getLocationImageURL(), c);
        addInfoField("Diet:", bird.getDiet(), c);
        addInfoField("Diet Image:", bird.getDietImageURL(), c);
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
        add(new JLabel(information), c);

        // Increment y for next item
        c.gridy++;
    }

    private void addEditButton(Object o, GridBagConstraints c) {
        c.gridx = 1;
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                parent.setEditPage(o);
            }
        });
        add(editButton, c);
    }
}
