package icarius.gui.tabs;



//import ch.qos.logback.classic.db.names.ColumnName;

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
        tabName = "Default";
    }

    public JPanel returnPanel(){
        return panel;
    }

    public String returnName(){
        return tabName;
    }


}
