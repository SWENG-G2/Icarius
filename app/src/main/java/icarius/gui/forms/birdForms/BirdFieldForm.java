package icarius.gui.forms.birdForms;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class BirdFieldForm extends JPanel{

    public JTextField textField;

    protected JButton UploadButton;

    public String UrlPath;

    public BirdFieldForm(){
    }

    protected GridBagConstraints configure() {
        // Configure layout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        c.ipadx = 8;
        c.ipady = 8;
        c.gridy = 0;
        return c;
    }

    // Returns added textfield
    protected JTextField addTextField(String labelText, String placeholderText, GridBagConstraints c) {
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        
        // Add Label
        add(new JLabel(labelText), c);

        // Add TextField
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField textField = new JTextField(placeholderText, 18);
        add(textField, c);

        // Increment y for next item
        c.gridy++;
        return textField;
    }

    protected JButton addFileUploadField(String labelText, String placeholderText, GridBagConstraints c, ActionListener al) {
        // Configure Layout
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        
        // Add Label
        add(new JLabel(labelText), c);

        // Add TextField
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        if (placeholderText == null || placeholderText.equals("")) placeholderText = "Upload a file";
        JButton button = new JButton(placeholderText);
        button.addActionListener(al);
        button.setPreferredSize(new Dimension(90, 20));
        add(button, c);

        // Increment y for next item
        c.gridy++;
        return button;
    }

}
