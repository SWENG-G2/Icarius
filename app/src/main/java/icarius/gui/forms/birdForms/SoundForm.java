package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import icarius.entities.Bird;

import static icarius.services.FileUploadService.*;

public class SoundForm extends BirdFieldForm{
    public SoundForm(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();

        //TODO - Ethan - add an audio player here

        UploadButton = addFileUploadField("Sound:", bird.getSoundURL(), c, uploadAudio());
    }

    public SoundForm(){
        // Configure Layout
        GridBagConstraints c = configure();

        //TODO - Ethan - add an audio player here

        UploadButton = addFileUploadField("Sound:", "", c, uploadAudio());
    }

    public ActionListener uploadAudio() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file = selectLocalFile("Audio");
                if (file == null) return;
                UploadButton.setText("File selected: " + file.getName());
                UrlPath = file.getPath();
            }
        };
    }
}
