package icarius.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import okhttp3.OkHttpClient;
import icarius.auth.User;
import icarius.gui.panels.FooterPanel;
import icarius.gui.panels.LoginPanel;
import icarius.gui.panels.MainPanel;
import icarius.http.ConnectionFailedInterceptor;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class Gui {
    public User user;

    // Main Frame
    public JFrame mainFrame;
    public static final int MAIN_FRAME_X_SIZE=600;
    public static final int MAIN_FRAME_Y_SIZE=500;

    // Main Frame Components
    public LoginPanel loginPanel;
    public MainPanel mainPanel;
    public FooterPanel footerPanel;

    public Gui(){
        // Instantiate Client with connection interceptor
        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new ConnectionFailedInterceptor(this))
            .build();
        user = new User(client);

        // Configure gui
        configureFlatLat();
    }

    public void init() {
        // Initialise Main Frame
        mainFrame = new JFrame();
        mainFrame.setTitle("Icarius");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(MAIN_FRAME_X_SIZE, MAIN_FRAME_Y_SIZE);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);

        // Initialise Frame Components
        loginPanel = new LoginPanel(this);
        mainPanel = new MainPanel(this);

        // Add Footer
        footerPanel = new FooterPanel(this);

        // Open Login Screen
        openLoginPanel();
        mainFrame.add(footerPanel, BorderLayout.SOUTH);
        mainFrame.validate();
    }

    public void openMainPanel() {
        // Switch Active Panel
        mainFrame.remove(loginPanel);
        mainFrame.add(mainPanel);
        mainFrame.validate();

        // Configure Footer
        footerPanel.showLogOutButton();
        footerPanel.resetNotification();
    }

    public void openLoginPanel() {
            // Switch Active Panel
            mainFrame.remove(mainPanel);
            mainFrame.add(loginPanel);
            mainFrame.validate();

            // Reset login fields
            loginPanel.resetForm();

            // Configure Footer
            footerPanel.hideLogOutButton();
            footerPanel.resetNotification();
    }

    private void configureFlatLat() {
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
    }
}

