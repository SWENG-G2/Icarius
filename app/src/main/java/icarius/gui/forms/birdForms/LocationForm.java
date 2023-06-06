package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;
import java.util.HashMap;

import javax.swing.JScrollPane;

import icarius.entities.Bird;

public class LocationForm extends BirdFieldForm {
    /**
     * location form for editing bird
     * 
     * @param bird
     * @param changedParams
     */
    public LocationForm(Bird bird, HashMap<String, String> changedParams) {
        super(changedParams);
        // Configure Layout
        GridBagConstraints c = configure();

        textArea = addTextArea("Location:", bird.getAboutMe(), c);

        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane);
    }

    /**
     * location form for creating new bird
     */
    public LocationForm() {
        // Configure Layout
        GridBagConstraints c = configure();

        textArea = addTextArea("Location:", "", c);

        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane);
    }
}
