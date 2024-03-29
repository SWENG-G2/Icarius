package icarius.gui.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import icarius.App;
import icarius.entities.Bird;
import icarius.entities.Campus;
import icarius.gui.Gui;
import icarius.gui.frames.MainFrame;
import icarius.gui.tabs.MainTab;
import icarius.http.ConnectionException;

import static icarius.services.FileUploadService.*;
import static icarius.services.FileDetails.*;

public class AddForm extends JPanel {
    private Campus campus;
    private JTextField nameField;

    private String[] birdFields = { "List Image", "Hero Image", "Sound", "About", "Video",
            "Location", "Location Image", "Diet", "Diet Image" };

    private MainBirdForm birdForm;

    /**
     * Form for adding a campus entity
     */
    public AddForm() {
        // Configure Layout
        GridBagConstraints c = configure();

        // Add Inputs
        nameField = addTextField("Campus Name:", c);

        // Add Buttons
        addCreateCampusButton(c);
    }

    /**
     * form for adding a bird entity
     * 
     * @param campus
     */
    public AddForm(Campus campus) {
        // Configure Layout
        GridBagConstraints c = configure();
        this.campus = campus;

        addBirdFields(campus, c);

        // Add Buttons
        addCreateBirdButton(c);
    }

    /**
     * Configures gridBagConstraints
     * 
     * @return GridBagConstraints c
     */
    private GridBagConstraints configure() {
        // Configure layout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        c.ipadx = 8;
        c.ipady = 8;
        c.gridy = 0;
        return c;
    }

    /**
     * Returns added text field
     * 
     * @param labelText
     * @param c
     * @return JTextField textField
     */
    private JTextField addTextField(String labelText, GridBagConstraints c) {
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;

        // Add Label
        add(new JLabel(labelText), c);

        // Add TextField
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField textField = new JTextField(18);
        add(textField, c);

        // Increment y for next item
        c.gridy++;
        return textField;
    }

    /**
     * Returns form for adding information for new bird
     * Returns added
     * 
     * @param campus
     * @param c
     */
    public void addBirdFields(Campus campus, GridBagConstraints c) {
        // Add Inputs
        nameField = addTextField("Bird Name:", c);
        addComboBox("Section Select:", birdFields, c);
        birdForm = addBirdForm(birdFields[0], c);
    }

    /**
     * creates and adds combobox that controls which form of the create bird form is
     * on screen
     * 
     * @param labelText
     * @param options
     * @param c
     */
    private void addComboBox(String labelText, String[] options, GridBagConstraints c) {

        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;

        add(new JLabel(labelText), c);

        // Add ComboBox
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.addActionListener(formSelect(comboBox));

        add(comboBox, c);

        // Increment y for next item
        c.gridy++;
    }

