package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;

import icarius.entities.Bird;

public class LocationForm extends BirdFieldForm{
    public LocationForm(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();

        textField = addTextField("Location:", bird.getLocation(), c);
    }
}
