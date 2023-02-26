package icarius.auth;

public class User {
    private String username, password;
    private byte[] key;
    private boolean valid;

    public User(String user, String pass) {
        this.username = user;
        this.password = pass;

        // check valid
        // if valid save key
    }

    public String getUser() {
        return username;
    }

    public String getPass() {
        return password;
    }

    public byte[] getKey() {
        // TODO: If null get key from server
        return key;
    }

    public String getAuth() {
        return AuthenticationService.getAuth(this);
    }
}
