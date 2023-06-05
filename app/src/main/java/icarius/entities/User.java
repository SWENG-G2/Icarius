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

    /**
     * @param userClient
     * @param username
     */
    public User(UserClient userClient, String username) {
        this.userClient = userClient;
        this.username = username;
    }

    /**
     * Create user on server
     * 
     * @param password
     * @param request
     * @return TRUE if successfully created on server, else FALSE
     */
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

    /**
     * Delete user on server
     * 
     * @param request
     * @return TRUE if successfully deleted from server, else FALSE
     */
    public Boolean delete(PostRequest request) {
        // Send create user request to server
        if (request == null)
            request = new PostRequest("/api/users/remove", userClient);

        request.addParameter("username", username);

        ServerResponse response = request.send();
        return response.isSuccessful();
    }

    /**
     * Add campus permissions to user on server
     * 
     * @param campusId
     * @param request
     * @return TRUE if successfully added permissions to user on server, else FALSE
     */
    public Boolean addCampus(Long campusId, PatchRequest request) {
        // Send create user request to server
        if (request == null)
            request = new PatchRequest("/api/users/addCampus", userClient);

        request.addParameter("username", username);
        request.addParameter("campusID", "" + campusId);

        ServerResponse response = request.send();

        return response.isSuccessful();
    }

    /**
     * Remove campus permissions to user on server
     * 
     * @param campusId
     * @param request
     * @return TRUE if successfully removed permissions from user on server, else FALSE
     */
    public Boolean removeCampus(Long campusId, PatchRequest request) {
        // Send create user request to server
        if (request == null)
            request = new PatchRequest("/api/users/removeCampus", userClient);

        request.addParameter("username", username);
        request.addParameter("campusID", "" + campusId);

        ServerResponse response = request.send();
        return response.isSuccessful();
    }

    /**
     * Add campus permission to local object
     * @param campus
     */
    public void addPermission(Campus campus) {
        campusPermissions.add(campus);
    }

    /**
     * Remove campus permission from local object
     * @param campus
     */
    public void removePermission(Campus campus) {
        campusPermissions.remove(campus);
    }
}
