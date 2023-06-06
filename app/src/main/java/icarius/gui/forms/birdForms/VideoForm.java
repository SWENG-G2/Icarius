package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import icarius.entities.Bird;

import static icarius.services.FileUploadService.*;

public class VideoForm extends BirdFieldForm{

    /**
     * video form for editing bird
     * @param bird
     */
    public VideoForm(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();

        //TODO - Ethan - add a video player here 

        UploadButton = addFileUploadField("Video:", bird.getAboutMeVideoURL(), c, uploadVideo());
    }

    /**
     * location form for creating new bird
     */
    public VideoForm(){
        // Configure Layout
        GridBagConstraints c = configure();

        //TODO - Ethan - add a video player here 

        UploadButton = addFileUploadField("Video:", "", c, uploadVideo());
    }


    /**
     * action listener to get selected video from local files
     * @return new ActionListener
     */
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
