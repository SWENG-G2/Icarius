package icarius.auth;

import lombok.Data;

public @Data class Credentials {
    private String username, password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
