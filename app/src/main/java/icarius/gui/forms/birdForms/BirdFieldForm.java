package icarius.gui.forms.birdForms;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import icarius.App;

import javax.swing.JTextArea;

//Class to be inherited by the other bird forms, contrains functions which they all use
public abstract class BirdFieldForm extends JPanel{

    public JTextField textField;

    public JTextArea textArea;

    protected JButton UploadButton;

    public String UrlPath;

    public BirdFieldForm(){
    }

    protected GridBagConstraints configure() {
        // Configure layout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        c.ipadx = 8;
        c.ipady = 8;
        c.gridy = 0;
        return c;
    }

    // Returns added textfield
    protected JTextField addTextField(String labelText, String placeholderText, GridBagConstraints c) {
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        
        // Add Label
        add(new JLabel(labelText), c);

        // Add TextField
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField textField = new JTextField(placeholderText, 18);
        add(textField, c);

        // Increment y for next item
        c.gridy++;
        return textField;
    }

    protected JButton addFileUploadField(String labelText, String placeholderText, GridBagConstraints c, ActionListener al) {
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        
        // Add Label
        add(new JLabel(labelText), c);

        // Add TextField
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        if (placeholderText == null || placeholderText.equals("")) placeholderText = "Upload a file";
        JButton button = new JButton(placeholderText);
        button.addActionListener(al);
        button.setPreferredSize(new Dimension(105, 20));
        add(button, c);

        // Increment y for next item
        c.gridy++;
        return button;
    }

    // Returns added textArea
    protected JTextArea addTextArea(String labelText, String placeholderText, GridBagConstraints c) {
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;

        // Add Label
        add(new JLabel(labelText), c);

        // Add TextField
        c.gridy++;
        JTextArea textArea = new JTextArea(placeholderText, 15, 20);
        add(textArea, c);

        // Wraps text
        textArea.setLineWrap(true);

        return textArea;
    }
    

    /**
     * Create and return image at path specified by url image
     * @return
     */
    protected JLabel getImage(String imageName) {
        JLabel imageLbl = new JLabel();
        imageLbl.setName(imageName);
        BufferedImage buffImage = null;
        try {
            System.out.println(UrlPath);                    
            // TODO - remove when using server
            if (UrlPath.contains("localhost")) {
                String[] newPath = UrlPath.split("https://localhost:8080");
                String path = newPath[newPath.length - 1];
                UrlPath = App.PENELOPE_STORAGE + path;
                //UrlPath = UrlPath.replace("https://localhost:8080", App.PENELOPE_STORAGE);
            }
            System.out.println(UrlPath);
            buffImage = ImageIO.read(new File(UrlPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Image image = buffImage.getScaledInstance(300, 200, Image.SCALE_DEFAULT);
        imageLbl.setIcon(new ImageIcon(image));
        return imageLbl;
    }
}
