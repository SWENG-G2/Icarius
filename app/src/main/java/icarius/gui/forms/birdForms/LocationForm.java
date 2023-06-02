package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import icarius.entities.Bird;

public class LocationForm extends BirdFieldForm{
    public LocationForm(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();
        
        JTextArea textArea = new JTextArea();
        textArea = addTextArea("Location:", bird.getAboutMe(), c);

        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane);
    }
}
