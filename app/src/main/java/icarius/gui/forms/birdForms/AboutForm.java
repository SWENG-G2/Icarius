package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;
import java.util.HashMap;

import javax.swing.JScrollPane;

import icarius.entities.Bird;

public class AboutForm extends BirdFieldForm {
    /**
     * about form for editing exisiting bird
     * 
     * @param bird
     * @param changedParams
     */
    public AboutForm(Bird bird, HashMap<String, String> changedParams) {
        super(changedParams);

        // Configure Layout
        GridBagConstraints c = configure();

        textArea = addTextArea("About:", bird.getAboutMe(), c);

        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane);
    }

    /**
     * about form for creating new bird
     */
    public AboutForm() {
        // Configure Layout
        GridBagConstraints c = configure();

        textArea = addTextArea("About:", "", c);

        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane);
    }
}
