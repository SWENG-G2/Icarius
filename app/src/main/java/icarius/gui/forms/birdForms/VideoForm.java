package icarius.gui.forms.birdForms;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import icarius.entities.Bird;

import static icarius.services.FileUploadService.*;

public class VideoForm extends BirdFieldForm{
    public VideoForm(Bird bird, HashMap<String, String> changedParams){
        super(changedParams);
        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.REMAINDER;

        uploadButton = addFileUploadField("Video:", bird.getAboutMeVideoURL(), c, uploadVideo());
    }

    public VideoForm(){
        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;

        uploadButton = addFileUploadField("Video:", "", c, uploadVideo());
    }

    public ActionListener uploadVideo() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                // Configure Layout
                GridBagConstraints c = configure();
                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 2;
                c.fill = GridBagConstraints.HORIZONTAL;

                // Removes previous thumbnail
                String video = "video";
                for (Component label : getComponents()) {
                    if (label instanceof JLabel)
                    {
                        if (label.getName() == video){
                            remove(label);
                        }
                    }
                }

                // Upload file
                File file = selectLocalFile("Video");
                if (file == null) return;
                uploadButton.setText("File selected: " + file.getName());
                urlPath = file.getPath();
                
                if (changedParams != null)
                    changedParams.put("aboutMeVideoURL", urlPath);

                // Displays video thumbnail
                JLabel thumbnailLbl = new JLabel();
                thumbnailLbl.setName(video);
                try {
                    BufferedImage thumbnailBuff = null;
                    FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(urlPath);
                    grabber.start();

                    Java2DFrameConverter converter = new Java2DFrameConverter();

                    //Grabs a key frame from the first 50
                    for (int i = 0 ; i < 50 ; i++){
                        org.bytedeco.javacv.Frame frame = grabber.grabKeyFrame();
                        if (converter.convert(frame) == null) {
                            break;
                        } else {
                            thumbnailBuff = converter.convert(frame);
                        }
                    }
                    
                    Image image = thumbnailBuff.getScaledInstance(300, 200, Image.SCALE_DEFAULT);
                    thumbnailLbl.setIcon(new ImageIcon(image));
                    add(thumbnailLbl, c);

                    grabber.close();
                    converter.close();
                } catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
