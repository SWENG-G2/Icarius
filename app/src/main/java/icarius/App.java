package icarius;

import icarius.gui.Gui;
import okhttp3.OkHttpClient;

public class App {
    public static final String BASE_URL = "http://localhost:8080";
    public Gui gui;

    public static void main(String[] args) {
        Gui gui = new Gui(new OkHttpClient());
        gui.init();
    }
}
