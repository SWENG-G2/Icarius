package icarius.gui.forms;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import icarius.auth.User;
import icarius.entities.Bird;
import icarius.entities.Campus;
import icarius.gui.panels.FormPanel;
import static icarius.services.FileUploadService.*;

public class EditForm extends JPanel {
    private FormPanel parent;
    private User user;

    // Campus Edit Page Fields
    private JTextField campusNameField;

    // Bird Edit Page Fields
    private JTextField birdNameField;
    private JTextField aboutField;
    private JTextField locationField;
    private JTextField dietField;

    private JButton listImageUploadButton;
    private JButton heroImageUploadButton;
    private JButton soundUploadButton;
    private JButton videoUploadButton;
    private JButton locationImageUploadButton;
    private JButton dietImageUploadButton;

    private String listImageUrlPath;
    private String heroImageUrlPath;
    private String soundURLPath;
    private String videoUrlPath;
    private String locationImageUrlPath;
    private String dietImageUrlPath;

    // Edit Page
    public EditForm(Object o, FormPanel parent) {
        // Configure Layout
        GridBagConstraints c = configure(parent);

        // Add Details
        if (o instanceof Campus) addCampusEditFields((Campus) o, c);
        if (o instanceof Bird) addBirdEditFields((Bird) o, c);

        // Add Cancel/Save Buttons
        addCancelButton(o, c);
        addSaveButton(o, c);
        addDeleteButton(o, c);
    }

    private GridBagConstraints configure(FormPanel parent) {
        // Configure layout
        this.parent = parent;
        this.user = parent.gui.user;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        c.ipadx = 8;
        c.ipady = 8;
        c.gridy = 0;
        return c;
    }

    private void addCampusEditFields(Campus campus, GridBagConstraints c) {
        campusNameField = addTextField("Campus Name:", campus.getName(), c);
    }

    private void addBirdEditFields(Bird bird, GridBagConstraints c) {
        birdNameField = addTextField("Bird Name:", bird.getName(), c);
        listImageUploadButton = addFileUploadField("List Image:", bird.getListImageURL(), c, uploadListImage());
        heroImageUploadButton = addFileUploadField("Hero Image:", bird.getHeroImageURL(), c, uploadHeroImage());
        soundUploadButton = addFileUploadField("Sound:", bird.getSoundURL(), c, uploadAudio());
        aboutField = addTextField("About:", bird.getAboutMe(), c);
        videoUploadButton = addFileUploadField("Video:", bird.getAboutMeVideoURL(), c, uploadVideo());
        locationField = addTextField("Location:", bird.getLocation(), c);
        locationImageUploadButton = addFileUploadField("Location Image:", bird.getLocationImageURL(), c, uploadLocationImage());
        dietField = addTextField("Diet:", bird.getDiet(), c);
        dietImageUploadButton = addFileUploadField("Diet Image:", bird.getDietImageURL(), c, uploadDietImage());
    }

    // Returns added textfield
    private JTextField addTextField(String labelText, String placeholderText, GridBagConstraints c) {
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        
        // Add Label
        add(new JLabel(labelText), c);

        // Add TextField
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField textField = new JTextField(placeholderText, 18);
        add(textField, c);

        // Increment y for next item
        c.gridy++;
        return textField;
    }

    private JButton addFileUploadField(String labelText, String placeholderText, GridBagConstraints c, ActionListener al) {
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        
        // Add Label
        add(new JLabel(labelText), c);

        // Add TextField
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        if (placeholderText == null || placeholderText.equals("")) placeholderText = "Upload a file";
        JButton button = new JButton(placeholderText);
        button.addActionListener(al);
        button.setPreferredSize(new Dimension(90, 20));
        add(button, c);

        // Increment y for next item
        c.gridy++;
        return button;
    }

