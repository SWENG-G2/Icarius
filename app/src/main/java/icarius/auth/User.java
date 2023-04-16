package icarius.auth;

import icarius.http.KeyRequest;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;

@Getter
@Setter
public class User {
    private Credentials credentials;
    private String publicKey;
    private Boolean admin;
    private final OkHttpClient okHttpClient;

    public User(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        refreshKey();
    }

    public String getAuth() {
        return AuthenticationService.getAuth(this);
    }

    private void refreshKey() {
        publicKey = new KeyRequest(okHttpClient).send();
    }

    public boolean validate() {
        // TODO: validate credentials and see if admin
        admin = true;
        return true;
    }
}
