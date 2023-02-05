package icarius.controllers;

import java.util.HashMap;

import icarius.http.DeleteRequest;
import icarius.http.PatchRequest;
import icarius.http.PostRequest;
import icarius.user.User;


public class BirdController {

    /**
    * Uploads a new bird to the server corresponding to a specific campus.
    *
    * @param birdInformationHashMap all the birds information (name, diet, image, video, ect).
    * @param user user which contains id for which campus to add the bird to.
    */
    public static void newBird(HashMap<String,String> birdInformationHashMap, User user) {
        PostRequest request = new PostRequest("/api/birds/" + user.getCampusIdString() + "/new", user);
        request.addParameters(birdInformationHashMap);
        System.out.println( request.send() );
    }

    /**
    * Edit a bird's information thats already uploaded to the server.
    *
    * @param birdInformationHashMap any of the birds information that needs changing (name, diet, image, video, ect).
    * @param birdId id for which bird that needs information changing.
    * @param user user which contains id for which campus the bird belongs to.
    */
    public static void editBird(HashMap<String,String> birdInformationHashMap, int birdId, User user) {
        PatchRequest request = new PatchRequest("/api/birds/" + user.getCampusIdString() + "/edit", user);
        request.addParameter("id", Integer.toString(birdId));
        request.addParameters(birdInformationHashMap);
        System.out.println( request.send() );
    }

    /**
    * Remove a bird from the server.
    *
    * @param birdId id for which bird that removing.
    * @param user user which contains id for which campus the bird belongs to.
    */
    public static void removeBird(int birdId, User user) {
        DeleteRequest request = new DeleteRequest("/api/birds/" + user.getCampusIdString() + "/remove", user);
        request.addParameter("id", Integer.toString(birdId));
        request.addParameter("campusId", user.getCampusIdString());
        System.out.println( request.send() );
    }
}