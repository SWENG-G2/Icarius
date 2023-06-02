package icarius.gui.forms.birdForms;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import icarius.entities.Bird;

import static icarius.services.FileUploadService.*;

public class LocationImageForm extends BirdFieldForm{
    public LocationImageForm(Bird bird){
            // Configure Layout
            GridBagConstraints c = configure();
            c.gridx = 2;
            c.gridy = 0;

            UploadButton = addFileUploadField("Location Image:", bird.getLocationImageURL(), c, uploadLocationImage());
    }

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
                String location = "location";
                for (Component label : getComponents()) {
                    if (label instanceof JLabel)
                    {
                        if (label.getName() == location){
                            remove(label);
                        }
                    }
                }

                //Upload Image
                File file = selectLocalFile("Image");
                if (file == null) return;
                UploadButton.setText("File selected: " + file.getName());
                UrlPath = file.getPath();


                // Adds image to frame
                JLabel locationImageLbl = new JLabel();
                locationImageLbl.setName(location);
                BufferedImage buffImage = null;
                try {                    
                    buffImage = ImageIO.read(new File(file.getPath()));

                    Image image = buffImage.getScaledInstance(300, 200, Image.SCALE_DEFAULT);
                    locationImageLbl.setIcon(new ImageIcon(image));
                    add(locationImageLbl, c);
                } catch (Exception e) {
                }
            }
        };
    }
}
