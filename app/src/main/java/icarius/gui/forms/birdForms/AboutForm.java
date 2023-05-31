package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;

import icarius.entities.Bird;

public class AboutForm extends BirdFieldForm{
    public AboutForm(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();
        //TODO - Ethan - change the size of the text field to whatever you think is appropriate
        textField = addTextField("About:", bird.getAboutMe(), c);
    }
}
