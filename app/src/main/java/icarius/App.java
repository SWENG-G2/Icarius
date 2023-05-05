package icarius;

import icarius.gui.Gui;

public class App {
    public static final String BASE_URL = "http://localhost:8080";
    public Gui gui;

    public static void main(String[] args) {
        Gui gui = new Gui();
        gui.init();
    }
}
