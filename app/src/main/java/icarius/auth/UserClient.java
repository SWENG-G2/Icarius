package icarius.auth;

import java.util.ArrayList;
import java.util.List;

import icarius.http.GetRequest;
import icarius.http.PostRequest;
import icarius.http.ServerResponse;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;

@Getter
@Setter
public class UserClient {
    private Credentials credentials;
    private String publicKey;
    private Boolean admin = false;
    private Boolean valid = false;
    private List<Long> campusPermissions;
    private final OkHttpClient okHttpClient;

    public UserClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public String getAuth() {
        if (publicKey == null || publicKey.equals(""))
            refreshKey(null);
        if (publicKey == null || publicKey.equals(""))
            return " ";
        return AuthenticationService.getAuth(this, null);
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

        if (response.isSuccessful()) {
            this.valid = Boolean.parseBoolean(response.getHeader("valid"));
            this.admin = Boolean.parseBoolean(response.getHeader("admin"));

            if (this.valid) {
                // reset list
                campusPermissions = new ArrayList<>();
                String campusPermissionHeader = response.getHeader("Campuses");

                // If permissions header doesn't exist or is empty return
                if (campusPermissionHeader.equals("") || campusPermissionHeader == null) {
                    return this.valid;
                }

                // If sysadmin
                if (campusPermissionHeader.equals("-1")) {
                    return this.valid;
                }

                // Convert string of numbers into list of ints
                String[] campusIdsAsStrings = campusPermissionHeader.split(",");
                for (String id : campusIdsAsStrings) {
                    campusPermissions.add(Long.parseLong(id));
                }
            }

            return this.valid;
        }
        return false;
    }

    public boolean isAdmin() {
        return this.admin;
    }
}
