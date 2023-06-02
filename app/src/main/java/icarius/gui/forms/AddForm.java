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

public class AddForm extends JPanel {
    private Campus campus;
    private JTextField nameField;

    private String[] birdFields = { "List Image", "Hero Image", "Sound", "About", "Video",
    "Location", "Location Image", "Diet", "Diet Image" };

    private MainBirdForm birdForm;

    // Add Campus Form
    public AddForm() {
        // Configure Layout
        GridBagConstraints c = configure();

        // Add Inputs
        nameField = addTextField("Campus Name:", c);

        // Add Buttons
        addCreateCampusButton(c);
    }

    // Add Bird Form
    public AddForm(Campus campus) {
        // Configure Layout
        GridBagConstraints c = configure();
        this.campus = campus;

        addBirdFields(campus, c);

        // Add Buttons
        addCreateBirdButton(c);
    }

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

    // Returns added textfield
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

    public void addBirdFields(Campus campus, GridBagConstraints c){
        // Add Inputs
        nameField = addTextField("Bird Name:", c);
        addComboBox("Section Select:", birdFields, c);
        birdForm = addBirdForm(birdFields[0], c);
    }

    //TODO - make this void, in editForm too
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

    private ActionListener formSelect(JComboBox<String> comboBox) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                birdForm.setForm((String) comboBox.getSelectedItem());
            }
        };
    }

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

    public void addCreateCampusButton(GridBagConstraints c) {
        c.gridx = 1;
        JButton createButton = new JButton("Create Campus");
        createButton.addActionListener(new ActionListener(){
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
                    if ( newCampus.create(App.userClient, null) ) {
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
                // TODO - (Connall) No Permission excemption
            }
        });
        add(createButton, c);
    }

    public void addCreateBirdButton(GridBagConstraints c) {
        c.gridx = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        JButton createButton = new JButton("Create Bird");
        createButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                MainFrame frame = (MainFrame) getTopLevelAncestor();
                MainTab mainTab = frame.getMainTab();
                String textFieldValue = nameField.getText();
                
                String[] fieldValues = {textFieldValue, birdForm.aboutForm.textArea.getText(),
                    birdForm.locationForm.textArea.getText(), birdForm.dietForm.textArea.getText(),
                    birdForm.listImageForm.UrlPath, birdForm.heroImageForm.UrlPath, birdForm.soundForm.UrlPath,
                    birdForm.videoForm.UrlPath, birdForm.locationImageForm.UrlPath, birdForm.dietImageFrom.UrlPath};

                // Confirm bird name textfield is not blank
                for (String fieldValue : fieldValues){
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

                Long cID = newBird.getCampusId();

                newBird.setName(textFieldValue);
                newBird.setCampusId(campus.getId());
                newBird.setAboutMe(birdForm.aboutForm.textArea.getText());
                newBird.setLocation(birdForm.locationForm.textArea.getText());
                newBird.setDiet(birdForm.dietForm.textArea.getText());

                String listImageUrlPath = uploadFile(App.userClient, cID, birdForm.listImageForm.UrlPath, "image", null);
                newBird.setListImageURL(listImageUrlPath);

                String heroImageUrlPath = uploadFile(App.userClient, cID, birdForm.heroImageForm.UrlPath, "image", null);
                newBird.setHeroImageURL(heroImageUrlPath);

                String soundURLPath = uploadFile(App.userClient, cID, birdForm.soundForm.UrlPath, "audio", null);
                newBird.setSoundURL(soundURLPath);

                String videoUrlPath = uploadFile(App.userClient, cID, birdForm.videoForm.UrlPath, "video", null);
                newBird.setAboutMeVideoURL(videoUrlPath);

                String locationImageUrlPath = uploadFile(App.userClient, cID, birdForm.locationImageForm.UrlPath, "image", null);
                newBird.setLocationImageURL(locationImageUrlPath);

                String dietImageUrlPath = uploadFile(App.userClient, cID, birdForm.dietImageFrom.UrlPath, "image", null);
                newBird.setDietImageURL(dietImageUrlPath);


                try {
                    if ( newBird.create(App.userClient, null) ) {
                        //TODO - Connall - make sure I've added all of this to the right place
                        // Success

                        frame.setNotification(newBird.getName() + " added to " + campus.getName(), null);
                        // Refresh tree
                        mainTab.refreshDatabaseTree(newBird);

                    

                    } else {
                        // Failure
                        frame.setNotification("Failed to add " + newBird.getName() + " to " + campus.getName() + "!", null);
                    }
                } catch (ConnectionException ce) {
                    frame.setNotification(ce.getMessage(), Color.RED);
                }
                // TODO - (Connall) No Permission excemption

            }
        });
        add(createButton, c);
    }
}
