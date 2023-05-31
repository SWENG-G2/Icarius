package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;

import icarius.entities.Bird;

public class LocationForm extends BirdFieldForm{
    public LocationForm(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();
        
        //TODO - Ethan - change the size of the text field to whatever you think is appropriate
        textField = addTextField("Location:", bird.getLocation(), c);
    }
}
