package icarius.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import icarius.entities.User;
import icarius.gui.forms.UserForm;

public class UserInfoPanel extends JPanel {
    private GridBagConstraints c;

    /**
     * Panel which displays information on selected user - used in UsersTab
     */
    public UserInfoPanel() {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        add(new JLabel("Select an item to continue."), c);
    }

    /**
     * Shows information on selected user
     * @param user
     */
    public void setUserInfoPage(User user) {
        removeAll();
        add(new UserForm(user), c);
        revalidate();
        repaint();
    }

}
