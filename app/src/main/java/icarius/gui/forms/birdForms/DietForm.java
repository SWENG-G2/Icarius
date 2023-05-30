package icarius.gui.forms.birdForms;

import java.awt.GridBagConstraints;

import icarius.entities.Bird;

public class DietForm extends BirdFieldForm{
    public DietForm(Bird bird){
        // Configure Layout
        GridBagConstraints c = configure();

        textField = addTextField("Diet:", bird.getDiet(), c);
    }

}
