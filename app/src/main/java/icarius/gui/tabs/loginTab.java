package icarius.gui.tabs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.Dimension;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

public class loginTab extends Tab{
    public JTextField usernameField;
    private JPasswordField keyField;
    public JButton loginButton;
    private JLabel response;
    public loginTab(){
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        // adding labels which won't need to change later
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Username:"), c);

        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Key:"), c);

        // adding any buttons, labels, or text fields which need variables for later
        usernameField = new JTextField("");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.9;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(usernameField, c);

        keyField = new JPasswordField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.9;
        c.gridx = 1;
        c.gridy = 2;
        panel.add(keyField, c);

        loginButton = new JButton("Login");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 3;
        panel.add(loginButton, c);

        response = new JLabel("");
        response.setForeground(Color.RED);
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(response, c);
    }

    public void invalidLogin(){
        response.setText("               Username or key incorrect");
        keyField.setText("");
    }

    public String getKey(){
        return String.valueOf(keyField.getPassword());
    }

    public void reset(){
        response.setText("");
        keyField.setText("");
        usernameField.setText("");
    }
}
