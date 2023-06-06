package icarius.gui.forms.birdForms;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JLabel;

import icarius.entities.Bird;

import static icarius.services.FileUploadService.*;

public class HeroImageForm extends BirdFieldForm {
    private String HERO_IMAGE_NAME = "Hero Image";

    /**
     * hero image form for editing exisiting bird
     * 
     * @param bird
     * @param changedParams
     */
    public HeroImageForm(Bird bird, HashMap<String, String> changedParams) {
        super(changedParams);
        urlPath = bird.getHeroImageURL();

        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.REMAINDER;

        uploadButton = addFileUploadField("Hero Image:", bird.getHeroImageURL(), c, uploadHeroImage());

        c.gridx = 0;
        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(getImage(HERO_IMAGE_NAME), c);
    }

    /**
     * hero image form for creating new bird
     */
    public HeroImageForm() {
        super(null);
        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;

        uploadButton = addFileUploadField("Hero Image:", "", c, uploadHeroImage());
    }

    /**
     * action listener to get selected picture from local files
     * 
     * @return new ActionListener
     */
    public ActionListener uploadHeroImage() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                // Configure Layout
                GridBagConstraints c = configure();
                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 2;
                c.fill = GridBagConstraints.HORIZONTAL;

                // Removes previous image
                for (Component label : getComponents()) {
                    if (label instanceof JLabel) {
                        if (label.getName() == HERO_IMAGE_NAME) {
                            remove(label);
                        }
                    }
                }

                // Upload Image
                File file = selectLocalFile("Image");
                if (file == null)
                    return;
                uploadButton.setText(getUploadedFileText(file.getName()));
                urlPath = file.getPath();

                if (changedParams != null)
                    changedParams.put("heroImageURL", urlPath);

                // Adds image to frame
                add(getImage(HERO_IMAGE_NAME), c);
            }
        };
    }
}
