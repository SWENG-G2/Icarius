package icarius;

import icarius.http.GetRequest;
import icarius.http.PostRequest;
import icarius.user.User;

import java.util.HashMap;

import icarius.controllers.BirdController;
import icarius.controllers.KeyController;
import icarius.entities.Bird;
import icarius.entities.Campus;
import icarius.entities.ServerEntity;

public class App {
    public static final String BASE_URL = "http://localhost:8080";
    public User user;

    public App() {
        user = new User("sysadmin");
    }

    public static void main(String[] args) {
        App app = new App();
        // app.testGetBird();
        app.testGetCampus();
    }

    // TEMPORARY FUNCTIONS FOR TESTING PURPOSES

    private void testGetBird() {
        long bird_Id = 2;
        Bird bird = new Bird(bird_Id);
        System.out.println(bird.toString());
    }

    private void testGetCampus() {
        long campus_Id = 5;
        Campus campus = new Campus(campus_Id);
        System.out.println(campus.getBirds());
    }

    private void testPOSTBirds() {
        user.setCampusId(5);
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
        BirdController.newBird(birdProperties, user);
    }

    private void testDELETEBirds() {
        int birdId = 2;
        user.setCampusId(5);
        BirdController.removeBird(birdId, user);
    }

    private void testPATCHBirds() {
        user.setCampusId(5);
        int birdId = 2;
        HashMap<String, String> newBirdInformation = new HashMap<String, String>();
        newBirdInformation.put("aboutMe", "Donald the Duck");
        BirdController.editBird(newBirdInformation, birdId, user);
    }

    private void testGET() {
        GetRequest test = new GetRequest("/bird/2");
        System.out.println(test.send());
    }

    private void testPOST() {
        PostRequest test = new PostRequest("/api/campus/new", user);
        test.addParameter("name", "user test");
        System.out.println(test.send());
    }

    private void testFileUpload() {
        PostRequest test = new PostRequest("/api/file/1/new", user);
        test.addFile("app/src/main/resources/test.jpg", "image");
        System.out.println(test.send());
    }

    private void testKeyGen() {
        KeyController.generateKey(true, "OWNER_NAME", user);
        KeyController.removeKey("identity of generated key here", user);
    }
}
