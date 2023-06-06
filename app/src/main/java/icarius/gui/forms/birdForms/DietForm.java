package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;
import java.util.HashMap;

import javax.swing.JScrollPane;

import icarius.entities.Bird;

public class DietForm extends BirdFieldForm {
    /**
     * diet form for editing exisiting bird
     * 
     * @param bird
     * @param changedParams
     */
    public DietForm(Bird bird, HashMap<String, String> changedParams) {
        super(changedParams);
        // Configure Layout
        GridBagConstraints c = configure();

        textArea = addTextArea("Diet:", bird.getAboutMe(), c);

        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane);
    }

    /**
     * diet form for creating new bird
     */
    public DietForm() {
        // Configure Layout
        GridBagConstraints c = configure();

        textArea = addTextArea("Diet:", "", c);

        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane);
    }

}
