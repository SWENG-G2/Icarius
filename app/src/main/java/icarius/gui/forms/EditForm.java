package icarius.gui.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

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

public class EditForm extends JPanel {
    // Campus Edit Page Fields
    private JTextField campusNameField;

    // Bird Edit Page Fields
    private JTextField birdNameField;

    private String[] birdFields = { "List Image", "Hero Image", "Sound", "About", "Video",
            "Location", "Location Image", "Diet", "Diet Image" };

    private MainBirdForm birdForm;

    /**
     * Edit page for campus and bird entities
     * 
     * @param o
     */
    public EditForm(Object o) {
        // Configure Layout
        GridBagConstraints c = configure();

        // Add Details
        if (o instanceof Campus) {
            addCampusEditFields((Campus) o, c);
        }

        if (o instanceof Bird) {
            addBirdEditFields((Bird) o, c);
        }

        addSaveButton(o, c);
        addDeleteButton(o, c);

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
     * Adds fields for editing a campus
     * 
     * @param campus
     * @param c
     */
    private void addCampusEditFields(Campus campus, GridBagConstraints c) {
        campusNameField = addTextField("Campus Name:", campus.getName(), c);
    }

    /**
     * Adds fields for editing a bird
     * 
     * @param bird
     * @param c
     */
    private void addBirdEditFields(Bird bird, GridBagConstraints c) {
        birdNameField = addTextField("Bird Name:", bird.getName(), c);
        addComboBox("Section Select:", birdFields, bird, c);
        birdForm = addBirdForm(bird, birdFields[0], c);
    }

    /**
     * Returns added textfield
     * 
     * @param labelText
     * @param placeholderText
     * @param c
     * @return JTextField textField
     */
    private JTextField addTextField(String labelText, String placeholderText, GridBagConstraints c) {
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;

        // Add Label
        add(new JLabel(labelText), c);

        // Add TextField
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField textField = new JTextField(placeholderText, 15);
        add(textField, c);

        // Increment y for next item
        c.gridy++;
        return textField;
    }

    /**
     * Returns form for editing bird information
     * 
     * @param bird
     * @param firstForm
     * @param c
     * @return MainBirdForm birdForm
     */
    private MainBirdForm addBirdForm(Bird bird, String firstForm, GridBagConstraints c) {
        // Configure Layout
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridwidth = 4;

        // Add MainBirdForm
        MainBirdForm birdForm = new MainBirdForm(bird, firstForm);
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
     * creates and adds combobox that controls which form of the edit bird form is
     * on screen
     * 
     * @param labelText
     * @param options
     * @param bird
     * @param c
     */
    private void addComboBox(String labelText, String[] options, Bird bird, GridBagConstraints c) {

        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;

        add(new JLabel(labelText), c);

        // Add ComboBox
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.addActionListener(formSelect(bird, comboBox));

        add(comboBox, c);

        // Increment y for next item
        c.gridy++;
    }

    /**
     * ActionListener for comboBox that controls which form of the edit bird form is
     * on screen
     * 
     * @param bird
     * @param comboBox
     * @return new ActionListener
     */
    private ActionListener formSelect(Bird bird, JComboBox<String> comboBox) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                birdForm.setForm((String) comboBox.getSelectedItem());
            }
        };
    }

    /**
     * Adds button to save changes made in edit manu
     * 
     * @param o
     * @param c
     */
    private void addSaveButton(Object o, GridBagConstraints c) {
        c.gridx = 0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        JButton editButton = new JButton("Save Changes");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                MainFrame frame = (MainFrame) getTopLevelAncestor();
                MainTab mainTab = frame.getMainTab();

                try {
                    if (o instanceof Campus) {
                        Campus c = (Campus) o;
                        String newCampusName = campusNameField.getText();

                        // Check if campus already exists with this name
                        if (App.db.getCampus(newCampusName) != null) {
                            frame.setNotification("Campus: '" + c.getName() + "' already exists!", Color.RED);
                        }

                        c.setName(campusNameField.getText());

                        // Send Update Campus Request
                        if (c.update(App.userClient, null)) {
                            // SUCCESS
                            frame.setNotification("Campus: '" + c.getName() + "' has been successfully updated.", null);
                        } else {
                            // FAILURE
                            frame.setNotification(
                                    "Campus: '" + c.getName() + "' failed to update, please contact an administrator.",
                                    Color.RED);
                        }
                    }
                    if (o instanceof Bird) {
                        Bird b = (Bird) o;
                        Long cID = b.getCampusId();

                        // If trying to update name to a name already existing in this campus
                        String newBirdName = birdNameField.getText();
                        if (!newBirdName.equals(b.getName())
                                && App.db.getCampusById(cID).getBird(newBirdName) != null) {
                            // Attempting to change bird name to existing bird
                            frame.setNotification(
                                    "Update failed, bird: '" + b.getName() + "' already exists in this campus!",
                                    Color.RED);
                            return;
                        }

                        // Update Bird entity with TextField Values
                        HashMap<String, String> changedParams = birdForm.getChangeParams();
                        changedParams.forEach((reqParameter, localFile) -> {
                            String fileUrl = uploadFile(App.userClient, cID, localFile, reqParameter, null);
                            if (fileUrl != null)
                                changedParams.put(reqParameter, fileUrl);
                            System.out.println(changedParams);

                        });

                        changedParams.put("name", newBirdName);
                        changedParams.put("aboutMe", birdForm.aboutForm.textArea.getText());
                        changedParams.put("location", birdForm.locationForm.textArea.getText());
                        changedParams.put("diet", birdForm.dietForm.textArea.getText());

                        // Send Update Bird Request
                        if (b.update(App.userClient, null)) {
                            // SUCCESS
                            frame.setNotification("Bird: '" + b.getName() + "' has been successfully updated.", null);
                        } else {
                            // FAILURE
                            frame.setNotification(
                                    "Bird: '" + b.getName() + "' failed to update, please contact an administrator.",
                                    Color.RED);
                        }
                    }
                    mainTab.refreshDatabaseTree(o);
                } catch (ConnectionException ce) {
                    frame.setNotification(ce.getMessage(), Color.RED);
                }
            }
        });
        add(editButton, c);
        c.gridwidth = 1;
    }

    /**
     * adds button which deletes selected bird or campus entity
     * 
     * @param o
     * @param c
     */
    private void addDeleteButton(Object o, GridBagConstraints c) {
        if (o instanceof Bird) {
            c.gridx = 3;
            c.gridy = 0;
        } else if (o instanceof Campus) {
            c.gridx = 0;
            c.gridy++;
        }
        c.gridwidth = GridBagConstraints.REMAINDER;

        JButton editButton = new JButton("Delete");

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                MainFrame frame = (MainFrame) getTopLevelAncestor();
                MainTab mainTab = frame.getMainTab();

                try {
                    if (o instanceof Campus) {
                        // Remove Campus from server
                        Campus c = (Campus) o;
                        if (c.delete(App.userClient, null)) {
                            // Success
                            frame.setNotification("Campus: '" + c.getName() + "' has been successfully removed.", null);
                        } else {
                            // Failure
                            frame.setNotification("Campus: Failed to remove '" + c.getName() + "' from server!", null);
                        }
                    }

                    if (o instanceof Bird) {
                        Bird b = (Bird) o;
                        if (b.delete(App.userClient, null)) {
                            // Success
                            frame.setNotification("Bird: '" + b.getName() + "' has been successfully removed.", null);
                        } else {
                            // Failure
                            frame.setNotification("Bird: Failed to remove '" + b.getName() + "' from server!", null);
                        }
                    }

                    // Refresh Tree
                    mainTab.refreshDatabaseTree(o);
                } catch (ConnectionException ce) {
                    frame.setNotification(ce.getMessage(), Color.RED);
                }
            }
        });

        add(editButton, c);
    }
}
