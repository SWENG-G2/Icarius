package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;
import java.util.HashMap;

import javax.swing.JScrollPane;

import icarius.entities.Bird;

public class DietForm extends BirdFieldForm{
    public DietForm(Bird bird, HashMap<String, String> changedParams){
        super(changedParams);
        // Configure Layout
        GridBagConstraints c = configure();
        
        textArea = addTextArea("Diet:", bird.getAboutMe(), c);

        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane);
    }

    public DietForm(){
        // Configure Layout
        GridBagConstraints c = configure();
        
        textArea = addTextArea("Diet:", "", c);

        JScrollPane scrollpane = new JScrollPane(textArea);
        add(scrollpane);
    }

}
