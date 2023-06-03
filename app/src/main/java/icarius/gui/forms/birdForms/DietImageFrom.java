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


public class DietImageFrom extends BirdFieldForm{
    public DietImageFrom(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;

        UploadButton = addFileUploadField("Diet Image:", bird.getDietImageURL(), c, uploadDietImage());
    }

    public DietImageFrom(){
        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;

        UploadButton = addFileUploadField("Diet Image:", "", c, uploadDietImage());
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
                String diet = "diet";
                for (Component label : getComponents()) {
                    if (label instanceof JLabel)
                    {
                        if (label.getName() == diet){
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
                JLabel dietImageLbl = new JLabel();
                dietImageLbl.setName(diet);
                BufferedImage buffImage = null;
                try {                    
                    buffImage = ImageIO.read(new File(UrlPath));

                    Image image = buffImage.getScaledInstance(300, 200, Image.SCALE_DEFAULT);
                    dietImageLbl.setIcon(new ImageIcon(image));
                    add(dietImageLbl, c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
