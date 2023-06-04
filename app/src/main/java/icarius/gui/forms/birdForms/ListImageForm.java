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


public class ListImageForm extends BirdFieldForm{
    private String LIST_IMAGE_NAME = "List Image";
    
    public ListImageForm(Bird bird, HashMap<String, String> changedParams){
        super(changedParams);
        urlPath = bird.getListImageURL();

        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.REMAINDER;

        uploadButton = addFileUploadField("List Image:", urlPath, c, uploadListImage());

        c.gridx = 0;
        c.gridy++;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(getImage(LIST_IMAGE_NAME), c);               
    }

    public ListImageForm(){
        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;

        uploadButton = addFileUploadField("List Image:", "", c, uploadListImage());               
    }

    public ActionListener uploadListImage() {
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
                        if (label.getName() == LIST_IMAGE_NAME){
                            remove(label);
                        }
                    }
                }

                //Upload Image
                File file = selectLocalFile("Image");
                if (file == null) return;
                uploadButton.setText("File selected: " + file.getName());
                urlPath = file.getPath();


                if (changedParams != null)
                    changedParams.put("listImageURL", urlPath);

                // Adds image to frame
                add(getImage(LIST_IMAGE_NAME), c);
            }
        };
    }
}
