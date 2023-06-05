package icarius.gui.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;

import icarius.entities.Campus;
import icarius.gui.forms.AddForm;
import icarius.gui.forms.DetailsForm;
import icarius.gui.forms.EditForm;

public class FormPanel extends JPanel {
    private String WELCOME_TEXT = "Select an item to continue.";

    public FormPanel() {        
        JPanel welcomePanel = new JPanel();
        welcomePanel.add(new JLabel(WELCOME_TEXT));
        add(welcomePanel);
    }

    public void setAddCampus() {
        removeAll();
        add(new AddForm());
        revalidate();
        repaint();
    }

    public void setAddBird(Campus campus) {
        removeAll();
        add(new AddForm(campus));
        revalidate();
        repaint();
    }

    public void setEditPage(Object o) {
        removeAll();
        add(new EditForm(o));
        revalidate();
        repaint();
    }

    public void setDetailsPage(Object o) {
        removeAll();
        add(new DetailsForm(o));
        revalidate();
        repaint();
    }
}
