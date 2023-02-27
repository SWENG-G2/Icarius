package icarius.auth;

import icarius.http.KeyRequest;
import lombok.Getter;

@Getter
public class User {
    private Credentials credentials;
    private String key;

    public User(Credentials credentials) {
        this.credentials = credentials;
        refreshKey();
    }

    public String getAuth() {
        return AuthenticationService.getAuth(this);
    }

    private void refreshKey() {
        key = new KeyRequest().send();
    }
}
