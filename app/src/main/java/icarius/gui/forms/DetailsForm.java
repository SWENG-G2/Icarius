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

    /**
     * Details page for selected campus or bird entity
     * @param o
     */
    public DetailsForm(Object o) {
        // Configure Layout
        GridBagConstraints c = configure();

        // Add Details
        if (o instanceof Campus) addCampusDetails((Campus) o, c);
        if (o instanceof Bird) addBirdDetails((Bird) o, c);

        // Edit Button
        addEditButton(o, c);
    }


    /**
     * Configures gridBagConstraints
     * @return GridBagConstraints c
     */
    private GridBagConstraints configure() {
        // Configure layout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        return c;
    }

    /**
     * Adds details for selected campus
     * @param campus
     * @param c
     */
    private void addCampusDetails(Campus campus, GridBagConstraints c) {
        addInfoField("Campus Name:", campus.getName(), c);
    }

    /**
     * Adds details for selected bird
     * @param bird
     * @param c
     */
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


    /**
     * adds label and information text for individual information field
     * @param labelText
     * @param information
     * @param c
     */
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


    /**
     * creates and adds button to edit selected entity
     * @param o
     * @param c
     */
    private void addEditButton(Object o, GridBagConstraints c) {
        c.gridx = 1;
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                ((FormPanel) getParent()).setEditPage(o);
            }
        });
        add(editButton, c);
    }
}
