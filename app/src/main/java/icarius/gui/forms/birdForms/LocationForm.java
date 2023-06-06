package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;

import javax.swing.JScrollPane;

import icarius.entities.Bird;

public class LocationForm extends BirdFieldForm{
    /**
     * location form for editing bird
     * @param bird
     */
    public LocationForm(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();
        
        textArea = addTextArea("Location:", bird.getAboutMe(), c);

        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane);
    }

    /**
     * loction form for creating new bird
     */
    public LocationForm(){
        // Configure Layout
        GridBagConstraints c = configure();
        
        textArea = addTextArea("Location:", "", c);

        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane);
    }
}
