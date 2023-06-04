package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;
import java.util.HashMap;

import javax.swing.JScrollPane;

import icarius.entities.Bird;

public class LocationForm extends BirdFieldForm{
    public LocationForm(Bird bird, HashMap<String, String> changedParams){
        super(changedParams);
        // Configure Layout
        GridBagConstraints c = configure();
        
        textArea = addTextArea("Location:", bird.getAboutMe(), c);

        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane);
    }

    public LocationForm(){
        // Configure Layout
        GridBagConstraints c = configure();
        
        textArea = addTextArea("Location:", "", c);

        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane);
    }
}
