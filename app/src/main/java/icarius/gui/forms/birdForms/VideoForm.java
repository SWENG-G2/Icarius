package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import icarius.entities.Bird;

import static icarius.services.FileUploadService.*;

public class VideoForm extends BirdFieldForm{
    public VideoForm(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();

        //TODO - Ethan - add a video player here 

        UploadButton = addFileUploadField("Video:", bird.getAboutMeVideoURL(), c, uploadVideo());
    }

    public VideoForm(){
        // Configure Layout
        GridBagConstraints c = configure();

        //TODO - Ethan - add a video player here 

        UploadButton = addFileUploadField("Video:", "", c, uploadVideo());
    }

    public ActionListener uploadVideo() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file = selectLocalFile("Video");
                if (file == null) return;
                UploadButton.setText("File selected: " + file.getName());
                UrlPath = file.getPath();
            }
        };
    }
}
