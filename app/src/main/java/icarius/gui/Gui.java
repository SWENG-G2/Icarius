package icarius.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import icarius.gui.frames.LoginFrame;

import javax.swing.UIManager;

public class Gui {
    // Main Frame Properties
    public static final int MAIN_FRAME_X_SIZE=600;
    public static final int MAIN_FRAME_Y_SIZE=500;

    public Gui(){
        // Configure GUI
        // GUI Theme
        FlatMacDarkLaf.setup();

        // Component Properties
        UIManager.put("Button.arc", 5);
        UIManager.put("Button.innerFocusWidth", 0);
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Component.innerFocusWidth", 0);
        UIManager.put("Component.arrowType", "chevron");
        UIManager.put("TabbedPane.showTabSeparators", true);
        UIManager.put("ScrollBar.showButtons", true);
        UIManager.put("Tree.showsRootHandles", true);
        UIManager.put("Tree.wideSelection",false);
        UIManager.put("Tree.paintLines", true);

        // Open Login Frame
        new LoginFrame(null);
    }
}

