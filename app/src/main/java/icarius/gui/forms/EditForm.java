package icarius.gui.forms;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import icarius.entities.Bird;
import icarius.entities.Campus;
import icarius.gui.panels.FormPanel;

public class EditForm extends JPanel {
    private FormPanel parent;

    // Campus Edit Page Fields
    private JTextField campusName;

    // Bird Edit Page Fields
    private JTextField birdNameField;
    private JTextField soundField;
    private JTextField aboutField;
    private JTextField locationField;
    private JTextField diet;

    private JButton listImageUploadButton;
    private JButton heroImageUploadButton;
    private JButton videoUploadButton;
    private JButton locationImageUploadButton;
    private JButton dietImageUploadButton;

    private String listImageUrlPath;
    private String heroImageUrlPath;
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
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        c.ipadx = 8;
        c.ipady = 8;
        c.gridy = 0;
        return c;
    }

    private void addCampusEditFields(Campus campus, GridBagConstraints c) {
        campusName = addTextField("Campus Name:", campus.getName(), c);
    }

    private void addBirdEditFields(Bird bird, GridBagConstraints c) {
        birdNameField = addTextField("Bird Name:", bird.getName(), c);
        listImageUploadButton = addImageUploadField("List Image:", bird.getListImageURL(), c, uploadListImage());
        heroImageUploadButton = addImageUploadField("Hero Image:", bird.getHeroImageURL(), c, uploadHeroImage());
        soundField = addTextField("Sound:", bird.getSoundURL(), c); // TODO - sound file upload
        aboutField = addTextField("About:", bird.getAboutMe(), c);
        videoUploadButton = addImageUploadField("Video:", bird.getAboutMeVideoURL(), c, uploadVideo());
        locationField = addTextField("Location:", bird.getLocation(), c);
        locationImageUploadButton = addImageUploadField("Location Image:", bird.getLocationImageURL(), c, uploadLocationImage());
        diet = addTextField("Diet:", bird.getDiet(), c);
        dietImageUploadButton = addImageUploadField("Diet Image:", bird.getDietImageURL(), c, uploadDietImage());
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

    private JButton addImageUploadField(String labelText, String placeholderText, GridBagConstraints c, ActionListener al) {
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        
        // Add Label
        add(new JLabel(labelText), c);

        // Add TextField
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        if (placeholderText == null) placeholderText = "Upload a file"; // TODO - figure out why this only works for first file upload button
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
                File file = fileUploader("Image");
                if (file == null) return;
                listImageUploadButton.setText("File selected: " + file.getName());
                listImageUrlPath = file.getPath();
            }
        };
    }

    public ActionListener uploadHeroImage() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file = fileUploader("Image");
                if (file == null) return;
                heroImageUploadButton.setText("File selected: " + file.getName());
                heroImageUrlPath = file.getPath();
            }
        };
    }

    public ActionListener uploadVideo() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file = fileUploader("Video");
                if (file == null) return;
                videoUploadButton.setText("File selected: " + file.getName());
                videoUrlPath = file.getPath();
            }
        };
    }

    public ActionListener uploadLocationImage() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file = fileUploader("Image");
                if (file == null) return;
                locationImageUploadButton.setText("File selected: " + file.getName());
                locationImageUrlPath = file.getPath();
            }
        };
    }

    public ActionListener uploadDietImage() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file = fileUploader("Image");
                if (file == null) return;
                dietImageUploadButton.setText("File selected: " + file.getName());
                dietImageUrlPath = file.getPath();
            }
        };
    }

    public File fileUploader(String type) {
        final JFileChooser fc = new JFileChooser();

        // Configure Filter
        FileNameExtensionFilter filter;
        switch (type) {
            case "Image":
                fc.setDialogTitle("Select an Image file");
                filter = new FileNameExtensionFilter("PNG, JPEG, JPG and GIF images", "png", "gif", "jpeg","JPG");
                break;
            case "Audio":
                fc.setDialogTitle("Select an Audio file");
                filter = new FileNameExtensionFilter("MP3, MP4 and WAV files", "MP3", "MP4", "WAV");
                break;
            case "Video":
                fc.setDialogTitle("Select a Video file");
                filter = new FileNameExtensionFilter("MP4, MOV, WMV and AVI files", "MP4", "MOV", "WMV","AVI");
                break;
            default:
                filter = null;
                break;
        }
        fc.addChoosableFileFilter(filter);

        // Return Chosen File
        return (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) ? fc.getSelectedFile() : null;

        // TODO - (HARRY) investigate following warning that appears when i click file upload button
        // WARNING: An illegal reflective access operation has occurred 
        // WARNING: Illegal reflective access by com.formdev.flatlaf.ui.FlatFileChooserUI$FlatShortcutsPanel (file:/C:/Users/Connall/.gradle/caches/modules-2/files-2.1/com.formdev/flatlaf/3.0/b5410f3f9137febc7d916ca4e0a7e9f6ddeb5b9a/flatlaf-3.0.jar) to method sun.awt.shell.ShellFolder.get(java.lang.String)
        // WARNING: Please consider reporting this to the maintainers of com.formdev.flatlaf.ui.FlatFileChooserUI$FlatShortcutsPanel
        // WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
        // WARNING: All illegal access operations will be denied in a future release
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
                if (o instanceof Campus) {
                    // TODO - save campus
                }
                if (o instanceof Bird) {
                    // TODO - save bird
                }
                // TODO - notification message
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
                // TODO - (CONNALL) consider use of interface polymorphism?

                // Remove from server
                if (o instanceof Campus) ((Campus) o).delete(parent.gui.user, null);
                if (o instanceof Bird) ((Bird) o).delete(parent.gui.user, null);

                // Refresh Tree
                parent.parent.refreshDatabaseTree();
                // TODO - No Permission message
                // TODO - Failed message
                // TODO - success message
            }
        });
        add(editButton, c);
    }
}
