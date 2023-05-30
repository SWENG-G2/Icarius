package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;

import icarius.entities.Bird;

public class AboutForm extends BirdFieldForm{
    public AboutForm(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();

        textField = addTextField("About:", bird.getAboutMe(), c);
    }
}
