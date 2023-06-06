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


public class ListImageForm extends BirdFieldForm{
    
    /**
     * list image form for editing exisiting bird
     * @param bird
     */
    public ListImageForm(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;


        UploadButton = addFileUploadField("List Image:", bird.getListImageURL(), c, uploadListImage());               
    }

    /**
     * list image form for creating new bird
     */
    public ListImageForm(){
        // Configure Layout
        GridBagConstraints c = configure();
        c.gridx = 2;
        c.gridy = 0;


        UploadButton = addFileUploadField("List Image:", "", c, uploadListImage());               
    }


    /**
     * action listener to get selected picture from local files
     * @return new ActionListener
     */
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
                String list = "list";
                for (Component label : getComponents()) {
                    if (label instanceof JLabel)
                    {
                        if (label.getName() == list){
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
                JLabel listImageLbl = new JLabel();
                listImageLbl.setName(list);
                BufferedImage buffImage = null;
                try {                    
                    buffImage = ImageIO.read(new File(file.getPath()));

                    Image image = buffImage.getScaledInstance(300, 200, Image.SCALE_DEFAULT);
                    listImageLbl.setIcon(new ImageIcon(image));
                    add(listImageLbl, c);
                } catch (Exception e) {
                }
            }
        };
    }
}
