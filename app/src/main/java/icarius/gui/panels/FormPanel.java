package icarius.gui.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;

import icarius.entities.Campus;
import icarius.gui.forms.AddForm;
import icarius.gui.forms.DetailsForm;
import icarius.gui.forms.EditForm;

public class FormPanel extends JPanel {

    /**
     * Panel used in MainTab which displays the add, edit and details forms for both campus and bird
     */
    public FormPanel() {        
        JPanel welcomePanel = new JPanel();
        welcomePanel.add(new JLabel("Select an item to continue."));
        add(welcomePanel);
    }


    /**
     * Creates and adds the addForm for campuses 
     */
    public void setAddCampus() {
        removeAll();
        add(new AddForm());
        revalidate();
        repaint();
    }

     /**
     * Creates and adds the addForm for birds 
     */
    public void setAddBird(Campus campus) {
        removeAll();
        add(new AddForm(campus));
        revalidate();
        repaint();
    }

    /**
     * Creates and adds the editForm for either birds or campuses 
     */
    public void setEditPage(Object o) {
        removeAll();
        add(new EditForm(o));
        revalidate();
        repaint();
    }

    /**
     * Creates and adds the detailsForm for either birds or campuses 
     */
    public void setDetailsPage(Object o) {
        removeAll();
        add(new DetailsForm(o));
        revalidate();
        repaint();
    }
}
