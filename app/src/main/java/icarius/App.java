package icarius;

import icarius.http.GetRequest;
import icarius.http.PostRequest;
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
