package icarius.auth;

import icarius.http.GetRequest;
import icarius.http.PostRequest;
import icarius.http.ServerResponse;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;

@Getter
@Setter
public class User {
    private Credentials credentials;
    private String publicKey;
    private Boolean admin, valid, serverConnection;
    private final OkHttpClient okHttpClient;

    public User(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public String getAuth() {
        if (publicKey == null || publicKey.equals("")) refreshKey(null);
        if (publicKey == null || publicKey.equals("")) return "";
        return AuthenticationService.getAuth(this);
    }

    public void refreshKey(GetRequest request) {
        if (request == null) {
            request = new GetRequest("/key", this);
        }
        publicKey = request.send().getHeader("key");
    }

    public boolean validate(PostRequest request) {
        if (request == null) {
            request = new PostRequest("/api/users/validate", this);
        }
        ServerResponse response = request.send();

        if (response.getCode() == 404) serverConnection = false;

        if (response.isSuccessful()) {
            serverConnection = true;
            this.valid = Boolean.parseBoolean(response.getHeader("valid"));
            this.admin = Boolean.parseBoolean(response.getHeader("admin"));
            return this.valid;
        }
        return false;
    }
}
