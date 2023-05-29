package icarius.entities;

import java.util.ArrayList;
import java.util.List;

import icarius.auth.UserClient;
import icarius.http.PatchRequest;
import icarius.http.PostRequest;
import icarius.http.ServerResponse;
import lombok.Getter;
import lombok.Setter;


public class User {
    private UserClient userClient;
    private @Getter String username;
    private @Setter @Getter Boolean admin = false;
    private @Getter List<Campus> campusPermissions = new ArrayList<>();

    public User(UserClient userClient, String username) {
        this.userClient = userClient;
        this.username = username;
    }

    public Boolean create(String password, PostRequest request) {
        // Send create user request to server
        if (request == null)
            request = new PostRequest("/api/users/new", userClient);

        request.addParameter("username", username);
        request.addParameter("password", password);
        if (admin)
            request.addParameter("sysadmin", "" + admin);

        ServerResponse response = request.send();
        return response.isSuccessful();
    }

    public Boolean delete(PostRequest request) {
        // Send create user request to server
        if (request == null)
            request = new PostRequest("/api/users/remove", userClient);

        request.addParameter("username", username);

        ServerResponse response = request.send();
        return response.isSuccessful();
    }

    public Boolean addCampus(Long campusId, PatchRequest request) {
        // Send create user request to server
        if (request == null)
            request = new PatchRequest("/api/users/addCampus", userClient);

        request.addParameter("username", username);
        request.addParameter("campusID", "" + campusId);

        ServerResponse response = request.send();

        return response.isSuccessful();
    }

    public Boolean removeCampus(Long campusId, PatchRequest request) {
        // Send create user request to server
        if (request == null)
            request = new PatchRequest("/api/users/removeCampus", userClient);

        request.addParameter("username", username);
        request.addParameter("campusID", "" + campusId);

        ServerResponse response = request.send();
        return response.isSuccessful();
    }

    public void addPermission(Campus campus) {
        campusPermissions.add(campus);
    }

    public void removePermission(Campus campus) {
        campusPermissions.remove(campus);
    }
}
