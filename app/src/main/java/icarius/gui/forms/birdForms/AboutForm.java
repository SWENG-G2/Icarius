package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import icarius.entities.Bird;

public class AboutForm extends BirdFieldForm{
    public AboutForm(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();

        JTextArea textArea = new JTextArea();
        textArea = addTextArea("About:", bird.getAboutMe(), c);

        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane);
    }
}
