package icarius.gui.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;

import icarius.entities.Campus;
import icarius.gui.forms.AddForm;
import icarius.gui.forms.DetailsForm;
import icarius.gui.forms.EditForm;
import icarius.gui.frames.MainFrame;

public class FormPanel extends JPanel {
    private String WELCOME_TEXT = "Select an item to continue.";
    private String LOADING_TEXT = "Loading...";

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
        setLoading();
        removeAll();
        add(new EditForm(o));
        revalidate();
        repaint();
        endLoading();
    }

    public void setDetailsPage(Object o) {
        setLoading();
        removeAll();
        add(new DetailsForm(o));
        revalidate();
        repaint();
        endLoading();
    }

    public void setLoading() {
        MainFrame frame = (MainFrame) getTopLevelAncestor();
        frame.setNotification(LOADING_TEXT, null);
    }

    public void endLoading() {
        MainFrame frame = (MainFrame) getTopLevelAncestor();
        frame.setNotification(" ", null);
    }
}
