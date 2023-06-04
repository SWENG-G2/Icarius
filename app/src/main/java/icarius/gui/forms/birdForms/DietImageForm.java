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

public class DietImageForm extends BirdFieldForm {
    private String DIET_IMAGE_NAME = "Diet Image";

    public DietImageForm(Bird bird, HashMap<String, String> changedParams) {
        super(changedParams);
        urlPath = bird.getDietImageURL();

        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.REMAINDER;

        uploadButton = addFileUploadField("Diet Image:", bird.getDietImageURL(), c, uploadDietImage());

        c.gridx = 0;
        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(getImage(DIET_IMAGE_NAME), c);
    }

    public DietImageForm() {
        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;

        uploadButton = addFileUploadField("Diet Image:", "", c, uploadDietImage());
    }

    public ActionListener uploadDietImage() {
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
                        if (label.getName() == DIET_IMAGE_NAME) {
                            remove(label);
                        }
                    }
                }

                // Upload Image
                File file = selectLocalFile("Image");
                if (file == null)
                    return;
                uploadButton.setText("File selected: " + file.getName());
                urlPath = file.getPath();

                if (changedParams != null)
                    changedParams.put("dietImageURL", urlPath);

                // Adds image to frame
                add(getImage(DIET_IMAGE_NAME), c);
            }
        };
    }
}
