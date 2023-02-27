package icarius;

import icarius.http.GetRequest;
import icarius.http.PostRequest;
import icarius.auth.Credentials;
import icarius.auth.User;

import icarius.entities.Bird;
import icarius.gui.Gui;

public class App {
    public static final String BASE_URL = "http://localhost:8080";
    public User user;

    public App() {
        Credentials credentials = new Credentials("sysadmin", "sysadmin");
        user = new User(credentials);
    }

    public static void main(String[] args) {
        App app = new App();
        Gui gui = new Gui();
    }

    // TEMPORARY FUNCTIONS FOR TESTING PURPOSES

    private void testGetBird() {
        Long Id = 2L;
        Bird bird = new Bird(Id, "Daphne", user);
        System.out.println(bird.toString());
    }

    // private void testDELETEBirds() {
    //     int birdId = 2;
    //     BirdController.removeBird(birdId, user);
    // }

    // private void testPATCHBirds() {
    //     int birdId = 2;
    //     HashMap<String, String> newBirdInformation = new HashMap<String, String>();
    //     newBirdInformation.put("aboutMe", "Donald the Duck");
    //     BirdController.editBird(newBirdInformation, birdId, user);
    // }

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
}
