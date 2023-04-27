package icarius.gui.tabs;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

//TODO - Harry - when key tab is finished put any code that repeats across the tab items here
public abstract class Tab {
    
    protected JPanel panel;
    protected GridBagConstraints c; // c = constraints
    protected String tabName;
    
    public Tab(){
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        tabName = "Default"; //tabName is set at the start of each tab which is displayed in the gui (not subTabMain)
    }

    //returns the panel so that the "tabs" can be added to the JTabbedPane
    public JPanel returnPanel(){
        return panel;
    }

    
    public String returnName(){
        return tabName;
    }


}
