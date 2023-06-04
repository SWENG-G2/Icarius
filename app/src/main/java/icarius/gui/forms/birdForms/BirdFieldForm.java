package icarius.gui.forms.birdForms;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;

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
    protected final HashMap<String, String> changedParams;

    public JTextField textField;

    public JTextArea textArea;

    protected JButton uploadButton;

    public String urlPath;

    public BirdFieldForm(HashMap<String, String> changedParams){
        this.changedParams = changedParams;
    }

    public BirdFieldForm() {
        this.changedParams = null;
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
        if (placeholderText == null || placeholderText.equals("")) placeholderText = "Upload a file";

        // Get File Name
        // String[] getFileName = placeholderText.split("/");
        // placeholderText = getFileName[getFileName.length - 1];
        placeholderText = placeholderText.replace(App.BASE_URL + "/", "");

        //c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
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
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                super.run();
                BufferedImage buffImage = null;
                try {      
                    //System.out.println("PATH BEFORE: " + UrlPath);         
                    // TODO - remove when using server
                    if (urlPath.contains("localhost")) {
                        urlPath = urlPath.replace("https://localhost:8080", App.PENELOPE_STORAGE);
                    }
        
                    if (urlPath.contains("http")) {
                        urlPath = urlPath.replace(" ", "%20");
                        buffImage = ImageIO.read(new URL(urlPath).openStream());
                    } else {
                        buffImage = ImageIO.read(new File(urlPath));
                    }   
                    //System.out.println("PATH AFTER: " + UrlPath);     
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (buffImage != null) {
                    Image image = buffImage.getScaledInstance(300, 200, Image.SCALE_DEFAULT);
                    imageLbl.setIcon(new ImageIcon(image));
                }
            }
        }.run();
        return imageLbl;
    }
}
