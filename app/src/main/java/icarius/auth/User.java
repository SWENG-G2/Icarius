package icarius.auth;

import icarius.http.KeyRequest;
import lombok.Getter;
import okhttp3.OkHttpClient;

@Getter
public class User {
    private Credentials credentials;
    private String key;
    private final OkHttpClient okHttpClient;

    public User(Credentials credentials, OkHttpClient okHttpClient) {
        this.credentials = credentials;
        this.okHttpClient = okHttpClient;
        refreshKey();
    }

    public String getAuth() {
        return AuthenticationService.getAuth(this);
    }

    private void refreshKey() {
        key = new KeyRequest(okHttpClient).send();
    }
}
