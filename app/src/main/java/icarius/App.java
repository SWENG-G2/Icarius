package icarius;

import icarius.http.GetRequest;
import okhttp3.OkHttpClient;

import java.util.List;
import java.util.ArrayList;

import icarius.auth.Credentials;
import icarius.auth.User;

import icarius.entities.Bird;
import icarius.entities.Campus;
import icarius.gui.Gui;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class App {
    public static final String BASE_URL = "http://localhost:8080";
    public User user;
    public List<Campus> campuses;
    private final OkHttpClient okHttpClient;

    public App() {
        Credentials credentials = new Credentials("sysadmin", "sysadmin");
        this.okHttpClient = new OkHttpClient();
        this.user = new User(credentials, okHttpClient);
    }

    public static void main(String[] args) {
        App app = new App();
        app.getCampusList();
        Gui gui = new Gui();
    }

    public String getCampusList() {
        this.campuses = new ArrayList<>();
        String campusId;
        // send and store GET request response
        GetRequest request = new GetRequest("/campus/list", this.okHttpClient);
        String response = request.send();

        if (response == null) {
            return null;
        } else {
            // Fetch name from XML response
            try {
                Document document = DocumentHelper.parseText(response);
                Element root = document.getRootElement();
                // iterate through child elements of presentation with element name "slide"
                for (Iterator<Element> it = root.elementIterator("slide"); it.hasNext();) {
                    Element slide = it.next();
                    campusId = slide.attributeValue("title");
                    Campus newCampus = new Campus(okHttpClient);
                    newCampus.setId(Long.parseLong(campusId));
                    newCampus.read(null);
                    this.campuses.add(newCampus);
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }

            int listSize = campuses.size();
            return Integer.toString(listSize);
        }

    }

    private String campusListToString(){
        String campusList = "";
        for (Campus campus : campuses) {
            campusList += campus.getBirds
            ();
        }
        return campusList;
    }

    // TEMPORARY FUNCTIONS FOR TESTING PURPOSES

    // private void testGetBird() {
    //     long bird_Id = 2;
    //     Bird bird = new Bird(bird_Id);
    //     System.out.println(bird.toString());
    // }

    // private void testGetCampus() {
    //     long campus_Id = 5;
    //     Campus campus = new Campus(campus_Id);
    //     System.out.println(campus.toString());
    //     System.out.println(campus.getBirds());
    // }

    // private void testPOSTBirds() {
    // user.setCampusId(5);
    // HashMap<String, String> birdProperties = new HashMap<String, String>();
    // birdProperties.put("name", "Davey");
    // birdProperties.put("listImageURL", "anImageOfADuck.jpg");
    // birdProperties.put("heroImageURL", "aHeroImageOfADuck.jpg");
    // birdProperties.put("soundURL", "quack.mp3");
    // birdProperties.put("aboutMe", "Davey the Duck");
    // birdProperties.put("aboutMeVideoURL", "DaveysVideoCV.mp4");
    // birdProperties.put("location", "over there");
    // birdProperties.put("locationImageURL", "overThere.jpg");
    // birdProperties.put("diet", "Werms and Grapes");
    // birdProperties.put("dietImageURL", "Image of werms and grapes");
    // BirdController.newBird(birdProperties, user);
    // }

    // private void testDELETEBirds() {
    // int birdId = 2;
    // BirdController.removeBird(birdId, user);
    // }

    // private void testPATCHBirds() {
    // int birdId = 2;
    // HashMap<String, String> newBirdInformation = new HashMap<String, String>();
    // newBirdInformation.put("aboutMe", "Donald the Duck");
    // BirdController.editBird(newBirdInformation, birdId, user);
    // }

    // private void testGET() {
    // GetRequest test = new GetRequest("/bird/2");
    // System.out.println(test.send());
    // }

    // private void testPOST() {
    // PostRequest test = new PostRequest("/api/campus/new", user);
    // test.addParameter("name", "user test");
    // System.out.println(test.send());
    // }

    // private void testFileUpload() {
    // PostRequest test = new PostRequest("/api/file/1/new", user);
    // test.addFile("app/src/main/resources/test.jpg", "image");
    // System.out.println(test.send());
    // }
}
