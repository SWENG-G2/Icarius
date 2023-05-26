package icarius.gui.forms;

import java.awt.Color;
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

import icarius.auth.UserClient;
import icarius.entities.Bird;
import icarius.entities.Campus;
import icarius.gui.frames.MainFrame;
import icarius.gui.panels.FormPanel;
import icarius.gui.tabs.MainTab;
import icarius.http.ConnectionException;

import static icarius.services.FileUploadService.*;

public class EditForm extends JPanel {
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
    public EditForm(Object o) {
        // Configure Layout
        GridBagConstraints c = configure();

        // Add Details
        if (o instanceof Campus) addCampusEditFields((Campus) o, c);
        if (o instanceof Bird) addBirdEditFields((Bird) o, c);

        // Add Cancel/Save Buttons
        addCancelButton(o, c);
        addSaveButton(o, c);
        addDeleteButton(o, c);
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
                ((FormPanel) getParent()).setDetailsPage(o);
            }
        });
        add(editButton, c);
    }

    private void addSaveButton(Object o, GridBagConstraints c) {
        c.gridx = 1;
        JButton editButton = new JButton("Save Changes");
        editButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                MainFrame frame = (MainFrame) getTopLevelAncestor();
                MainTab mainTab = frame.getMainTab();
                UserClient user = frame.getUser();
                try {
                    if (o instanceof Campus) {
                        Campus c = (Campus) o;
                        c.setName(campusNameField.getText());

                        // Send Update Campus Request
                        if (c.update(user, null)) {
                            // SUCCESS
                            frame.setNotification("Campus: '" + c.getName() + "' has been successfully updated.", null);
                        } else {
                            // FAILURE
                            frame.setNotification("Campus: '" + c.getName() + "' failed to update, please contact an administrator.", null);
                        }
                    }
                    if (o instanceof Bird) {
                        Bird b = (Bird) o;
                        Long cID = b.getCampusId();

                        // Update Bird entity with TextField Values
                        b.setName(birdNameField.getText());
                        b.setAboutMe(aboutField.getText());
                        b.setLocation(locationField.getText());
                        b.setDiet(dietField.getText());

                        // If file uploaded, Upload Files, then update Bird entity File Path
                        if (listImageUrlPath != null) {
                            listImageUrlPath = uploadFile(user, cID, listImageUrlPath, "image", null);
                            b.setListImageURL(listImageUrlPath);
                        }

                        if (heroImageUrlPath != null) {
                            heroImageUrlPath = uploadFile(user, cID, heroImageUrlPath, "image", null);
                            b.setHeroImageURL(heroImageUrlPath);
                        }

                        if (soundURLPath != null) {
                            soundURLPath = uploadFile(user, cID, soundURLPath, "audio", null);
                            b.setSoundURL(soundURLPath);
                        }
                        
                        if (videoUrlPath != null) {
                            videoUrlPath = uploadFile(user, cID, videoUrlPath, "video", null);
                            b.setAboutMeVideoURL(videoUrlPath);
                        }
                        
                        if (locationImageUrlPath != null) {
                            locationImageUrlPath = uploadFile(user, cID, locationImageUrlPath, "image", null);
                            b.setLocationImageURL(locationImageUrlPath);
                        }
                        
                        if (dietImageUrlPath != null) {
                            dietImageUrlPath = uploadFile(user, cID, dietImageUrlPath, "image", null);
                            b.setDietImageURL(dietImageUrlPath);
                        }

                        // Send Update Bird Request
                        if (b.update(user, null)) {
                            // SUCCESS
                            frame.setNotification("Bird: '" + b.getName() + "' has been successfully updated.", null);
                        } else {
                            // FAILURE
                            frame.setNotification("Bird: '" + b.getName() + "' failed to update, please contact an administrator.", null);
                        }
                    }
                    mainTab.refreshDatabaseTree();
                } catch (ConnectionException ce) {
                    frame.setNotification(ce.getMessage(), Color.RED);
                }
                // TODO - (Connall) No Permission excemption
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
                MainFrame frame = (MainFrame) getTopLevelAncestor();
                MainTab mainTab = frame.getMainTab();
                UserClient user = frame.getUser();
                
                try {
                    if (o instanceof Campus) {
                        // Remove Campus from server
                        Campus c = (Campus) o;
                        if ( c.delete(user, null) ) {
                            // Success
                            frame.setNotification("Campus: '" + c.getName() + "' has been successfully removed.", null);
                        } else {
                            // Failure
                            frame.setNotification("Campus: Failed to remove '" + c.getName() + "' from server!", null);
                        }
                    }

                    if (o instanceof Bird) {
                        Bird b = (Bird) o;
                        if ( b.delete(user, null) ) {
                            // Success
                            frame.setNotification("Bird: '" + b.getName() + "' has been successfully removed.", null);
                        } else {
                            // Failure
                            frame.setNotification("Bird: Failed to remove '" + b.getName() + "' from server!", null);
                        }         
                    }

                    // Refresh Tree
                    mainTab.refreshDatabaseTree();
                } catch (ConnectionException ce) {
                    frame.setNotification(ce.getMessage(), Color.RED);
                }
                // TODO - (Connall) No Permission excemption
            }
        });

        add(editButton, c);
    }
}
