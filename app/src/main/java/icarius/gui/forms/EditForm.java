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
import icarius.gui.panels.FormPanel;
import icarius.gui.tabs.MainTab;
import icarius.http.ConnectionException;

import static icarius.services.FileUploadService.*;
public class EditForm extends JPanel{
        // Campus Edit Page Fields
        private JTextField campusNameField;

        // Bird Edit Page Fields
        private JTextField birdNameField;
    
        private String listImageUrlPath;
        private String heroImageUrlPath;
        private String soundURLPath;
        private String videoUrlPath;
        private String locationImageUrlPath;
        private String dietImageUrlPath;

        private String[] birdFields = {"List Image", "Hero Image", "Sound", "About", "Video",
                                        "Location", "Location Image", "Diet", "Diet Image"};

        private JComboBox formSelect;
    
        private MainBirdForm birdForm;

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
            formSelect = addComboBox("Section Select:", birdFields, bird, c);
            birdForm = addBirdForm(bird, birdFields[0], c);

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

        private MainBirdForm addBirdForm(Bird bird, String firstForm, GridBagConstraints c) {
            // Configure Layout
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridwidth = 3;

            // Add MainBirdForm
            MainBirdForm birdForm = new MainBirdForm(bird, firstForm);
            int height = (int)(Gui.MAIN_FRAME_Y_SIZE / 1.79);
            birdForm.setPreferredSize(new Dimension(this.getWidth(), height));
            add(birdForm, c);

            // Resets gridwidth for next item
            c.gridwidth = 1;
            // Increment y for next item
            c.gridy++;

            return birdForm;
        }


        private JComboBox addComboBox(String labelText, String[] options, Bird bird, GridBagConstraints c) {

            // Configure Layout
            c.fill = GridBagConstraints.NONE;
            c.gridx = 0;

            add(new JLabel(labelText), c);

            // Add ComboBox
            c.gridx++;
            c.fill = GridBagConstraints.HORIZONTAL;
            
            JComboBox comboBox = new JComboBox<>(options);
            comboBox.addActionListener(formSelect(bird, comboBox));

            add(comboBox, c);

            // Increment y for next item
            c.gridy++;
            return comboBox;
        }

        private ActionListener formSelect(Bird bird, JComboBox comboBox){
            return new ActionListener() {
                public void actionPerformed(ActionEvent ae){
                    birdForm.setForm(bird, (String)comboBox.getSelectedItem());
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

                    listImageUrlPath = birdForm.listImageUrlPath;
                    heroImageUrlPath = birdForm.heroImageUrlPath;
                    soundURLPath = birdForm.soundURLPath;
                    videoUrlPath = birdForm.videoUrlPath;
                    locationImageUrlPath = birdForm.locationImageUrlPath;
                    dietImageUrlPath = birdForm.dietImageUrlPath;

                    
                    try {
                        if (o instanceof Campus) {
                            Campus c = (Campus) o;
                            c.setName(campusNameField.getText());
    
                            // Send Update Campus Request
                            if (c.update(App.userClient, null)) {
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
                            b.setAboutMe(birdForm.aboutField.getText());
                            b.setLocation(birdForm.locationField.getText());
                            b.setDiet(birdForm.dietField.getText());
    
                            // If file uploaded, Upload Files, then update Bird entity File Path
                            if (listImageUrlPath != null) {
                                listImageUrlPath = uploadFile(App.userClient, cID, listImageUrlPath, "image", null);
                                b.setListImageURL(listImageUrlPath);
                            }
    
                            if (heroImageUrlPath != null) {
                                heroImageUrlPath = uploadFile(App.userClient, cID, heroImageUrlPath, "image", null);
                                b.setHeroImageURL(heroImageUrlPath);
                            }
    
                            if (soundURLPath != null) {
                                soundURLPath = uploadFile(App.userClient, cID, soundURLPath, "audio", null);
                                b.setSoundURL(soundURLPath);
                            }
                            
                            if (videoUrlPath != null) {
                                videoUrlPath = uploadFile(App.userClient, cID, videoUrlPath, "video", null);
                                b.setAboutMeVideoURL(videoUrlPath);
                            }
                            
                            if (locationImageUrlPath != null) {
                                locationImageUrlPath = uploadFile(App.userClient, cID, locationImageUrlPath, "image", null);
                                b.setLocationImageURL(locationImageUrlPath);
                            }
                            
                            if (dietImageUrlPath != null) {
                                dietImageUrlPath = uploadFile(App.userClient, cID, dietImageUrlPath, "image", null);
                                b.setDietImageURL(dietImageUrlPath);
                            }
    
                            // Send Update Bird Request
                            if (b.update(App.userClient, null)) {
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
            if (o instanceof Bird){
                c.gridx = 3;
                c.gridy=0;
            }else if (o instanceof Campus){
                c.gridx = 1;
                c.gridy++;
            }
            
            JButton editButton = new JButton("Delete");
    
            editButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae) {
                    MainFrame frame = (MainFrame) getTopLevelAncestor();
                    MainTab mainTab = frame.getMainTab();
                    
                    try {
                        if (o instanceof Campus) {
                            // Remove Campus from server
                            Campus c = (Campus) o;
                            if ( c.delete(App.userClient, null) ) {
                                // Success
                                frame.setNotification("Campus: '" + c.getName() + "' has been successfully removed.", null);
                            } else {
                                // Failure
                                frame.setNotification("Campus: Failed to remove '" + c.getName() + "' from server!", null);
                            }
                        }
    
                        if (o instanceof Bird) {
                            Bird b = (Bird) o;
                            if ( b.delete(App.userClient, null) ) {
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