    public ActionListener uploadListImage() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file = selectLocalFile("Image");
                if (file == null) return;
                listImageUploadButton.setText("File selected: " + file.getName());
                listImageUrlPath = file.getPath();
            }
        };
    }

    public ActionListener uploadHeroImage() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file = selectLocalFile("Image");
                if (file == null) return;
                heroImageUploadButton.setText("File selected: " + file.getName());
                heroImageUrlPath = file.getPath();
            }
        };
    }

    public ActionListener uploadAudio() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file = selectLocalFile("Audio");
                if (file == null) return;
                soundUploadButton.setText("File selected: " + file.getName());
                soundURLPath = file.getPath();
            }
        };
    }

    public ActionListener uploadVideo() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file = selectLocalFile("Video");
                if (file == null) return;
                videoUploadButton.setText("File selected: " + file.getName());
                videoUrlPath = file.getPath();
            }
        };
    }

    public ActionListener uploadLocationImage() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file = selectLocalFile("Image");
                if (file == null) return;
                locationImageUploadButton.setText("File selected: " + file.getName());
                locationImageUrlPath = file.getPath();
            }
        };
    }

    public ActionListener uploadDietImage() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file = selectLocalFile("Image");
                if (file == null) return;
                dietImageUploadButton.setText("File selected: " + file.getName());
                dietImageUrlPath = file.getPath();
            }
        };
    }

    private void addCancelButton(Object o, GridBagConstraints c) {
        c.gridx = 0;
        JButton editButton = new JButton("Cancel");
        editButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                parent.setDetailsPage(o);
            }
        });
        add(editButton, c);
    }

    private void addSaveButton(Object o, GridBagConstraints c) {
        c.gridx = 1;
        JButton editButton = new JButton("Save Changes");
        editButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                // TODO - (CONNALL) make campus and bird update methods return boolean
                // TODO - success/failure notification messages below
                if (o instanceof Campus) {
                    Campus campus = (Campus) o;
                    campus.setName(campusNameField.getText());
                    campus.update(user, null);
                }
                if (o instanceof Bird) {
                    Bird bird = (Bird) o;
                    Long cID = bird.getCampusId();

                    // Update Bird with TextField Values
                    bird.setName(birdNameField.getText());
                    bird.setAboutMe(aboutField.getText());
                    bird.setLocation(locationField.getText());
                    bird.setDiet(dietField.getText());

                    // Upload Files, then update Bird File Path
                    listImageUrlPath = uploadFile(user, cID, listImageUrlPath, "image", null);
                    bird.setListImageURL(listImageUrlPath);

                    heroImageUrlPath = uploadFile(user, cID, heroImageUrlPath, "image", null);
                    bird.setHeroImageURL(heroImageUrlPath);

                    soundURLPath = uploadFile(user, cID, soundURLPath, "audio", null);
                    bird.setSoundURL(soundURLPath);
                    
                    videoUrlPath = uploadFile(user, cID, videoUrlPath, "video", null);
                    bird.setAboutMeVideoURL(videoUrlPath);
                    
                    locationImageUrlPath = uploadFile(user, cID, locationImageUrlPath, "image", null);
                    bird.setLocationImageURL(locationImageUrlPath);
                    
                    dietImageUrlPath = uploadFile(user, cID, dietImageUrlPath, "image", null);
                    bird.setDietImageURL(dietImageUrlPath);

                    bird.update(parent.gui.user, null);
                }
                parent.parent.refreshDatabaseTree();
            }
        });
        add(editButton, c);
    }

    private void addDeleteButton(Object o, GridBagConstraints c) {
        c.gridx = 1;
        c.gridy++;
        JButton editButton = new JButton("Delete");
        editButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                if (o instanceof Campus) {
                    // Remove Campus from server
                    Campus c = (Campus) o;
                    if ( c.delete(user, null) ) {
                        // Success
                        parent.gui.footerPanel.setNotification("Campus: '" + c.getName() + "' has been successfully removed.", null);
                    } else {
                        // Failure
                        parent.gui.footerPanel.setNotification("Campus: Failed to remove '" + c.getName() + "' from server!", null);
                    }
                }
                if (o instanceof Bird) {
                    Bird b = (Bird) o;
                    String campusName = parent.parent.database.getCampusById(b.getCampusId()).getName();
                    if ( b.delete(user, null) ) {
                        // Success
                        parent.gui.footerPanel.setNotification("Bird: '" + b.getName() + "' has been successfully removed from " + campusName, null);
                    } else {
                        // Failure
                        parent.gui.footerPanel.setNotification("Bird: Failed to remove '" + b.getName() + "' from server!", null);
                    }         
                }

                // Refresh Tree
                parent.parent.refreshDatabaseTree();
                // TODO - (Connall) No Permission message - create in http package and set notification
                // TODO - (Connall) No Connection message - create in http package and set notification
            }
        });
        add(editButton, c);
    }
}
