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

public class LocationImageForm extends BirdFieldForm{
    private String LOCATION_IMAGE_NAME = "Location Image";
    
    /**
     * location image form for editing bird
     * @param bird
     * @param changedParams
     */
    public LocationImageForm(Bird bird, HashMap<String, String> changedParams){
        super(changedParams);
        urlPath = bird.getLocationImageURL();
        
        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.REMAINDER;

        uploadButton = addFileUploadField("Location Image:", bird.getLocationImageURL(), c, uploadLocationImage());
    
        c.gridx = 0;
        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(getImage(LOCATION_IMAGE_NAME), c);  
    }

    /**
     * location form for creating new bird
     */
    public LocationImageForm(){
        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;

        uploadButton = addFileUploadField("Location Image:", "", c, uploadLocationImage());
    }

    /**
     * action listener to get selected picture from local files
     * @return new ActionListener
     */
    public ActionListener uploadLocationImage() {
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
                    if (label instanceof JLabel)
                    {
                        if (label.getName() == LOCATION_IMAGE_NAME){
                            remove(label);
                        }
                    }
                }

                //Upload Image
                File file = selectLocalFile("Image");
                if (file == null) return;
                uploadButton.setText(getUploadedFileText(file.getName()));
                urlPath = file.getPath();

                
                if (changedParams != null)
                    changedParams.put("locationImageURL", urlPath);

                // Adds image to frame
                add(getImage(LOCATION_IMAGE_NAME), c);
            }
        };
    }
}
