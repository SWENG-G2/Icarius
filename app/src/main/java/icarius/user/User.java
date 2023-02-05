package icarius.user;

import icarius.services.AuthenticationService;

public class User {
    private String identity;
    private int campusId = -1;

    public User(String identity) {
        this.identity = identity;

        // When instantiating, check whether any keys exist locally
        // If local key exists, automatically log in as that user
        // else ask for user login

    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getCampusId() {
        return campusId;
    }

    public String getCampusIdString() {
        return campusId == -1 ? "admin" : String.valueOf(campusId);
    }

    public void setCampusId(int currentCampusId) {
        this.campusId = currentCampusId;
    }

    public String getAuth() {
        return AuthenticationService.getAuth(
            getIdentity(),
            getCampusIdString()
        );
    }
}
