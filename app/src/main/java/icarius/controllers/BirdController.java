package icarius.controllers;

import java.util.HashMap;

import icarius.App;
import icarius.http.DeleteRequest;
import icarius.http.PatchRequest;
import icarius.http.PostRequest;


public class BirdController {

    private static int TO_STRING_RADIX = 10;

    /**
    * Uploads a new bird to the server corresponding to a specific campus.
    *
    * @param birdInformationHashMap all the birds information (name, diet, image, video, ect).
    * @param campusId id for which campus to add the bird to.
    */
    public static void newBird(HashMap<String,String> birdInformationHashMap , int campusId) {
        PostRequest request = new PostRequest("/api/birds/" + Integer.toString(campusId, TO_STRING_RADIX) + "/new", App.currentIdentity, campusId);
        // Iterates through the hashmap adding the key (to be added) and corresponding value (to be added) to the request
        request.addParameters(birdInformationHashMap);
        System.out.println( request.send() );
    }

    /**
    * Edit a bird's information thats already uploaded to the server.
    *
    * @param birdInformationHashMap any of the birds information that needs changing (name, diet, image, video, ect).
    * @param birdId id for which bird that needs information changing.
    * @param campusId id for which campus the bird belongs to.
    */
    public static void editBird(HashMap<String,String> birdInformationHashMap , int birdId , int campusId) {
        PatchRequest request = new PatchRequest("/api/birds/" + Integer.toString(campusId, TO_STRING_RADIX) + "/edit", App.currentIdentity, campusId);
        request.addParameter("id", Integer.toString(birdId));
        request.addParameters(birdInformationHashMap);
        System.out.println( request.send() );
    }

    /**
    * Remove a bird from the server.
    *
    * @param birdId id for which bird that removing.
    * @param campusId id for which campus the bird belongs to.
    */
    public static void removeBird(int birdId, int campusId) {
        DeleteRequest request = new DeleteRequest("/api/birds/" + Integer.toString(campusId, TO_STRING_RADIX) + "/remove", App.currentIdentity, campusId);
        request.addParameter("id", Integer.toString(birdId));
        request.addParameter("campusid", Integer.toString(campusId));
        System.out.println( request.send() );
    }
}