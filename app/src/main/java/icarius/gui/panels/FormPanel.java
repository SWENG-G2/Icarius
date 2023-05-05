package icarius.gui.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;

import icarius.entities.Campus;
import icarius.gui.Gui;
import icarius.gui.forms.AddForm;
import icarius.gui.forms.DetailsForm;
import icarius.gui.forms.EditForm;
import icarius.gui.tabs.MainTab;

public class FormPanel extends JPanel {
    public Gui gui;
    public MainTab parent;
    public JPanel welcomePanel;

    public FormPanel(Gui gui, MainTab parent) {
        this.gui = gui;
        this.parent = parent;
        
        welcomePanel = new JPanel();
        welcomePanel.add(new JLabel("Select an item to continue."));
        add(welcomePanel);
    }

    public void setAddCampus() {
        removeAll();
        add(new AddForm(this));
        revalidate();
        repaint();
    }

    public void setAddBird(Campus campus) {
        removeAll();
        add(new AddForm(this, campus));
        revalidate();
        repaint();
    }

    public void setEditPage(Object o) {
        removeAll();
        add(new EditForm(o, this));
        revalidate();
        repaint();
    }

    public void setDetailsPage(Object o) {
        removeAll();
        add(new DetailsForm(o, this));
        revalidate();
        repaint();
    }
}
