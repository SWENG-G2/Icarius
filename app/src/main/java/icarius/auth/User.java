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
    private Boolean admin, valid;
    private final OkHttpClient okHttpClient;

    public User(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        refreshKey(null);
    }

    public String getAuth() {
        return AuthenticationService.getAuth(this);
    }

    private void refreshKey(GetRequest request) {
        if (request == null) {
            request = new GetRequest("/key", okHttpClient);
        }
        publicKey = request.send().getHeader("key");
    }

    public boolean validate(PostRequest request) {
        if (request == null) {
            request = new PostRequest("/api/users/validate", this);
        }
        ServerResponse response = request.send();
        
        this.valid = Boolean.parseBoolean(response.getHeader("valid"));
        this.admin = Boolean.parseBoolean(response.getHeader("admin"));
        return this.valid;
    }
}
