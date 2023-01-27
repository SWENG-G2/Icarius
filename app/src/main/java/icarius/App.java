package icarius;

import icarius.http.GetRequest;
import icarius.http.PostRequest;

import java.util.HashMap;

import icarius.controllers.BirdController;
import icarius.controllers.KeyController;

public class App {
    public static final String BASE_URL = "http://localhost:8080";
    public static final String currentIdentity = "a";

    public App() {
    }

    public static void main(String[] args) {
       App app = new App();
       //testPOST();
       //testGET();
       //testFileUpload();
       //testKeyGen();
    }

    // TEMPORARY FUNCTIONS FOR TESTING PURPOSES

    private static void testPOSTBirds() {
        int campusId = 5;
        HashMap<String, String> birdProperties = new HashMap<String, String>();
        birdProperties.put("name", "Davey");
        birdProperties.put("listImageURL", "anImageOfADuck.jpg");
        birdProperties.put("heroImageURL", "aHeroImageOfADuck.jpg");
        birdProperties.put("soundURL", "quack.mp3");
        birdProperties.put("aboutMe", "Davey the Duck");
        birdProperties.put("aboutMeVideoURL", "DaveysVideoCV.mp4");
        birdProperties.put("location", "over there");
        birdProperties.put("locationImageURL", "overThere.jpg");
        birdProperties.put("diet", "Werms and Grapes");
        birdProperties.put("dietImageURL", "Image of werms and grapes");
        BirdController.newBird(birdProperties, campusId);
    }

    private static void testDELETEBirds() {
        int birdId = 2;
        int campusId = 5;
        BirdController.removeBird(birdId, campusId);
    }

    private static void testPATCHBirds() {
        int campusId = 5;
        int birdId = 2;
        HashMap<String, String> newBirdInformation = new HashMap<String, String>();
        newBirdInformation.put("aboutMe", "Donald the Duck");
        BirdController.editBird(newBirdInformation, birdId, campusId);
    }

    private static void testGET() {
        GetRequest test = new GetRequest("/api/campus/all");
        System.out.println( test.send() );
    }

    private static void testPOST() {
        PostRequest test = new PostRequest("/api/campus/new", currentIdentity, 0);
        test.addParameter("name", "testnumber1mil");
        System.out.println( test.send() );
    }

    private static void testFileUpload() {
        PostRequest test = new PostRequest("/api/file/1/new", currentIdentity, 1);
        test.addFile("app/src/main/resources/test.jpg", "image");
        System.out.println( test.send() );
    }

    private static void testKeyGen() {
        KeyController.generateKey(true, "OWNER_NAME");
        KeyController.removeKey("identity of generated key here");
    }
}
