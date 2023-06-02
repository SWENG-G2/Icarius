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

public class HeroImageForm extends BirdFieldForm{
    public HeroImageForm(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;

        UploadButton = addFileUploadField("Hero Image:", bird.getHeroImageURL(), c, uploadHeroImage());
    }

    public HeroImageForm(){
        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;

        UploadButton = addFileUploadField("Hero Image:", "", c, uploadHeroImage());
    }

    public ActionListener uploadHeroImage() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                // Configure Layout
                GridBagConstraints c = configure();
                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 2;
                c.fill = GridBagConstraints.HORIZONTAL;

                // Removes previous image
                String hero = "hero";
                for (Component label : getComponents()) {
                    if (label instanceof JLabel)
                    {
                        if (label.getName() == hero){
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
                JLabel heroImageLbl = new JLabel();
                heroImageLbl.setName(hero);
                BufferedImage buffImage = null;
                try {                    
                    buffImage = ImageIO.read(new File(file.getPath()));

                    Image image = buffImage.getScaledInstance(300, 200, Image.SCALE_DEFAULT);
                    heroImageLbl.setIcon(new ImageIcon(image));
                    add(heroImageLbl, c);
                } catch (Exception e) {
                }
            }
        };
    }
}
