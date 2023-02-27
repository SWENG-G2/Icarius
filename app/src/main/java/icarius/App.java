package icarius;

import icarius.http.GetRequest;
import icarius.http.PostRequest;
import icarius.auth.Credentials;
import icarius.auth.User;

import java.util.HashMap;

public class App {
    public static final String BASE_URL = "http://localhost:8080";
    public User user;

    public App() {
        Credentials credentials = new Credentials("sysadmin", "sysadmin");
        user = new User(credentials);
    }

    public static void main(String[] args) {
        App app = new App();

        app.testPOST();
    }

    // TEMPORARY FUNCTIONS FOR TESTING PURPOSES

    // private void testPOSTBirds() {
    //     HashMap<String, String> birdProperties = new HashMap<String, String>();
    //     birdProperties.put("name", "Davey");
    //     birdProperties.put("listImageURL", "anImageOfADuck.jpg");
    //     birdProperties.put("heroImageURL", "aHeroImageOfADuck.jpg");
    //     birdProperties.put("soundURL", "quack.mp3");
    //     birdProperties.put("aboutMe", "Davey the Duck");
    //     birdProperties.put("aboutMeVideoURL", "DaveysVideoCV.mp4");
    //     birdProperties.put("location", "over there");
    //     birdProperties.put("locationImageURL", "overThere.jpg");
    //     birdProperties.put("diet", "Werms and Grapes");
    //     birdProperties.put("dietImageURL", "Image of werms and grapes");
    //     BirdController.newBird(birdProperties, user);
    // }

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
        GetRequest test = new GetRequest("/api/campus/all");
        System.out.println( test.send() );
    }

    private void testPOST() {
        PostRequest test = new PostRequest("/api/campus/new", user);
        test.addParameter("name", "user test");
        System.out.println( test.send() );
    }

    private void testFileUpload() {
        PostRequest test = new PostRequest("/api/file/1/new", user);
        test.addFile("app/src/main/resources/test.jpg", "image");
        System.out.println( test.send() );
    }
}
