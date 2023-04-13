package icarius;

import okhttp3.OkHttpClient;

import java.util.List;
import icarius.auth.Credentials;
import icarius.auth.User;

import icarius.entities.Campus;
import icarius.gui.Gui;

public class App {
    public static final String BASE_URL = "http://localhost:8080";
    public User user;
    public final OkHttpClient okHttpClient;

    public App() {
        Credentials credentials = new Credentials("sysadmin", "sysadmin");
        this.okHttpClient = new OkHttpClient();
        this.user = new User(credentials, new OkHttpClient());
    }

    public static void main(String[] args) {
        App app = new App();
        Gui gui = new Gui(app.user);
    }
}
