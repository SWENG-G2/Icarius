package icarius;

import icarius.gui.Gui;
import okhttp3.OkHttpClient;
import icarius.auth.UserClient;
import icarius.entities.Database;

public class App {
    public static final String BASE_URL = "http://localhost:8080";
    public static UserClient userClient;
    public static Database db;
    public Gui gui;

    public static void main(String[] args) {
        userClient = new UserClient(new OkHttpClient());
        Gui gui = new Gui();
    }
}