    /**
     * ActionListener for comboBox that controls which form of the add bird form is
     * on screen
     * 
     * @param comboBox
     * @return new ActionListener
     */
    private ActionListener formSelect(JComboBox<String> comboBox) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                birdForm.setForm((String) comboBox.getSelectedItem());
            }
        };
    }

    /**
     * Adds panel to display bird attributes
     * 
     * @param firstForm - first attribute form to display
     * @param c         - Grid Bag Constraints
     * @return MainBirdForm Panel containing attribute panels
     */
    private MainBirdForm addBirdForm(String firstForm, GridBagConstraints c) {
        // Configure Layout
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridwidth = 4;

        // Add MainBirdForm
        MainBirdForm birdForm = new MainBirdForm(firstForm);
        int height = (int) (Gui.MAIN_FRAME_Y_SIZE / 1.79);
        birdForm.setPreferredSize(new Dimension(this.getWidth(), height));
        add(birdForm, c);

        // Resets gridwidth for next item
        c.gridwidth = 1;
        // Increment y for next item
        c.gridy++;

        return birdForm;
    }

    /**
     * Adds button to create campus in server with given details
     * 
     * @param c
     */
    public void addCreateCampusButton(GridBagConstraints c) {
        c.gridx = 1;
        JButton createButton = new JButton("Create Campus");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                MainFrame frame = (MainFrame) getTopLevelAncestor();
                MainTab mainTab = frame.getMainTab();
                String textFieldValue = nameField.getText();

                // Confirm campus name textfield is not blank
                if (textFieldValue.isBlank()) {
                    frame.setNotification("Campus name field can not be blank!", Color.RED);
                    return;
                }

                // Confirm campus name does not already exists
                if (App.db.getCampus(textFieldValue) != null) {
                    frame.setNotification("Campus name " + textFieldValue + " already exists!", Color.RED);
                    return;
                }

                Campus newCampus = new Campus(App.userClient);
                newCampus.setName(textFieldValue);
                try {
                    if (newCampus.create(App.userClient, null)) {
                        // Success
                        frame.setNotification(newCampus.getName() + " added to campus list.", null);
                        // Refresh Tree
                        mainTab.refreshDatabaseTree(newCampus);
                    } else {
                        // Failure
                        frame.setNotification("Failed to add " + newCampus.getName() + " to campus list!", null);
                    }

                } catch (ConnectionException ce) {
                    frame.setNotification(ce.getMessage(), Color.RED);
                }
            }
        });
        add(createButton, c);
    }

    /**
     * Adds button to create bird in server with given details
     * 
     * @param c
     */
    public void addCreateBirdButton(GridBagConstraints c) {
        c.gridx = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        JButton createButton = new JButton("Create Bird");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                MainFrame frame = (MainFrame) getTopLevelAncestor();
                MainTab mainTab = frame.getMainTab();
                String textFieldValue = nameField.getText();

                String[] fieldValues = { textFieldValue, birdForm.aboutForm.textArea.getText(),
                        birdForm.locationForm.textArea.getText(), birdForm.dietForm.textArea.getText(),
                        birdForm.listImageForm.urlPath, birdForm.heroImageForm.urlPath, birdForm.soundForm.urlPath,
                        birdForm.videoForm.urlPath, birdForm.locationImageForm.urlPath,
                        birdForm.dietImageFrom.urlPath };

                // Confirm bird name textfield is not blank
                for (String fieldValue : fieldValues) {
                    if (fieldValue.isBlank()) {
                        frame.setNotification("Please fill in all of the fields", Color.RED);
                        return;
                    }
                }

                if (textFieldValue.isBlank()) {
                    frame.setNotification("Bird name field can not be blank!", Color.RED);
                    return;
                }

                // Confirm campus name does not already exists
                if (campus.getBird(textFieldValue) != null) {
                    frame.setNotification("Bird name " + textFieldValue + " already exists in this campus!", Color.RED);
                    return;
                }

                Bird newBird = new Bird(App.userClient);

                newBird.setName(textFieldValue);
                newBird.setCampusId(campus.getId());
                newBird.setAboutMe(birdForm.aboutForm.textArea.getText());
                newBird.setLocation(birdForm.locationForm.textArea.getText());
                newBird.setDiet(birdForm.dietForm.textArea.getText());

                Long cID = newBird.getCampusId();

                String listImageUrlPath = uploadFile(App.userClient, cID, birdForm.listImageForm.urlPath,
                        LIST_IMAGE_URL, null);
                newBird.setListImageURL(listImageUrlPath);

                String heroImageUrlPath = uploadFile(App.userClient, cID, birdForm.heroImageForm.urlPath,
                        HERO_IMAGE_URL, null);
                newBird.setHeroImageURL(heroImageUrlPath);

                String soundURLPath = uploadFile(App.userClient, cID, birdForm.soundForm.urlPath, SOUND_URL, null);
                newBird.setSoundURL(soundURLPath);

                String videoUrlPath = uploadFile(App.userClient, cID, birdForm.videoForm.urlPath, VIDEO_URL, null);
                newBird.setAboutMeVideoURL(videoUrlPath);

                String locationImageUrlPath = uploadFile(App.userClient, cID, birdForm.locationImageForm.urlPath,
                        LOCATION_IMAGE_URL, null);
                newBird.setLocationImageURL(locationImageUrlPath);

                String dietImageUrlPath = uploadFile(App.userClient, cID, birdForm.dietImageFrom.urlPath,
                        DIET_IMAGE_URL, null);
                newBird.setDietImageURL(dietImageUrlPath);

                try {
                    if (newBird.create(App.userClient, null)) {
                        // Success
                        frame.setNotification(newBird.getName() + " added to " + campus.getName(), null);
                        // Refresh tree
                        mainTab.refreshDatabaseTree(campus);
                    } else {
                        // Failure
                        frame.setNotification("Failed to add " + newBird.getName() + " to " + campus.getName() + "!",
                                null);
                    }
                } catch (ConnectionException ce) {
                    frame.setNotification(ce.getMessage(), Color.RED);
                }

            }
        });
        add(createButton, c);
    }
}
