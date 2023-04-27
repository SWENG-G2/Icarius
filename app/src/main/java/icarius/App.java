package icarius;

import icarius.auth.User;

import icarius.gui.Gui;
import okhttp3.OkHttpClient;

public class App {
    public static final String BASE_URL = "http://localhost:8080";

    public static void main(String[] args) {
        User user = new User(new OkHttpClient());
        Gui gui = new Gui(user);
    }
}
