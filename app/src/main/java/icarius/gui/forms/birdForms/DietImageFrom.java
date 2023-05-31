package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import icarius.entities.Bird;

import static icarius.services.FileUploadService.*;


public class DietImageFrom extends BirdFieldForm{
    public DietImageFrom(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();

        //TODO - Ethan - Display the image here, possibly in a JScrollPane if you think the images might take up too much space but preferrably not

        UploadButton = addFileUploadField("Diet Image:", bird.getDietImageURL(), c, uploadDietImage());
    }

    public ActionListener uploadDietImage() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file = selectLocalFile("Image");
                if (file == null) return;
                UploadButton.setText("File selected: " + file.getName());
                UrlPath = file.getPath();
            }
        };
    }
}
